package com.cj.qc.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author : Flon
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019/04/02 15:37
 */
@Configuration
public class MybatisConfig {

	@Bean
	public PageHelper pageHelper(){
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();

		// 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
		p.setProperty("offsetAsPageNum","true");

		//设置为true时，使用RowBounds分页会进行count查询
		p.setProperty("rowBoundsWithCount","true");
		p.setProperty("reasonable","true");
		pageHelper.setProperties(p);
		return pageHelper;
	}
}
