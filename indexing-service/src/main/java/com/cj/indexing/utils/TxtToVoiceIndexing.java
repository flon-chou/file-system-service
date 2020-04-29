package com.cj.indexing.utils;

import com.cj.indexing.entity.VoiceIndexing;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: txt文件内容转对象
 * @date Date : 2019/03/25 17:03
 */
@Slf4j
public class TxtToVoiceIndexing{

	private static final int INDEX_SUM = 38;
	private static final String FIRST_SYMBOL = "'";
	private static final String LAST_SYMBOL = "'";
	private static final String SPLIT_SYMBOL = ",";

	/**
	 *  封装TXT文件内容
	 *  TXT文件内容样式：
	 *	 '文件名','作业领域','通话开始时间','通话结束时间','通话时长','通话号码','话务员工号','话务员组别','通道号','客户卡号','话务员团队','通话类型','录音号','场地','项目队列','满意度','质检员工号',
	 *	 '话后小结组别','小结分类','小结项目','小结内容','是否质检','是否提交案例库','是否提交学习跟进','是否异常录音','电营业务类型','数据状态及原因','数据状态二级原因','数据状态三级原因',
	 *	 '所属组长','客户证件号','客户姓名','单号','单号类型','电话类型','坐席姓名','规则类型','规则提示信息'
	 *	<p>注意事项</p>
	 * 	业务索引文件中每列以英文单引号和逗号（‘,’）隔开，如果没有内容需补空（不要有列缺失）；
	 * 	业务索引文件中每列内部内容里面，不允许出现单引号和逗号等属于分隔符号 或者回车换行符等会导致数据折行的特殊字符。
	 * 	业务索引文件的格式为不带BOM头的UTF8格式文件。
	 * 	业务索引文件中的“作业领域”、“文件名称”、“通话开始时间”、“通话时长”、 “通话号码”、“通话类型”等列不能为空。
	 * 	索引数据文件应以index为后缀的文件；
	 * 	单个索引数据文件大小必须大于（>）0KB；
	 * 	索引无须带有表头,只需给定应有的数据内容即可；
	 * @param path    文件路径
	 * @return
	 */
	public static List<VoiceIndexing> covert(String path){
		List<String> context = ReadTXT.readIndexFile(path);
		List<VoiceIndexing> list = new ArrayList<>();
		for (String line : context){
			VoiceIndexing indexing = covertLine(line);
			if (indexing==null){
				return null;
			}
			list.add(indexing);
		}
		return list;
	}

	/**
	 * 转换行数据为对象
	 * @param lineData
	 * @return 返回行数据对象 VoiceIndexing
	 */
	public static VoiceIndexing covertLine(String lineData){
		String[] line = lineData.split(SPLIT_SYMBOL);
		if (line.length!=INDEX_SUM){
			log.error("索引文件内容缺失或格式错误！");
			return null;
		}
		line = checkFormat(line);
		if(line==null || line.length<=0){
			return null;
		}
		return  covert(line);
	}

	/**
	 *  检查数组每个字符串格式是否正确  格式： 英文单引号+内容+英文单引号 构成的字符串
	 * @param arr  一个长度为38的字符串数组
	 * @return	处理过的字符串数组
	 */
	private static String[] checkFormat(String[] arr){
		String[]  temp= new String[arr.length];
		for (int i=0,len=arr.length;i<len;i++){
			if(! firstAndLastIndexOf(arr[i])){
				log.error(arr[i]+" 附近，内容格式不正确");
				return null;
			}
			temp[i] = arr[i].substring(1,arr[i].length()-1);
		}
		return temp;
	}

	/**
	 *  检查处理后的字符串数组中的字符串是否格式正确
	 * @param str	 将被检查的字符串
	 * @return
	 */
	private static boolean firstAndLastIndexOf(String str){
		return str.indexOf(FIRST_SYMBOL)==0
				&& str.lastIndexOf(LAST_SYMBOL)==str.length()-LAST_SYMBOL.length();
	}

	/**
	 *  对读取txt文件内容字符串数组进行封装
	 *  <p>注意</p>
	 *  此时的字符串数组中的每个数据应该是一个由  英文单引号+内容+英文单引号 构成的字符串
	 *  应该先去掉单引号再进行封装
	 * @param arr   一个长度为38的字符串数组
	 * @return
	 */
	private static VoiceIndexing covert(String[] arr){
		VoiceIndexing indexing = new VoiceIndexing();
		if(arr[0].trim().isEmpty()){
			log.error("索引文件内容错误，文件名为空");
			return null;
		}
		indexing.setFileName(arr[0].substring(0,arr[0].indexOf(".")));
		indexing.setNumberOfRecord(arr[1]);
		if(arr[2].trim().isEmpty()) {
			log.error("索引文件内容错误，开始时间为空");
			return null;
		}
		indexing.setStartTime(Timestamp.valueOf(arr[2]));
		indexing.setEndTime(Timestamp.valueOf(arr[3]));
		if(arr[4].trim().isEmpty()) {
			log.error("索引文件内容错误，通话时长为空");
			return null;
		}
		indexing.setDuration(Integer.valueOf(arr[4]));
		if(arr[5].trim().isEmpty()) {
			log.error("索引文件内容错误，通话号码为空");
			return null;
		}
		indexing.setCallNumber(arr[5]);
		if(arr[6].trim().isEmpty()) {
			log.error("索引文件内容错误，通话类型为空");
			return null;
		}
		indexing.setCallType(arr[6]);
		indexing.setTypeOfService(arr[7]);
		indexing.setTsrNo(arr[8]);
		indexing.setTsrGroup(arr[9]);
		indexing.setAgentNo(arr[10]);
		indexing.setTsrTeam(arr[11]);
		indexing.setRegion(arr[12]);
		indexing.setProjectList(arr[13]);
		indexing.setAccountNo(arr[14]);
		indexing.setCustomerId(arr[15]);
		indexing.setCustomerName(arr[16]);
		indexing.setQaNo(arr[17]);
		indexing.setSummarizeGroup(arr[18]);
		indexing.setSummarizeClass(arr[19]);
		indexing.setSummarizeProject(arr[20]);
		indexing.setSummarizeContent(arr[21]);
		indexing.setIsQa(arr[22]);
		indexing.setToWarehouse(arr[23]);
		indexing.setToImprovement(arr[24]);
		indexing.setIsNormality(arr[25]);
		indexing.setDataStatus(arr[26]);
		indexing.setDataStatus2(arr[27]);
		indexing.setDataStatus3(arr[28]);
		indexing.setGroupLeader(arr[29]);
		indexing.setPolicyNo(arr[30]);
		indexing.setProductType(arr[31]);
		indexing.setPlanCode(arr[32]);
		indexing.setPlanType(arr[33]);
		indexing.setInsName(arr[34]);
		indexing.setSalesArea(arr[35]);
		indexing.setBuType(arr[36]);
		indexing.setRulePrompt(arr[37]);
		return indexing;
	}

	public static void main(String[] args) {
		List<VoiceIndexing> indexing = TxtToVoiceIndexing.covert("C:\\Users\\flonc\\Desktop\\temp\\20190325.index");
		System.out.println(indexing);
	}

}
