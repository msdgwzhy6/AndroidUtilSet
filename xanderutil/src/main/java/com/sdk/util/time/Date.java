package com.sdk.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
/**
 * DATE 继承于 java.util.Date，多实现了很多方法。
 *  沙琪玛 QQ：862990787
 * May 29, 2013 9:52:51 AM
 */
public class Date extends java.util.Date {
     
    /**
     *
     */
    private static final long serialVersionUID = 2155545266875552658L;
     
    /**
     * 功能：转换为Calendar。
     *  沙琪玛 QQ：862990787
     * Aug 21, 2013 8:58:31 AM
     *  Calendar
     */
    public static Calendar toCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c;
    }
     
    /**
     * 功能：判断日期是否和当前date对象在同一天。
     *  沙琪玛 QQ：862990787
     * Aug 21, 2013 7:15:53 AM
     *  date 比较的日期
     *  boolean 如果在返回true，否则返回false。
     */
    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("日期不能为null");
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        return isSameDay(cal2);
    }
     
    /**
     * 功能：判断日期是否和当前date对象在同一天。
     *  沙琪玛 QQ：862990787
     * Aug 21, 2013 7:15:53 AM
     *  cal 比较的日期
     *  boolean 如果在返回true，否则返回false。
     */
    public  static boolean isSameDay(Calendar cal) {
        if (cal == null) {
            throw new IllegalArgumentException("日期不能为null");
        }
        //当前date对象的时间
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        return (cal1.get(Calendar.ERA) == cal.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR));
    }
     
    /**
     * 功能：将当前日期的秒数进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  second 秒数
     *  设置后的日期
     */
    public static Date setSecondNew(int second){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.SECOND,second);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：将当前日期的分钟进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  minute 分钟数
     *  设置后的日期
     */
    public static Date setMinuteNew(int minute){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MINUTE,minute);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：将当前日期的小时进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  hour 小时数 (24小时制)
     *  设置后的日期
     */
    public static Date setHourNew(int hour){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, hour);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：将当前日期的天进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  day 某一天
     *  设置后的日期
     */
    public static Date setDayNew(int day){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE,day);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：将当前日期的月进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  month 某一月
     *  设置后的日期
     */
    public Date setMonthNew(int month){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MONTH, month-1);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：将当前日期的年进行重新设置。
     *  沙琪玛 QQ：862990787
     * Jul 31, 2013 2:42:36 PM
     *  year 某一年
     *  设置后的日期
     */
    public Date setYearNew(int year){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.YEAR, year);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：得到当月有多少天。
     *  沙琪玛 QQ：862990787
     * Jul 2, 2013 4:59:41 PM
     *  int
     */
    public static int daysNumOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.getActualMaximum(Calendar.DATE);
    }
     
    /**
     *  dateStr 时间字符串
     *  dataFormat 当前时间字符串的格式。
     *  net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr,SimpleDateFormat dataFormat){
        try {
            java.util.Date d = dataFormat.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
     
    /**
     *  dateStr yyyy-MM-dd HH:mm:ss字符串
     *  net.maxt.util.Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
     
     

     
    /**
     * 功能：当前时间增加毫秒数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  milliseconds 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addMilliseconds(int milliseconds){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND)+milliseconds);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加秒数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  seconds 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addSeconds(int seconds){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND)+seconds);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加分钟数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  minutes 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addMinutes(int minutes){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+minutes);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加小时数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  hours 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addHours(int hours){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR)+hours);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加天数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  days 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addDays(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.DATE, c.get(Calendar.DATE)+days);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加月数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  months 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addMonths(int months){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+months);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 功能：当前时间增加年数。注意遇到2月29日情况，系统会自动延后或者减少一天。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:26:27 AM
     *  years 正值时时间延后，负值时时间提前。
     *  Date
     */
    public Date addYears(int years){
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR)+years);
        return new Date(c.getTimeInMillis());
    }
     

    public int secondInt() {
        return Integer.parseInt(toString("ss"));
    }
     
    /**
     *  int
     */
    public int minuteInt() {
        return Integer.parseInt(toString("mm"));
    }
     
    /**
     *  int
     */
    public int hourInt() {
        return Integer.parseInt(toString("HH"));
    }
     
    /**
     * 注意：这里1日返回1,2日返回2。
     *  int
     */
    public int dayInt() {
        return Integer.parseInt(toString("dd"));
    }
     
    /**
     * 注意：这里1月返回1,2月返回2。
     *  int
     */
    public int monthInt() {
        return Integer.parseInt(toString("MM"));
    }
     
    /**
     * 得到年。格式：2013
     *  int
     */
    public int yearInt() {
        return Integer.parseInt(toString("yyyy"));
    }
     
    /**
     * 得到短时间。格式：12:01
     *  String
     */
    public String shortTime() {
        return toString("HH:mm");
    }
     
    /**
     * 得到长时间。格式：12:01:01
     *  String
     */
    public String longTime() {
        return toString("HH:mm:ss");
    }
     
    /**
     * 得到今天的第一秒的时间。
     *  Date
     */
    public Date dayStart() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 得到当前所在自然月的第一天的开始,格式为长日期格式。例如：2012-03-01 00:00:00。
     *  Date
     */
    public Date monthStart(){
        Calendar c=Calendar.getInstance();
        String startStr= toString("yyyy-M-")+c.getActualMinimum(Calendar.DATE)+" 00:00:00";
        return Date.parseDate(startStr);
    }
 
    /**
     * 得到今天的最后一秒的时间。
     *  Date
     */
    public Date dayEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(this);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return new Date(c.getTimeInMillis());
    }
     
    /**
     * 7, 1, 2, 3, 4, 5, 6
     *  Integer 如：6
     */
    public int dayOfWeekInt() {
        Integer dayNames[] = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }
     
    /**
     * 将日期转换成长日期字符串 例如：2009-09-09 01:01:01
     *  String
     */
    public String toLongDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (null == this) ? null : df.format(this);
    }
     
    /**
     * 例如想将时间格式化为2012-03-05 12:56 ,则只需要传入formate为yyyy-MM-dd HH:mm即可。
     *  formate 格式化格式，如：yyyy-MM-dd HH:mm
     *  String 格式后的日期字符串。如果当前对象为null，则直接返回null。
     */
    public String toString(String formate) {
        DateFormat df = new SimpleDateFormat(formate);
        return (null == this) ? null : df.format(this);
    }
 
    /**
     * 得到某个时间的时间戳yyyyMMddHHmmss。
     *  String 如果当前对象为null，则直接返回null。
     */
    public String toTimeStamp() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return (null == this) ? null : df.format(this);
    }
     
    /**
     * 将日期转换成短日期字符串,例如：2009-09-09。
     *  String ,如果当前对象为null，返回null。
     */
    public String toShortDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return (null == this) ? null : df.format(this);
    }
     
    /**
     * 功能：用java.util.Date进行构造。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 10:59:05 AM
     *   date
     */
    public Date(java.util.Date date) {
        super(date.getTime());
    }
     
    /**
     * 功能：用毫秒进行构造。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 10:59:05 AM
     *  timeInMillis
     */
    public Date(long timeInMillis) {
        super(timeInMillis);
    }
 
 
    /**
     * 功能：默认构造函数。
     *  沙琪玛 QQ：862990787
     * May 29, 2013 11:00:05 AM
     */
    public Date() {
        super();
    }
     
}
