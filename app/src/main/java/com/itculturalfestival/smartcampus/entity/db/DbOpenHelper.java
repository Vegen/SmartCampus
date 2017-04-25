package com.itculturalfestival.smartcampus.entity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itculturalfestival.smartcampus.other.EaseUiHelper;

/**
 * @creation_time: 2017/4/21
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static DbOpenHelper instance;

    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessgeDao.TABLE_NAME + " ("
            + InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";

    private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
            + UserDao.ROBOT_TABLE_NAME + " ("
            + UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
            + UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";

    private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + UserDao.PREF_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return  EaseUiHelper.getInstance().getCurrentUsernName() + "_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERNAME_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(CREATE_PREF_TABLE);
        db.execSQL(ROBOT_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE "+ UserDao.TABLE_NAME +" ADD COLUMN "+
                    UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
        }

        if(oldVersion < 3){
            db.execSQL(CREATE_PREF_TABLE);
        }
        if(oldVersion < 4){
            db.execSQL(ROBOT_TABLE_CREATE);
        }
        if(oldVersion < 5){
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
        }
        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
        }
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}

