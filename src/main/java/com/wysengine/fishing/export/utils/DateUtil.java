package com.wysengine.fishing.export.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月4日 上午11:14:12 
*  
*/

public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	public final static int YEAR = 0;
	public final static int MONTH = 1;
	public final static int DAY = 2;
	public final static int HOUR = 3;
	public final static int MINUTE = 4;
	public final static int SECOND = 5;
	public final static int MILLISECOND = 6;
	/**
	 * yyyyMMddHHmmss
	 */
	public final static String longDateTimeFormat = "yyyyMMddHHmmss";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String longDateTimeFormatWithSplit = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	public final static String longDateTimeFormatMilliSecondWithSplit = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * yyyy-MM-dd
	 */
	public final static String dateFormatWitSplit = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	public final static String dateFormat = "yyyyMMdd";
	/**
	 * yyyy-MM
	 */
	public final static String dataFormatMonth = "yyyy-MM";
	/**
	 * yyyy-MM-dd-HH-mm-ss
	 */
	public final static String stringFormat = "yyyy-MM-dd-HH-mm-ss";
	
	public static TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
	
	/**
	 * getYear
	 * @return
	 */
	public static int getYear(){
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * get year
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * getMonth
	 * @return
	 */
	public static int getMonth(){
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * get month
	 * @return
	 */
	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * getMonth
	 * @param now
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static int getMonth(String now, String format) throws ParseException {
		Calendar calendar = Calendar.getInstance(timeZone);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		Date current = dateFormat.parse(now);
		calendar.setTime(current);
	
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * now
	 * @return now
	 */
	public static Date now() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.getTime();
	}

	/**
	 * yyyyMMdd
	 * @return
	 */
	public static String getDateFormat() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.dateFormat);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}
	
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public static String getDateFormatWitSplit() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.dateFormatWitSplit);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}

	/**
	 * yyyy-MM
	 * @return
	 */
	public static String getDateFormatYearMonth() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.dataFormatMonth);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}

	/**
	 * yyyyMMddHHmmss
	 * @return
	 */
	public static String getLongDateTimeFormat() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.longDateTimeFormat);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getLongDateTimeFormatWithSplit() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.longDateTimeFormatWithSplit);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static String getLongDateTimeFormatMilliSecondWithSplit() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.longDateTimeFormatMilliSecondWithSplit);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(new Date());
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return Date type
	 */
	public static Date parseDate(String date, String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error(e.toString(), e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 */
	public static String convert(Long timestamp, String format) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return String format
	 */
	public static String formatDateString(Date date, String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(date);
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的第一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(int month, String format){
		Calendar calendar = Calendar.getInstance(timeZone);
		
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的第一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(int month, String format, Date date){
		Calendar calendar = Calendar.getInstance(timeZone);
		
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的第一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String getFirstDayOfMonth(
			int month, 
			String format, 
			String date, 
			String formatInput) throws ParseException{
		Calendar calendar = Calendar.getInstance(timeZone);
		
		DateFormat dateFormatInput = new SimpleDateFormat(formatInput);
		dateFormatInput.setTimeZone(timeZone);
		Date input = dateFormatInput.parse(date);
		calendar.setTime(input);
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的最后一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int month, String format){
		Calendar calendar = Calendar.getInstance(timeZone);
		
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的最后一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int month, String format, Date date){
		Calendar calendar = Calendar.getInstance(timeZone);
		
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 根据当前日期加上参数month后的日期，获取该月份的最后一天
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @return
	 * @throws ParseException 
	 */
	public static String getLastDayOfMonth(
			int month, 
			String format, 
			String date, 
			String formatInput) throws ParseException{
		Calendar calendar = Calendar.getInstance(timeZone);
		
		DateFormat dateFormatInput = new SimpleDateFormat(formatInput);
		dateFormatInput.setTimeZone(timeZone);
		Date input = dateFormatInput.parse(date);
		calendar.setTime(input);
		calendar.add(Calendar.MONTH, month);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 计算传入参数的2个日期的间隔时间，间隔的单位由dataType参数决定
	 * @param bigDate
	 * @param smallDate
	 * @param dateType
	 * @return
	 */
	public static float getDiffByDateType(Date bigDate, Date smallDate, int dateType){
		float diff = 0;
		long diffMillisecond = 0;
		diffMillisecond = bigDate.getTime() - smallDate.getTime();
		
		switch(dateType){
			case DateUtil.YEAR:
				diff = (float) (diffMillisecond / (1000 * 60 * 60 * 24 * 365));
				break;
			case DateUtil.MONTH:
				diff = (float) (diffMillisecond / (1000 * 60 * 60 * 24 * 30));
				break;
			case DateUtil.DAY:
				diff = (float) (diffMillisecond / (1000 * 60 * 60 * 24));
				break;
			case DateUtil.HOUR:
				diff = (float) (diffMillisecond / (1000 * 60 * 60));
				break;
			case DateUtil.MINUTE:
				diff = (float) (diffMillisecond / (1000 * 60));
				break;
			case DateUtil.SECOND:
				diff = (float) (diffMillisecond / 1000);
				break;
			case DateUtil.MILLISECOND:
				diff = (float) diffMillisecond;
				break;
			default:
				diff = -1;
				break;
		}
		return diff;
	}
	
	/**
	 * <li>功能描述：时间相减得到天数
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 * long 
	 * @author Administrator
	 * @throws ParseException 
	 */
	public static long getDaySub(String beginDateStr,String endDateStr) throws ParseException{
		long day=0;
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.dateFormatWitSplit);
		dateFormat.setTimeZone(timeZone);
		Date beginDate = null;
		Date endDate = null;
		
		beginDate = dateFormat.parse(beginDateStr);
		endDate= dateFormat.parse(endDateStr);
		
		day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
		
		return day;
	}
	
	/**
	 * 根据当前日期获取给定月后的日期
	 * @param month 给定月
	 * @param format
	 * @return
	 */
	public static String getAfterMonthDate(int month, String format){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.add(Calendar.MONTH, month);
		
		Date targetDate = calendar.getTime();
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(targetDate);
	}
	
	/**
	 * 根据给定的日期计算month月后的日期
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @param date
	 * @return
	 */
	public static String getAfterMonthDate(String month, Date date, String format){
		Calendar canlendar = Calendar.getInstance(timeZone);
		
		canlendar.setTime(date);
		canlendar.add(Calendar.MONTH, Integer.parseInt(month));
		DateFormat dateFormatOutput = new SimpleDateFormat(format);
		dateFormatOutput.setTimeZone(timeZone);
		
		return dateFormatOutput.format(canlendar.getTime());
	}
	
	/**
	 * 根据给定的日期计算month月后的日期
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param month
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String getAfterMonthDate(
			String month, 
			String date, 
			String formatInput, 
			String formatOutput) throws ParseException{
		Calendar canlendar = Calendar.getInstance(timeZone);
		
		DateFormat dateFormatInput = new SimpleDateFormat(formatInput);
		dateFormatInput.setTimeZone(timeZone);
		
		Date newDate = dateFormatInput.parse(date);
		canlendar.setTime(newDate);
		canlendar.add(Calendar.MONTH, Integer.parseInt(month));
		
		DateFormat dateFormatOutput = new SimpleDateFormat(formatOutput);
		dateFormatOutput.setTimeZone(timeZone);
		
		return dateFormatOutput.format(canlendar.getTime());
	}
	
	/**
	 * 得到days天之后的日期
	 * @param days 天数，可为负数
	 * @param format 返回日期格式
	 * @return
	 */
	public static String getAfterDayDate(int days, String format) {
		Calendar calendar = Calendar.getInstance(timeZone); // java.util包
		calendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = calendar.getTime();
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		String dateStr = dateFormat.format(date);
		
		return dateStr;
	}

	public static Date getAfterMinDate(int minutes, Date date) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date getAfterSecDate(Date date,int seconds) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/**
	 * 根据给定的日期获取days天后的日期
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 */
	public static String getAfterDayDate(Date date, int days, String format){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		Date target = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(target);
	}
	/**
	 * 根据给定的日期获取days天后的日期
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String getAfterDayDate(
			String date, 
			int days, 
			String formatInput, 
			String formatOutput) throws ParseException{
		Calendar calendar = Calendar.getInstance(timeZone);
		DateFormat dateFormatInput = new SimpleDateFormat(formatInput);
		dateFormatInput.setTimeZone(timeZone);
		
		Date source = dateFormatInput.parse(date);
		calendar.setTime(source);
		calendar.add(Calendar.DATE, days);
		Date target = calendar.getTime();
		DateFormat dateFormat = new SimpleDateFormat(formatOutput);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(target);
	}
	
	/**
	 * 得到n天之后是周几
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(int days) {
		Calendar canlendar = Calendar.getInstance(timeZone); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		
		DateFormat dateFormat = new SimpleDateFormat("E");
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(canlendar.getTime());
	}
	
	/**
	 * 获取hours后的日期
	 * @param fromDate
	 * @param hours
	 * @return
	 */
	public static Date getAfterHourDate(Date fromDate, int hours){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(fromDate);
		calendar.add(Calendar.HOUR, hours);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间hours之前的日期
	 * @param hours
	 * @return
	 */
	public static Date getAfterHourDate(int hours){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.add(Calendar.HOUR, hours);
		
		return calendar.getTime();
	}
	
	/**
	 * 获取传入时间hours之前的日期
	 * @param date
	 * @param format
	 * @param hours
	 * @return
	 */
	public static String getAfterHourDateString(String date, String format, int hours){
		Date fromDate = DateUtil.parseDate(date, DateUtil.longDateTimeFormatWithSplit);
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(fromDate);
		calendar.add(Calendar.HOUR, hours);
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * get the week of today
	 * @return
	 */
	public static String getWeekOfToday(){
		Calendar canlendar = Calendar.getInstance(timeZone);
		DateFormat dateFormat = new SimpleDateFormat("E");
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(canlendar.getTime());
	}
	
	/**
	 * 日期比较，如果s>=e 返回true 否则返回false
	 * @param first
	 * @param formatFirst
	 * @param second
	 * @param formatSecond
	 * @return
	 */
	public static boolean compareDate(
			String first, 
			String formatFirst, 
			String second, 
			String formatSecond) {
		Date firstDate = parseDate(first, formatFirst);
		Date secondDate = parseDate(second, formatSecond);
		if(firstDate == null || secondDate == null){
			return false;
		}
		
		return firstDate.getTime() >= secondDate.getTime();
	}
	
	/**
	 * 根据月份获取该月共有多少小时
	 * @param month format "2017-01"
	 * @return
	 */
	public static int getHoursByMonth(String month) {
		int hours = 0;
		if(month.indexOf("-") > 0) {
			String[] monthArray = month.split("-");
			if(monthArray.length == 2) {
				Calendar calendar = Calendar.getInstance(timeZone);
				int year = Integer.parseInt(monthArray[0]);
				int m = monthArray[1].substring(0, 1).equals("0") ? Integer.parseInt(monthArray[1].substring(1, 2)) : Integer.parseInt(monthArray[1]);
				calendar.set(year, m - 1, 1, 0, 0, 0);
				int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				hours = day * 24;
			}
		}
		
		return hours;
	}
	
	/**
	 * 根据年份获取该年的小时数
	 * @param year
	 * @return
	 */
	public static int getHours(String year) {
		int hours = 0;
		if(StringUtil.notEmpty(year)) {
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.set(Integer.parseInt(year), 0, 1, 0, 0, 0);
			int day = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			
			hours = day * 24;
		}
		
		return hours;
	}
	
	/**
	 * 根据年份和月份获取月有多少小时
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getHours(String year, String month) {
		int hours = 0;
		if(StringUtil.notEmpty(year) && StringUtil.notEmpty(month)) {
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1, 0, 0, 0);
			int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			hours = day * 24;
		}
		
		return hours;
	}
	
	/**
	 * 根据年份获取一年有多少天
	 * @param year
	 * @return
	 */
	public static int getDays(String year) {
		int days = 0;
		if(StringUtil.notEmpty(year)) {
			Calendar first = Calendar.getInstance(timeZone);
			first.set(Integer.parseInt(year), 0, 1, 0, 0, 0);
			
			days = first.getActualMaximum(Calendar.DAY_OF_YEAR);
		}
		
		return days;
	}
	
	/**
	 * 根据年份和月份获取一月有多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDays(String year, String month) {
		int days = 0;
		if(StringUtil.notEmpty(year)) {
			Calendar first = Calendar.getInstance(timeZone);
			first.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1, 0, 0, 0);
			
			days = first.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		return days;
	}
	
	
	/**
	 * 获取给定年月的第一天和最后一天
	 * @param year
	 * @return
	 */
	public static String getDateRange(String year) {
		String dateRange = "";
		if(StringUtil.notEmpty(year)) {
			Calendar first = Calendar.getInstance(timeZone);
			first.set(Integer.parseInt(year), 0, 1);
			
			Calendar end = Calendar.getInstance(timeZone);
			end.set(Integer.parseInt(year), 11, 31);
			
			DateFormat format = new SimpleDateFormat(DateUtil.dateFormatWitSplit);
			format.setTimeZone(timeZone);
			
			dateRange = format.format(first.getTime()) + " --- " + format.format(end.getTime());
		}
		
		return dateRange;
	}
	
	/**
	 * 获取给定年和月的第一天和最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getDateRange(String year, String month) {
		String dateRange = "";
		if(StringUtil.notEmpty(year) && StringUtil.notEmpty(month)) {
			Calendar first = Calendar.getInstance(timeZone);
			first.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1, 0, 0, 0);
			
			Calendar end = Calendar.getInstance(timeZone);
			end.set(Calendar.YEAR, Integer.parseInt(year));
			end.set(Calendar.MONTH, Integer.parseInt(month) - 1);
			end.set(Calendar.DATE, first.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			DateFormat format = new SimpleDateFormat(DateUtil.dateFormatWitSplit);
			format.setTimeZone(timeZone);
			
			dateRange = format.format(first.getTime()) + " --- " + format.format(end.getTime());
		}
		
		return dateRange;
	}
	
	/**
	 * 根据给定年和周获取周一和周日的时间
	 * @param year
	 * @param week
	 * @return
	 */
	public static String getDateRangeWeek(String year, String week) {
		String dateRange = "";
		if(StringUtil.notEmpty(year) && StringUtil.notEmpty(week)) {
			Calendar first = Calendar.getInstance(timeZone);
			first.set(Calendar.YEAR, Integer.parseInt(year));
			first.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			first.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			Calendar end = Calendar.getInstance(timeZone);
			first.set(Calendar.YEAR, Integer.parseInt(year));
			first.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			first.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
			DateFormat format = new SimpleDateFormat(DateUtil.dateFormatWitSplit);
			format.setTimeZone(timeZone);
			
			dateRange = format.format(first.getTime()) + " --- " + format.format(end.getTime());
		}
		
		return dateRange;
	}
	
	public static Long getTimestamp(String datetime, String format) {
		Date date = parseDate(datetime, format);
		
		return date.getTime();
	}
	
	public static String getDateFromTimestamp(String timestamp, String format) {
		String date = "";
		if(StringUtil.notEmpty(timestamp)) {
			Long time = Long.parseLong(timestamp);
			
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.setTimeInMillis(time);
			
			DateFormat dataFormat = new SimpleDateFormat(format);
			dataFormat.setTimeZone(timeZone);
			
			date = dataFormat.format(calendar.getTime());
		}else {
			return null;
		}
		
		return date;
	}
	public static Date getDateFromTimestamp(String timestamp) {
		if(StringUtil.notEmpty(timestamp)) {
			Long time = Long.parseLong(timestamp);
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.setTimeInMillis(time);
			return calendar.getTime();
		}else {
			return null;
		}
	}

}
