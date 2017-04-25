package com.itculturalfestival.smartcampus.entity.db;

import android.content.Context;

import com.hyphenate.easeui.domain.EaseUser;
import com.itculturalfestival.smartcampus.domain.RobotUser;

import java.util.List;
import java.util.Map;

/**
 * @creation_time: 2017/4/21
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class UserDao {
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";

    public static final String PREF_TABLE_NAME = "pref";
    public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
    public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

    public static final String ROBOT_TABLE_NAME = "robots";
    public static final String ROBOT_COLUMN_NAME_ID = "username";
    public static final String ROBOT_COLUMN_NAME_NICK = "nick";
    public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";


    public UserDao(Context context) {
    }

    /**
     * save contact list
     *
     * @param contactList
     */
    public void saveContactList(List<EaseUser> contactList) {
        DBManager.getInstance().saveContactList(contactList);
    }

    /**
     * get contact list
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {

        return DBManager.getInstance().getContactList();
    }

    /**
     * delete a contact
     * @param username
     */
    public void deleteContact(String username){
        DBManager.getInstance().deleteContact(username);
    }

    /**
     * save a contact
     * @param user
     */
    public void saveContact(EaseUser user){
        DBManager.getInstance().saveContact(user);
    }

    public void setDisabledGroups(List<String> groups){
        DBManager.getInstance().setDisabledGroups(groups);
    }

    public List<String>  getDisabledGroups(){
        return DBManager.getInstance().getDisabledGroups();
    }

    public void setDisabledIds(List<String> ids){
        DBManager.getInstance().setDisabledIds(ids);
    }

    public List<String> getDisabledIds(){
        return DBManager.getInstance().getDisabledIds();
    }

    public Map<String, RobotUser> getRobotUser(){
        return DBManager.getInstance().getRobotList();
    }

    public void saveRobotUser(List<RobotUser> robotList){
        DBManager.getInstance().saveRobotList(robotList);
    }
}
