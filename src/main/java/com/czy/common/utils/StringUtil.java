package com.czy.common.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Comup on 2016/12/29.
 * 字符串工具类
 */
public class StringUtil {

    private static final Pattern isNumber = Pattern.compile("[0-9]+");


    public static String charArray2String(char[] src){
        StringBuilder stringBuilder  = new StringBuilder();
        for(char c: src){
            if(c=='\u0000'){
                break;
            }
            stringBuilder.append(String.valueOf(c));
        }
        return stringBuilder.toString();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isNullOrEmptyWithSpace(String str) {
        if (str == null)
            return true;
        else {
            String myStr = str.replaceAll(" ", "");
            return isNullOrEmpty(myStr);
        }
    }

    public static boolean isNullOrEmptyWithSpace(String... str) {
        for (String s : str) {
            if (isNullOrEmptyWithSpace(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除指定前导字符和后导字符。如果任一参数为空或null则对源字符串执行trim操作，如果原字符为null则返回null
     *
     * @param src 需要处理的字符串
     * @param needTrimString 需要移除的前导和后导字符
     * @return 处理后的字符
     */
    public static String trim(String src, String needTrimString) {
        if (isNullOrEmptyWithSpace(needTrimString)) {
            return src == null ? null : src.trim();
        }
        if (src.endsWith(needTrimString)) {
            src = src.substring(0, src.length() - 1);
        }
        if (src.startsWith(needTrimString)) {
            src = src.substring(1, src.length());
        }
        return src;
    }

    /**
     * 判断字符串是否为数字
     * @param str 需要验证的字符串
     * @return true：是 false：否
     */
    public static boolean isNumeric(String str) {
        if (Objects.isNull(str)) {
            return false;
        }
        Matcher isNum = isNumber.matcher(str);
        return isNum.matches();
    }
}
