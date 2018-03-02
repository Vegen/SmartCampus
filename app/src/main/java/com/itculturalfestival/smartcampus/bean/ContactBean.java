package com.itculturalfestival.smartcampus.bean;

import com.itculturalfestival.smartcampus.utils.StrUtil;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 联系人
 */

public class ContactBean implements Comparable<ContactBean>{
    public String name; //名字
    public String tel;  //电话

    public ContactBean(){}

    public ContactBean(String name, String tel){
        this.name = name;
        this.tel = tel;
    }

    @Override
    public int compareTo(ContactBean another) {
        String l = StrUtil.getFirstPinYin(this.name);
        String r = StrUtil.getFirstPinYin(another.name);
        return l.compareTo(r);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ContactBean){
            ContactBean bean = (ContactBean) o;
            return this.name.equals(bean.name) && this.tel.equals(bean.tel);
        }
        return super.equals(o);
    }
}
