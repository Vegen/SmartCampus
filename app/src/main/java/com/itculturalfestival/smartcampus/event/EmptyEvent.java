package com.itculturalfestival.smartcampus.event;

/**
 * Created by vegen on 2018/3/5.
 */

public class EmptyEvent {
    String className;

    public EmptyEvent(String aClass) {
        this.className = aClass;
    }

    public String getClassName() {
        return className;
    }
}
