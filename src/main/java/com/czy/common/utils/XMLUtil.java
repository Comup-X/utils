package com.czy.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author chenglixu
 * @version 1.0
 * @FileName:XML转换
 * @function
 * @date 2016年9月2日
 * @Copyright © Spring Airlines;Spring Travel.All rights reserved.
 */
public class XMLUtil {

    /**
     * XML转换为 OBJ
     */
    public static Object xml2obj(Class c, String xml) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return xmlTobj(c, xml);
    }

    public static Object xmlTobj(Class cls, String xml) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        HashMap map = xml2map(xml);
        return map2obj(cls, map);
    }

    public static Object map2obj(Class cls, HashMap map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = cls.newInstance(), ov;
        Method methlist[] = cls.getDeclaredMethods();
        String fname, pname;
        for (int i = 0; i < methlist.length; i++) {
            Method m = methlist[i];
            fname = m.getName();
            pname = "";
            if (fname.indexOf("set") == 0) {
                //pname = fname.substring(3,4).toLowerCase()+fname.substring(4,fname.length()); //大小写
                pname = fname.substring(3, fname.length());    //大写
                ov = map.get(pname);
                m.invoke(o, ov);
            }
        }
        return o;
    }

    public static HashMap xml2map(String xml) {
        HashMap map = new HashMap();
        if (xml == null || xml.length() < 1) return map;
        String ss, se;
        int si, ei, eii, xl = xml.length();
        for (int i = 0; i <= xl; i++) {
            ei = xml.indexOf("</", i);
            if (ei < 0) break;
            i = ei;
            si = -1;
            eii = -1;
            for (int ii = ei; ii >= 0; ii--) {
                if (xml.charAt(ii) == '>') {
                    si = ii;
                    break;
                }
            }
            for (int ii = ei; ii <= xl; ii++) {
                if (xml.charAt(ii) == '>') {
                    eii = ii;
                    break;
                }
            }
            if (si < 0 || eii < 0) break;
            ss = xml.substring(ei + 2, eii);
            se = xml.substring(si + 1, ei);
            i = eii;
            map.put(ss, se.trim());
        }
        return map;
    }
}
