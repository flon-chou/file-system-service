package com.cj.monitor.listener;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;

/**
 * @author : Flon
 * @version V1.0
 * @Description: FileFilter 实现类
 * @date Date : 2019/04/08 16:33
 */
@Slf4j
public class FileFilterImpl implements FileFilter {

	/**
	 *
	 * @param pathname
	 * @return true:返回所有目录下所有文件详细(包含所有子目录);return false:返回主目录下所有文件详细(不包含所有子目录)
	 */
	@Override
	public boolean accept(File pathname) {
		log.info("file filter: " +pathname);
		return false;
	}
}


