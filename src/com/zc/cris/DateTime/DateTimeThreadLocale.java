package com.zc.cris.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 将线程和每个 SimpleDateFormat 对象绑定起来
public class DateTimeThreadLocale {

    public static final ThreadLocal<DateFormat> THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static Date convert(String str) throws ParseException {
        return THREAD_LOCAL.get().parse(str);
    }


}
