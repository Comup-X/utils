package com.czy.common.utils;

/**
 * Created by Comup on 2016/12/29.
 */
public class StringUtil {

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
        return (str == null || str.trim().equals("")) ? true : false;
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
     * @param src
     * @param needTrimString
     * @return
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
}
