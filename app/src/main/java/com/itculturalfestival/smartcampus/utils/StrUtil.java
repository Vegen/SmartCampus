package com.itculturalfestival.smartcampus.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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


    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstPinYin(String chinese) {
        L.e("tag","名字 " + chinese);
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < 1; i++) {
            if (arr[i] > 128) {
                L.e("tag","arr " + arr[i]);
                try {
//                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0].charAt(0));
//                    if (_t != null && _t[0] == "") {
//                        pybf.append(_t[0].charAt(0));
//                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        String aCase = pybf.toString().replaceAll("\\W", "").trim().toUpperCase();
        L.e("tag","大写字母 aCase " + aCase);
        //转为大写字母
        return aCase;
    }

    /**
     * 获取汉字串拼音，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getPinYin(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);       //大写字母
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);    //没有音标
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }
}
