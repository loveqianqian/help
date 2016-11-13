package com.ghc.gather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
    private static String SimplateDateFormate = "yyyy-MM-dd hh:mm:ss";
    private static String DateFormate = "yyyy-MM-dd";
    private static String ChineseSimpleDateFormate = "MM月dd日HH点";

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormate);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getCurrentTimeChinese() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ChineseSimpleDateFormate);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentTime());
        System.out.println(getCurrentTimeChinese());
    }
}