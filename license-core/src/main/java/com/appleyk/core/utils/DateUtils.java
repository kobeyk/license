package com.appleyk.core.utils;

import com.appleyk.core.ex.CommonException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>日期工具类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:40 下午 2020/8/21
 */
public class DateUtils {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    private static DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static Calendar calendar = Calendar.getInstance();

    public DateUtils() {
    }

    public static synchronized String getCurrentDateForFile() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        return sDateFormat.format(date);
    }

    public static Long getTime(String time) throws CommonException {
        if (CommonUtils.isEmpty(time)) {
            throw new CommonException("时间[" + time + "]格式不合法");
        } else if (time.length() < 11) {
            dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parse = LocalDate.parse(time, dtF);
            return LocalDate.from(parse).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } else {
            LocalDateTime parse = LocalDateTime.parse(time, dtF);
            return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }

    public static synchronized String getDate() {
        Date date = new Date();
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(date);
    }

    public static synchronized String date2Str(Long time) {
        Date date = new Date(time);
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(date);
    }

    public static synchronized String date2Str(Date time) {
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(time);
    }

    public static synchronized Date str2Date(String time) throws CommonException {
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return sDateFormat.parse(time);
        } catch (Exception var2) {
            throw new CommonException("字符串[" + time + "]转换日期格式异常");
        }
    }

    public static Date addYear(Date date, int mount) {
        calendar.setTime(date);
        calendar.add(1, mount);
        return calendar.getTime();
    }

    public static Date addYear(Long time, int mount) {
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(1, mount);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int mount) {
        calendar.setTime(date);
        calendar.add(2, mount);
        return calendar.getTime();
    }

    public static Date addMonth(Long time, int mount) {
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(1, mount);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int mount) {
        calendar.setTime(date);
        calendar.add(5, mount);
        return calendar.getTime();
    }

    public static Date addDay(Long time, int mount) {
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(5, mount);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Long time = 1555588742901L;
        date2Str(time);
    }
}
