package com.cj.qc.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 全局应用资源类（应用上下文）
 * @date Date : 2018/09/17 15:38
 */
@Slf4j
public class WebContext {

	/**
	 * 获取当前token中用户id
	 * @return
	 */
	public static Long getLocalUserId(){
		return 1L;
	}

	/**
	 * 获取当前token中用户名称
	 * @return
	 */
	public static String getLocalUserName(){
		return "测试者";
	}

	/**
	 * 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = getRequestAttributes().getRequest();
		return request;
	}

	/**
	 * 获取response
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		HttpServletResponse response = getRequestAttributes().getResponse();
		return response;
	}

	private static ServletRequestAttributes getRequestAttributes() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes;
	}
}
