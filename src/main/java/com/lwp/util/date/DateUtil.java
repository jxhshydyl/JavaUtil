package com.lwp.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * User: liuweipeng
 * Date: 2019/6/11
 * Description:
 */
public class DateUtil {
    /**
     * 得到指定的时间字符串的时间戳
     *
     * @param strDate
     * @return
     */
    public static long getTimeMillis(String strDate) {
        long ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(strDate);
            ts = date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ts;
    }

    /**
     * 得到指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static String getWeekBeginDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 默认以星期一为一周的开始
        return getWeekBeginDate(date, 2);
    }

    /**
     *  得到指定日期所在周的第一天(可选择以周日或周一作为一星期的第一天)
     * @param date
     * @param weekStart 星期开始，1=星期日，2=星期一
     * @return
     */
    public static String getWeekBeginDate(Date date, int weekStart) {
        if (weekStart<1 || weekStart>2) {
            weekStart = 1;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);

        if (weekStart==1) {
            day += 1;
        }

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 得到指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getWeekEndDate(Date date) {
        return getWeekEndDate(date, 2);
    }

    /**
     *  得到指定日期所在周的最后一天(可选择以周日或周一作为一星期的第一天)
     * @param date
     * @param weekStart 星期开始，1=星期日，2=星期一
     * @return
     */
    public static String getWeekEndDate(Date date, int weekStart) {
        if (weekStart<1 || weekStart>2) {
            weekStart = 1;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);

        if (weekStart==1) {
            day += 1;
        }

        System.out.println("day:"+day);
        System.out.println("cal.getFirstDayOfWeek():"+cal.getFirstDayOfWeek());
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 7 - day + 1);
        String imptimeEnd = sdf.format(cal.getTime());
        return imptimeEnd;
    }

    /**
     * 得到指定月份的最后一天
     *
     * @param date
     * @return
     */
    public static String getMonthEndDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            //设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            //格式化日期
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf1.format(cal.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到指定月份的第一天
     * @param date
     * @return
     */
    public static String getMonthBeginDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            //获取某月最小天数
            int firstDay = cal.getMinimum(Calendar.DATE);
            //设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);
            //格式化日期
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf1.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 得到指定月份的最后一天
     *
     * @param date
     * @return
     */
    public static String getyyyyMMddMonthEndDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DATE);
            //设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            //格式化日期
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf1.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到指定月份的第一天
     *
     * @param date
     * @return
     */
    public static String getyyyyMMddMonthBeginDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            //获取某月最小天数
            int firstDay = cal.getMinimum(Calendar.DATE);
            //设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);
            //格式化日期
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf1.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 得到指定的时间字符串的时间戳
     *
     * @return
     */
    public static String getyyyyMMddTime(String date) {
        String format = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(date);
            format = simpleDateFormat.format(date1);

        } catch (Exception e) {

        }
        return format;
    }

    public static String formatDateTime(long mss) {
        String DateTimes = null;
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if(hours==0 && minutes==0){
            DateTimes="0";
        }
        if (hours > 0) {
            DateTimes = hours + "小时" + minutes + "分钟";
        }
        if (hours ==0 && minutes > 0) {
            DateTimes = minutes + "分钟";
        }
        return DateTimes;
    }

    /**
     * 将Date转换成年月日时分秒格式的字符串
     * @return
     */
    public static String yyyyMMddDateToString(Date date){
        String format = null;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            format = simpleDateFormat.format(date);

        }catch (Exception e){

        }
        return format;
    }

    /**
     * 将年月日时分秒格式的字符串转换成Date
     * @return
     */
    public static Date yyyyMMddStringToDate(String date){
        Date format = null;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            format = simpleDateFormat.parse(date);

        }catch (Exception e){

        }
        return format;
    }

    /**
     * 计算两个日期相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int calculateDays(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {   //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {    //闰年
                    timeDistance += 366;
                } else {    //不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {    //不同年
            return day2 - day1;
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getMonthDiff(String beginTime,String endTime){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(sdf.parse(beginTime));
            aft.setTime(sdf.parse(endTime));
            int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            surplus = surplus <= 0 ? 1 : 0;
            return (Math.abs(month + result) + surplus);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据字符串日期得到是星期几
     * @param date
     */
    public static String getWeek(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse=new Date();
            if(date!= null && !"".equals(date)){
                parse = sdf.parse(date);
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE", Locale.CHINA);
            String week = sdf1.format(parse);
            return week;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args){
        String week = DateUtil.getWeek("2019-06-23");
        System.out.println(week);
    }

}