package com.cj.asr.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 语音识别回调控制器
 * @date Date : 2019/05/08 17:14
 */
@Slf4j
@RestController
@RequestMapping("/callback")
public class AsrCallBackController {

	private Map<String,String> map = new HashMap<>();

	@PostMapping("/ASR_REG_Web")
	public void callback(@RequestBody CallBackParam param){
		log.info("=====================语音识别接口回调结果（开始）=======================");
		log.info(param.toString());
		map.put(param.fileUniqueId,param.toString());
		log.info("=====================语音识别接口回调结果（结束）=======================");
	}

	@GetMapping("/ASR_REG_Web")
	public String callback(){
		return JSONObject.toJSONString(map);
	}

	@Data
	class CallBackParam {
		private String fileUniqueId;
		private String spareField;
		private String asrResult;
		private String channel;

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("{");
			sb.append("\"fileUniqueId\":\"")
					.append(fileUniqueId).append('\"');
			sb.append(",\"spareField\":\"")
					.append(spareField).append('\"');
			sb.append(",\"asrResult\":\"")
					.append(asrResult).append('\"');
			sb.append(",\"channel\":\"")
					.append(channel).append('\"');
			sb.append('}');
			return sb.toString();
		}
	}
}
