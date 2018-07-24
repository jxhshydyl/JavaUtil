package com.lwp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: liuweipeng
 * @Date: 2018/6/22 17:20
 * @Description: 时间工具类
 */
public class DateUtil {
    /**
     * 得到当前日期的年 月 日
     * @return
     */
    public static String getCurrentYYYY_MM_DD(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
    /**
     * 得到当前日期的年 月 日 时 分 秒
     * @return
     */
    public static String getCurrentYYYY_MM_DD_HH_mm_ss(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
    /**
     * 把指定字符串转为时间对象
     * @return
     */
    public static Date getAppointDateAndTime(String date){
        Date parse = null;
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
            parse = simpleDateFormat.parse(date);
            return parse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 把指定字符串转为时间对象
     * @return
     */
    public static Date getAppointDate(String date){
        Date parse = null;
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd");
            parse = simpleDateFormat.parse(date);
            return parse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 把指定字符串转为时间戳
     * @return
     */
    public static Long getStringDateTimeStamp(String date){
        Long time_stamp=0L;
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd");
            Date parse = simpleDateFormat.parse(date);
            time_stamp = parse.getTime();
            return time_stamp;
        }catch (Exception e){
            e.printStackTrace();
        }
        return time_stamp;
    }
    /**
     * 把指定字符串转为时间戳
     * @return
     */
    public static Long getStringTimeTimeStamp(String date){
        Long time_stamp=0L;
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
            Date parse = simpleDateFormat.parse(date);
            time_stamp = parse.getTime();
            return time_stamp;
        }catch (Exception e){
            e.printStackTrace();
        }
        return time_stamp;
    }
    public static void main(String[] args){
        System.out.println(DateUtil.getCurrentYYYY_MM_DD());
        System.out.println(DateUtil.getCurrentYYYY_MM_DD_HH_mm_ss());
        System.out.println(DateUtil.getAppointDateAndTime("2018_07_24 15:32:31"));
        System.out.println(DateUtil.getAppointDate("2018_07_24"));
        System.out.println(DateUtil.getStringDateTimeStamp("2018_07_24"));
        System.out.println(DateUtil.getStringTimeTimeStamp("2018_07_24 15:32:31"));
    }
}
