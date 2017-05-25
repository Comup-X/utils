package com.czy.common.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类 2017-05-25
 *
 * @author xlz35429674@gmail.com
 */
public final class DateUtil {

    private static Map<String, DateTimeFormatter> dateTimeFormatterMap = new HashMap<>();

    private DateUtil() {
    }

    /**
     * 时间转指定格式字符串
     *
     * @param date   需要转换的时间
     * @param patten 格式
     * @return 转换后的字符串
     */
    public static String dateToString(final Date date, final String patten) {
        DateTimeFormatter formatter;
        if (dateTimeFormatterMap.containsKey(patten)) {
            formatter = dateTimeFormatterMap.get(patten);
        } else {
            formatter = DateTimeFormatter.ofPattern(patten);
            dateTimeFormatterMap.put(patten, formatter);
        }
        return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}