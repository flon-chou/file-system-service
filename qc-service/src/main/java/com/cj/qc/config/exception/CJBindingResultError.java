package com.cj.qc.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

/**
 * @author : Flon
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019/01/15 12:06
 */
@Setter
@Getter
public class CJBindingResultError extends RuntimeException{
	private BindingResult result;
	private static final long serialVersionUID = 2887280263892726521L;

	public CJBindingResultError(BindingResult result){
		this.result = result;
	}
}
