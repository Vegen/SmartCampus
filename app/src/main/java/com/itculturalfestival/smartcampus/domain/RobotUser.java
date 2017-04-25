package com.itculturalfestival.smartcampus.domain;

import com.hyphenate.easeui.domain.EaseUser;

/**
 * @creation_time: 2017/4/21
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class RobotUser extends EaseUser {
    public RobotUser(String username) {
        super(username.toLowerCase());
    }
}
