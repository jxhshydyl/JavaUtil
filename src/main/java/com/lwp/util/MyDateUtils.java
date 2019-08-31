package com.lwp.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author linchong
 * @create 2019-04-01 16:07
 */
public class MyDateUtils {

    private final Calendar instance = Calendar.getInstance();

    //查询两个日期之间的所有日期
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 获取过去某一个时间段的日期
     * 例如获取过去一周，截止到昨天 begin=-7 end=-1
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getDays(int begin,int end) {
        Date yesterday = getdate(-1);
        Date date = getdate(-7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> days = getDays(sdf.format(date), sdf.format(yesterday));
        return days;
    }

    //查询当前月的第一天
    public static String getCurrMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        System.out.println("===============first:" + first);
        return first;
    }


    //获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
    public static Date getdate(int i) {
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, i);
        dat = cd.getTime();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp date = Timestamp.valueOf(dformat.format(dat));
        return date;
    }

    //获取当前周的星期一
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static void main(String[] args) {
        List<String> days = MyDateUtils.getDays("2019-06-29", "2019-06-29");
        System.out.println(days);
    }



    /**
     * 将yyyy-MM-dd格式的日期字符串转换成整数
     * @param date
     * @return
     */
    public static Integer dateToNum(String date) {
        if(date == null || "".equals(date) || !date.contains("-")) {
            return null;
        }
        String[] split = date.split("-");
        Integer result = 0;
        result = Integer.parseInt(split[0]) * 10000 + Integer.parseInt(split[1]) * 100 + Integer.parseInt(split[2]) * 1;
        return result;
    }

}

