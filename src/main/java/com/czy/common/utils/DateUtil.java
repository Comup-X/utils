package com.czy.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
        return formatter.format(dateToLocalDateTime(date));
    }

    /**
     * 获取Date的时区
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
     * @param zoneId        时区
     * @return 转换后的Date
     */
    public static Date localDateTimeToDate(final LocalDateTime localDateTime, final ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
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
     * @param zoneId    时区
     * @return 转换后的Date
     */
    public static Date localDateToDate(final LocalDate localDate, final ZoneId zoneId) {
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
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
}