package com.cj.asr.resolver.utils;

/**
 * @author : Flon
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019/03/28 16:58
 */
public enum RoleEnum {

	/**
	 * 后缀 C 代表中文角色，后缀 E 代表英文角色
	 */
	CUSTOMER_C("客户"),
	SERVER_C("坐席"),
	CUSTOMER_E("A"),
	SERVER_E("B");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public static int match(String role){
		if(RoleEnum.CUSTOMER_C.role.equals(role) || RoleEnum.CUSTOMER_E.role.equals(role)){
			return 1;
		}
		if (RoleEnum.SERVER_C.role.equals(role) || RoleEnum.SERVER_E.role.equals(role)){
			return 2;
		}
		return 0;
	}
}
