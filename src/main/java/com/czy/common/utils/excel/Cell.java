package com.czy.common.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Comup on 2017-05-16.
 * Excel元素设置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cell {
    /**
     * 列名
     * @return 字段列名
     */
    String column();

    /**
     * 顺序
     * @return 字段顺序
     */
    int order();
}
