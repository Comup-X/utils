package com.czy.common.utils.http;

import org.junit.Ignore;
import org.junit.Test;

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
public class HttpUtilTest {

    @Test
    public void doPost() throws Exception {
    }

    @Test
    @Ignore
    public void doGet() throws Exception {
        System.out.println(HttpUtil.doGet("http://www.czy.com", null));
    }

}