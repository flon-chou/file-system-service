package com.cj.qc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableSwaggerBootstrapUI
//@EnableCaching//开启缓存
@EnableScheduling//开启定时
//使用fastmybatis不需要MapperScan
//@MapperScan("com.chiju.doublerecord.dao")
public class QCServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QCServiceApplication.class, args);
	}

}
