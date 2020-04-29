package com.cj.asr.resolver.utils;

/**
 * @author : Flon
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019/03/28 17:18
 */
public enum GenderEnum {

	/**
	 *  性别
	 */
	MALE("男"),FEMALE("女");

	private String gender;

	GenderEnum(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public static int match(String gender){
		if(GenderEnum.MALE.gender.equals(gender)){
			return 1;
		}
		if (GenderEnum.FEMALE.gender.equals(gender)){
			return 0;
		}
		return 2;
	}
}
