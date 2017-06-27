package com.czy.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具类 2017-05-25
 *
 * @author xlz35429674@gmail.com
 */
public final class DateUtil {

    private static Map<String, DateTimeFormatter> dateTimeFormatterMap = Collections.synchronizedMap(new HashMap<>());

    private static Map<String, DateTimeFormatter> dateTimeFormatterLocalMap = Collections.synchronizedMap(new HashMap<>());

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
        if (date == null || StringUtil.isNullOrEmptyWithSpace(patten)) {
            throw new NullPointerException("date or patten may not be null");
        }
        DateTimeFormatter formatter;
        if (dateTimeFormatterMap.containsKey(patten)) {
            formatter = dateTimeFormatterMap.get(patten);
        } else {
            formatter = DateTimeFormatter.ofPattern(patten);
            dateTimeFormatterMap.put(patten, formatter);
        }
        return formatter.format(dateToLocalDateTime(date));
    }

    public static String dateToString(final Date date, final String patten, Locale locale) {
        if (date == null || StringUtil.isNullOrEmptyWithSpace(patten)) {
            throw new NullPointerException("date or patten may not be null");
        }
        DateTimeFormatter formatter;
        if (dateTimeFormatterLocalMap.containsKey(patten)) {
            formatter = dateTimeFormatterLocalMap.get(patten);
        } else {
            formatter = DateTimeFormatter.ofPattern(patten, locale);
            dateTimeFormatterLocalMap.put(patten, formatter);
        }
        return formatter.format(dateToLocalDateTime(date));
    }

    /**
     * 字符串转指定格式的时间
     *
     * @param date   需要转换的时间
     * @param patten 格式
     * @return 转换后的时间
     */
    public static LocalDateTime StringToLocalDateTime(final String date, final String patten) {
        if (date == null || StringUtil.isNullOrEmptyWithSpace(patten)) {
            throw new NullPointerException("date or patten may not be null");
        }
        DateTimeFormatter formatter;
        if (dateTimeFormatterMap.containsKey(patten)) {
            formatter = dateTimeFormatterMap.get(patten);
        } else {
            formatter = DateTimeFormatter.ofPattern(patten);
            dateTimeFormatterMap.put(patten, formatter);
        }
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * 获取Date的时区
     *
     * @param date 需要获取时区的时间
     * @return 时区信息
     */
    public static ZoneId getZoneId(final Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.getTimeZone().toZoneId();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date 需要转的时间
     * @return 转换后的LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date) {
        return date.toInstant().atZone(getZoneId(date)).toLocalDateTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date   需要转的时间
     * @param zoneId 时区
     * @return 转换后的LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime 需要转换的时间
     * @return 转换后的Date
     */
    public static Date localDateTimeToDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * LocalDate转Date
     *
     * @param localDate 需要转换的时间
     * @return 转换后的Date
     */
    public static Date localDateToDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDate(final Integer hour, final Integer minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime result = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute, 0, 0);
        if (result.isAfter(now)) {
            result = result.minusDays(1);
        }
        return localDateTimeToDate(result);
    }
}