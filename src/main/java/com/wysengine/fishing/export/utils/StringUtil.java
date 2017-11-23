package com.wysengine.fishing.export.utils;

import java.util.Random;
import java.util.regex.Pattern;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月4日 上午11:14:58 
*  
*/

public class StringUtil {
	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0)
		{
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	
			i++;
		}
		return returnStr;
	}
	
	/**
	 * 把数字转化为固定长度的字符串，如sequence=123，length=5，转化后的结果是00123
	 * 
	 * @param length
	 * @param sequence
	 * @return
	 */
	public static String convertToFixSize(Integer length, Integer sequence) {
		String seq = "";
		if (length <= 0) {
			seq = sequence.toString();
		} else {
			Integer len = 0;
			Integer num = sequence;
			while (num > 0) {
				num = num / 10;
				len++;
			}

			if (length - len <= 0) {
				seq = sequence.toString();
			} else {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < length - len; i++) {
					sb.append("0");
				}

				seq = sb.append(sequence.toString()).toString();
			}
		}

		return seq;
	}
	
	public static String getFixLenthString(int strLength) {  
		Random rm = new Random();  
	
		// 获得随机数  
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	
		// 将获得的获得随机数转化为字符串  
		String fixLenthString = String.valueOf(pross);  
		
		// 返回固定的长度的随机数  
		return fixLenthString.substring(1, strLength + 1);  
	}
	
	/**
	 * 判断字符串是否为double
	 * @wh
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;  
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断字符串是否为int
	 * @wh
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (null == str || "".equals(str)) {  
			return false;  
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断字符串是否为数字(int或double)
	 * @wh
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {  
		if (null == str || "".equals(str)) {  
			return false;  
		} 
		if(isInteger(str)||isDouble(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s != null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s == null || "".equals(s) || "null".equals(s);
	}
}
