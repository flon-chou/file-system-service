package com.cj.ffmpeg;

import java.io.File;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 临时文件组
 * @date Date : 2019/03/21 15:19
 */
public class TempFileGroup {
	/**
	 * 源文件
	 */
	private File source;
	/**
	 * 目标文件
	 */
	private File target;

	public TempFileGroup(File source, File target) {
		this.source = source;
		this.target = target;
	}

	public File getSource() {
		return source;
	}

	public void setSource(File source) {
		this.source = source;
	}

	public File getTarget() {
		return target;
	}

	public void setTarget(File target) {
		this.target = target;
	}
}
