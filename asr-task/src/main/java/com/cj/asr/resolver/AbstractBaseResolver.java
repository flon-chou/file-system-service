package com.cj.asr.resolver;


import com.cj.asr.resolver.exception.ResolverException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: Base Resolver Abstract Class
 * @date Date : 2019/03/27 11:25
 */
@Slf4j
public abstract class AbstractBaseResolver<T> {

	/**
	 * 语音引擎识别结果输出目录
	 */
	@Value("${speech-engine.running-result-dir}")
	private String runningResultDir;
	/**
	 * 识别结果文件后缀名
	 */
	@Value("${resolver.file-ext}")
	private String ext;
	/**
	 * 识别结果文件识别的音频文件后缀名
	 */
	@Value("${resolver.audio-ext}")
	private String audioExt;
	/**
	 * 识别结果文件识别的音频文件名路径分割符（windows: '\\'  unix: '/'）
	 */
	@Value("${resolver.audio-delimiter}")
	private String audioDelimiter;
	/**
	 * 识别结果文件内容行数据分割符
	 */
	@Value("${resolver.delimiter}")
	private String delimiter;
	protected static int SAME_LINE = 1;
	protected static int DIFFERENT_LINE = 2;
//	static int FILENAME_POSITION = 1;

	/**
	 * 解析到的数据所属音频文件的文件名
	 */
	private String audioName;

	public List<SpecialRowData> specialRowDataList = null;
	/**
	 * specialRowDataList 索引
	 */
	private static int index = 0;

	protected String getAudioName() {
		return audioName;
	}

	protected void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	/**
	 * 设置数据格式（子类必须重写设定数据格式）
	 * @return 数据格式
	 */
	protected abstract DataFormat formatData();

	/**
	 * 是否有特殊行数据
	 * @return 有特殊行数据则返回 true
	 */
	protected abstract boolean hasSpecialRowData();

	/**
	 *  添加特殊行数据（为有特殊需求的子类提供）
	 *  当且仅当  hasSpecialRowData() 方法返回 true 时调用
	 * @param specialRowData 存储特殊行数的对象
	 */
	protected void addSpecialRowData(SpecialRowData specialRowData){
		if (specialRowDataList==null){
			specialRowDataList = new ArrayList<>();
		}
		specialRowDataList.add(index,specialRowData);
		index ++;
	}

	/**
	 * 数据转换器（子类必须重写）
	 * @param arr 行数据字符串以{@code delimiter}分割后的字符串数组
	 * @return	返回封装好的对象
	 * @throws ResolverException
	 */
	protected abstract List<T> convert(String[] arr) throws ResolverException;

	/**
	 *  文件解析器
	 * @return	返回封装好的对象list集合
	 */
	public List<T> resolver(){
		if (formatData()==null){
			log.error(DataFormat.class.getName()+ " is empty. You must be set data content format!");
			return null;
		}
		if (! formatData().hasValue()){
			return null;
		}
		List<String> lineDataList = null;
		File file = new File(runningResultDir+File.separator+formatData().getFilename());
		if (isExist(file)){
			lineDataList = readFile(file);
		}
		if (lineDataList==null || lineDataList.isEmpty()){
			log.warn(formatData().getFilename()+": the file content is empty !");
			return null;
		}
		return traverseResolver(lineDataList);
	}

	/**
	 * 判断是否是文件，并且文件后缀名是否正确
	 * @param file  文件
	 * @return 当且仅当文件存在，并且文件后缀名正确返回 true
	 */
	private boolean isExist(File file){
		if(! file.isFile()){
			log.error(file.getAbsolutePath()+" is not a file!");
			return false;
		}
		if (! file.getName().contains(ext)){
			log.error(file.getAbsolutePath()+" not a "+ext+" file!");
			return false;
		}
		return true;
	}

	/**
	 *  读取文件行信息，每一行代表数据库一行数据
	 * @param file
	 * @return	将行数据插入ArrayList集合
	 */
	private List<String> readFile(File file){
		List<String> context = null;
		try (FileReader reader = new FileReader(file);
			 BufferedReader br = new BufferedReader(reader))
		{
			String lineData ;
			context = new ArrayList<>();
			while ((lineData = br.readLine()) != null) {
				context.add(lineData);
			}
		} catch (IOException e) {
			log.error("读取文件出错："+e.getMessage());
			e.printStackTrace();
		}
		return context;
	}

	/**
	 * 循环遍历数据集合，并封装成{@code T}对象集合
	 * @param dataList 文件内容行数据List
	 * @return	返回封装好的对象list集合
	 */
	private List<T> traverseResolver(List<String> dataList){
		List<T> results = new ArrayList<>(dataList.size());
		try {
			for(String lineData : dataList){
				//当且仅当返回 true 时不继续解析，将解析下一行数据
				if (rowDataFilter(lineData)){
					continue;
				}
				List<T> ts = rowResolver(lineData);
				if (ts==null || ts.isEmpty()){
					continue;
				}
				results.addAll(ts);
			}
		} catch (ResolverException e) {
			log.error(e.getMessage());
			results = null;
		}
		return results;
	}

	/**
	 * 行数据过滤，过滤文件名行数据和空行数据
	 * @param data	行数据
	 * @return 当且仅当为文件名行数据或空行数据时返回 true
	 * @throws ResolverException
	 */
	private boolean rowDataFilter(String data) throws ResolverException{
		if (data==null || data.isEmpty()){
			return true;
		}
		if (formatData().getFormatType()==DIFFERENT_LINE && data.endsWith(audioExt)) {
			this.audioName = parseAudioName(data);
			return true;
		}
		return false;
	}

	/**
	 * 行数据解析器
	 * @param data 行数据
	 * @return	返回封装好的对象
	 * @throws ResolverException
	 */
	private List<T> rowResolver(String data) throws ResolverException{
		String[] dataArr = splitLineData(data);
		if (dataArr.length!=formatData().getLineDataArrLen() && ! hasSpecialRowData()){
			throw new ResolverException("Row data array length does not match specified length !");
		}
		return convert(dataArr);
	}

	/**
	 * 以{@code delimiter}分割行数据字符串
	 * @param data  行数据字符串
	 * @return	行数据字符串分割后的字符串数组
	 */
	private String[] splitLineData(String data){
		return data.split(delimiter);
	}

	/**
	 * 解析识别结果识别的音频文件文件名
	 * @param data	识别结果行数据
	 * @return	音频文件名
	 */
	String parseAudioName(String data){
		String[] dataArr  = data.split(audioDelimiter);
		return dataArr[dataArr.length-1];
	}
}
