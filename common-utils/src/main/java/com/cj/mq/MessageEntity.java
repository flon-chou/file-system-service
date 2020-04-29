package com.cj.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 消息载体
 * @date Date : 2019/04/09 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity implements Serializable {
	private static final long serialVersionUID = -7204065271673484298L;

	private String msg;

	private boolean flag;

	private String dirName;

}
