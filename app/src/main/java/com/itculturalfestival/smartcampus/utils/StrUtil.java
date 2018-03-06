package com.itculturalfestival.smartcampus.utils;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 字符串转换
 */

public class StrUtil {
    public static int str2Int(String str){
        int result=0;
        if(str!=null&&!str.equals("")){
            result=Integer.parseInt(str);
        }
        return result;
    }

    public static int str2fInt(String str){
        int result=0;
        if(str!=null&&!str.equals("")){
            float aFloat = Float.parseFloat(str);
            result = (int)aFloat;
        }
        return result;
    }

    public static Double str2Double(String str){
        Double result=0.0;
        if(str!=null&&!str.equals("")){
            result=Double.parseDouble(str);
        }
        return result;
    }
    public static Float str2Float(String str){
        Float result=(float)0;
        if(str!=null&&!str.equals("")){
            result=Float.parseFloat(str);
        }
        return result;
    }
}
