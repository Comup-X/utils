package com.czy.common.utils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * <一句话功能简介><br>
 *
 * @author [004293]004293@ch.com
 * @version [版本号, ${date}]
 * @Description:${todo} <功能详细描述>
 * @ClassName:${type_name}
 * @see [相关类/方法]
 * @since [产品/模块]
 */
public class DateUtilTest {

    @Test
    @Ignore
    public void getZoneId() {
        Assert.assertTrue("DateUtil.getZoneId Error", Objects.equals(DateUtil.getZoneId(new Date()).toString(), "Asia/Shanghai"));
    }

    @Test
    public void dateToString() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.JANUARY, 1, 13, 10, 10);
        Assert.assertTrue("DateUtil.dateToString Error", Objects.equals("17", DateUtil.dateToString(calendar.getTime(), "yy")));
        Assert.assertTrue("DateUtil.dateToString Error", Objects.equals("2017", DateUtil.dateToString(calendar.getTime(), "yyyy")));
        Assert.assertTrue("DateUtil.dateToString Error", Objects.equals("20170101", DateUtil.dateToString(calendar.getTime(), "yyyyMMdd")));
        Assert.assertTrue("DateUtil.dateToString Error", Objects.equals("20170101131010", DateUtil.dateToString(calendar.getTime(), "yyyyMMddHHmmss")));
    }

    @Test
    public void dateToLocalDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.DECEMBER, 25, 9, 10, 10);
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getYear() == 2017;
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getMonthValue() == 12;
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getDayOfMonth() == 25;
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getHour() == 9;
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getMinute() == 10;
        assert DateUtil.dateToLocalDateTime(calendar.getTime()).getSecond() == 10;
    }

    @Test
    public void dateToLocalDateTimeWithZoneId() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MAY, 25, 17, 37, 10);
        LocalDateTime localDateTime = DateUtil.dateToLocalDateTime(calendar.getTime(), ZoneId.of("Asia/Kolkata"));
        assert localDateTime.getYear() == 2017;
        assert localDateTime.getMonthValue() == 5;
        assert localDateTime.getDayOfMonth() == 25;
        assert localDateTime.getHour() == 15;
        assert localDateTime.getMinute() == 7;
        assert localDateTime.getSecond() == 10;
    }

    @Test
    public void localDateTimeToDate() {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 1, 1, 1);
        Date date = DateUtil.localDateTimeToDate(dateTime);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        assert instance.get(Calendar.YEAR) == 2017;
        assert instance.get(Calendar.MONTH) + 1 == 1;
        assert instance.get(Calendar.DAY_OF_MONTH) == 1;
        assert instance.get(Calendar.HOUR) == 1;
        assert instance.get(Calendar.MINUTE) == 1;
        assert instance.get(Calendar.SECOND) == 1;
    }

    @Test
    public void localDateToDate() {
        LocalDate localDate = LocalDate.of(2017, 2, 1);
        Date date = DateUtil.localDateToDate(localDate);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        assert instance.get(Calendar.YEAR) == 2017;
        assert instance.get(Calendar.MONTH) + 1 == 2;
        assert instance.get(Calendar.DAY_OF_MONTH) == 1;
    }
}