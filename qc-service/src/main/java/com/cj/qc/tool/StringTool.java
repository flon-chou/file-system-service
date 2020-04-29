package com.cj.qc.tool;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  字符串工具类
 * @author flonc
 */
public class StringTool {


    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return 返回strStart与strEnd之间的字符串，不包含strStart和strEnd
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return 返回strStart与strEnd之间的字符串，包含strStart和strEnd
     */
    public static String subStringBH(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = strStart + str.substring(strStartIndex, strEndIndex).substring(strStart.length())+strEnd;
        return result;
    }

    /**
     * 字符串分割
     * @param pattern
     * @param source
     * @return
     */
    public static List<String> splitString(String pattern, String source){
        if(null==source){
            return new ArrayList<String>();
        }
        String[] arr = source.split(pattern);
        return new ArrayList<String>(Arrays.asList(arr));
    }

    /**
     * 字符串分割，去空白符
     * @param pattern
     * @param source
     * @return
     */
    public static List<String> splitStringByTrim(String pattern,String source){
        if(null==source){
            return new ArrayList<String>();
        }
        String[] arr = source.split(pattern);
        List<String> list = new ArrayList<>();
        for (String s:arr) {
            list.add(s.trim());
        }
        return list;
    }

    /**
     * 字符串分割，去重
     * @param pattern
     * @param source
     * @return
     */
    public static List<String> splitStringByDuplicateRemove(String pattern,String source){
        if(null==source){
            return new ArrayList<String>();
        }
        String[] arr = source.split(pattern);
        List<String> list = new ArrayList<>();
        for (String s:arr) {
            if(!list.contains(s)){
                list.add(s);
            }
        }
        return list;
    }
    /**
     * 字符串分割，去空白符，去重
     * @param splitContent
     * @param source
     * @return
     */
    public static List<String> splitStringByTrimAndDuplicateRemove(String splitContent,String source){
        if(null==source){
            return new ArrayList<String>();
        }
        String[] arr = source.split(splitContent);
        List<String> list = new ArrayList<>();
        for (String s:arr) {
            s = s.trim();
            if(!list.contains(s)){
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 字符串分割，去空白符，去重，分离后第一个元素空则去除
     * @param splitContent
     * @param source
     * @return
     */
    public static List<String> splitStringByTrimAndDuplicateRemoveAndheadTrimRemove(String splitContent,String source){
        if(null==source){
            return new ArrayList<String>();
        }
        List<String> stringList=splitStringByTrimAndDuplicateRemove(splitContent,source);
        if("".equals(stringList.get(0))){
            stringList.remove(0);
        }
        return stringList;
    }

    /**
     * 分离结果列表移除null和空字符串
     * @param splitContent
     * @param source
     * @return
     */
    public static List<String> splitStringByRemoveNullOrEmptyString(String splitContent,String source){
        if(null==source){
            return new ArrayList<String>();
        }
        //去掉所有空格
//        source = source.replaceAll(regex, replacement);
        String[] splitArr=source.split(splitContent);
        List<String> splitList=Arrays.asList(splitArr);
        List<String> resList=new ArrayList<>();
        for (String str : splitList) {
            if (!StringUtils.isBlank(str)) {
                resList.add(str);
            }
        }
        return resList;
    }
    /**
     * ValidationException
     *  将异常信息截取后转换成json对象
     * @param str
     * @return
     */
    public static JSONObject strToJson(String str){
        String json = StringTool.subStringBH(str,"{","}").replace("=",":\"");
        json = json.replace(",","\",");
        json = json.replace("\'","");
        json = json.replace("}","\"}");
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }

    /**
     * ValidationException
     *  将异常信息截取后转换成json对象
     * @param str
     * @return
     */
    public static <T> T strToJson(String str,Class<T> clazz){
        JSONObject jsonObject= JSONObject.fromObject(str);
        return (T) JSONObject.toBean(jsonObject);
    }

    /**
     * 对象转Json格式字符串
     * @param o
     * @return
     */
    public static String toJsonStr(Object o){
        JSONObject jsonObject = JSONObject.fromObject(o);
        return jsonObject.toString();
    }

    /**
     * 判断字符串是否是全英文字母
     * @param str
     * @return
     */
    public static boolean isLetters(String str) {
        char[] chars=str.toCharArray();
        boolean isLetters = false;
        for(int i = 0; i < chars.length; i++) {
            isLetters = (chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z');
            if (!isLetters) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是全数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * 判断字符串是否由英文字母开头，并且只包含字母和下划线
     * @param str
     * @return
     */
    public static boolean isLettersAndUnderline(String str) {
        char[] chars=str.toCharArray();
        boolean isLetters = false;
        for(int i = 0; i < chars.length; i++) {
            if(i==0){
                isLetters = isLetters(String.valueOf(chars[i]));
            }else {
                isLetters = (chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z' || chars[i] == '_');
            }
            if (!isLetters) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断String中是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 从{@value}中获取{@length}位随机字符串
     * @param value   验证码取值范围字符串
     * @param length  验证码长度
     * @return
     */
    public static String getRandomString(String value,int length) {
        Random random = new SecureRandom();
        char[] nonceChars = new char[length];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = value.charAt(random.nextInt(value.length()));
        }
        return new String(nonceChars);
    }

    /**
     * 富文本去除格式获取内容
     * @param str
     * @return
     */
    public static String removeTagForRichText(String str){

        String strRes=str.replaceAll("</?[^>]+>", "");
        strRes=strRes.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return strRes;
    }

    /**
     * 拼接字符串，分离内容与连接符拼接
     * @param splitList 分离内容
     * @param connector 连接符
     * @return
     */
    public static String splitJointString(List<String> splitList,String connector){
        String res="";
        for (int i = 0; i < splitList.size(); i++) {
            if(splitList.size()-1==i){
                res+=splitList.get(i);
                break;
            }
            res+=splitList.get(i)+connector;
        }
        return res;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static void main(String[] args) {
        System.out.println(isLettersAndUnderline("AA_A"));
    }
}
