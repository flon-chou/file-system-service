package com.cj.monitor.listener;

import com.cj.utils.DateTool;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 监听工厂
 * @date Date : 2019/04/08 16:51
 */
@Component
public class FileListenerFactory {

	@Value("${monitor.dir-path}")
	private  String monitorDirPath ;
	@Value("${monitor.interval}")
	private Long interval;
	@Value("${monitor.file-suffix}")
	private String suffix;

	@Autowired
	private FileListenerAdaptor fileListenerAdaptor;

	public FileAlterationMonitor initMonitor() {
		// 创建过滤器
		IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(),
				HiddenFileFilter.VISIBLE);
		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
				FileFilterUtils.suffixFileFilter(suffix));
		IOFileFilter filter = FileFilterUtils.or(directories, files);

		// 装配过滤器
		// FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir));
		monitorDirPath += File.separator+ DateTool.nowDate();
		FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDirPath), filter);

		// 向监听者添加监听器，并注入业务服务
		observer.addListener(fileListenerAdaptor);
		// 将秒转换为毫秒
		interval = interval*1000;
		// 返回监听者
		return new FileAlterationMonitor(interval, observer);
	}


}
