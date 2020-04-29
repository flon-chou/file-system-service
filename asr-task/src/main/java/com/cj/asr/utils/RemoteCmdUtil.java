package com.cj.asr.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 远程命令执行工具
 * 						用于执行语音引擎识别脚本
 * @date Date : 2019/03/22 16:29
 */
@Slf4j
@Component
public class RemoteCmdUtil {
	@Value("${speech-engine.service.encoding}")
	private static String  ENCODING;
	@Value("${speech-engine.service.ip}")
	private static String IP;
	@Value("${speech-engine.service.username}")
	private static String USERNAME;
	@Value("${speech-engine.service.password}")
	private static String PASSWORD;
	@Value("${speech-engine.op-cmd}")
	private static String CMD;

	/**
	 * 登录主机
	 * @return
	 * 		登录成功返回true，否则返回false
	 */
	public static Connection login(){
		return login(IP,USERNAME,PASSWORD);
	}
	/**
	 * 登录主机
	 * @return
	 *      登录成功返回true，否则返回false
	 */
	public static Connection login(String ip, String userName, String userPwd){
		boolean flg=false;
		Connection conn = null;
		try {
			conn = new Connection(IP);
			//连接
			conn.connect();
			//认证
			flg=conn.authenticateWithPassword(userName, userPwd);
			if(flg){
				log.info("========= Connection "+ip+" successful =========");
				return conn;
			}
			log.error("========= Connection "+ip+" failed =========");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return conn;
	}

	/**
	 * 远程执行shell脚本或命令
	 * @return 	true	-> 执行成功
	 */
	public static boolean execute(){
		Connection conn = login();
		if (conn ==null){
			return false;
		}
		return execute(conn,CMD);
	}

	/**
	 * 远程执行shell脚本或者命令
	 * @param cmd 	即将执行的命令
	 * @return	true	-> 执行成功
	 */
	public static boolean execute(@NotNull Connection conn, String cmd){
		String result="";
		try {
			Session session= conn.openSession();
			//执行命令
			session.execCommand(cmd);
			result=processStdout(new StreamGobbler(session.getStdout()),ENCODING);
			//如果为得到标准输出为空，说明脚本执行出错了
			if(StringUtils.isEmpty(result)){
				log.info("得到标准输出为空,链接conn:"+conn+",执行的命令："+cmd);
				result=processStdout(session.getStderr(),ENCODING);
			}else{
				log.info("执行命令成功,链接conn:"+conn+",执行的命令："+cmd);
			}
			conn.close();
			session.close();
			log.info("---------------------语音引擎执行日志Start---------------------");
			log.info(result);
			log.info("---------------------语音引擎执行日志 End---------------------");
		} catch (IOException e) {
			log.info("执行命令失败,链接conn:"+conn+",执行的命令："+cmd+"  "+e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 解析脚本执行返回的结果集  （语音引擎执行日志）
	 * @param in 输入流对象 
	 * @param charset 编码 
	 * @return 	以纯文本的格式返回
	 */
	private static String processStdout(InputStream in, String charset){
		InputStream  stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
			String line=null;
			while((line=br.readLine()) != null){
				buffer.append(line+"\n");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("解析脚本出错："+e.getMessage());
		} catch (IOException e) {
			log.error("解析脚本出错："+e.getMessage());
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		Connection con = RemoteCmdUtil.login(IP,USERNAME,PASSWORD);
		if(con.isAuthenticationComplete()) {
			RemoteCmdUtil.execute(con,CMD);
		}
	}
}
