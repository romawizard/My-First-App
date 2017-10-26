package ru.roma.vk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;

/**
 * Created by Ilan on 03.09.2017.
 */

public class DB {

    public static final String COLUMN_ID = "_id";
    public static final String FIRST_MAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ON_LINE = "on_line";
    public static final String URL_PHOTO = "URL_photo";
    public static final String USER_ID = "_user_id";
    private static final String DB_NAME = "myvk";
    private static final int DB_VERSION = 1;
    private static final String TAB_FRIEND = "tabfriends";
    private static final String TAB_CREATE =
            "create table " + TAB_FRIEND + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    USER_ID + " integer, " +
                    FIRST_MAME + " text, " +
                    LAST_NAME + " text, " +
                    URL_PHOTO + " text, " +
                    ON_LINE + " integer" +
                    ");";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(TAB_FRIEND, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addFriend(String fistname, String lastname, int user_id, String URL_photo, int on_line) {


        HashSet<Integer> cash = new HashSet<Integer>();

        Cursor cursor = mDB.query(TAB_FRIEND, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {

            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(cursor.getColumnIndex(USER_ID)) == user_id) {
                        ContentValues cv = new ContentValues();
                        cv.put(FIRST_MAME, fistname);
                        cv.put(LAST_NAME, lastname);
                        cv.put(USER_ID, user_id);
                        cv.put(URL_PHOTO, URL_photo);
                        cv.put(ON_LINE, on_line);

                        mDB.update(TAB_FRIEND, cv, USER_ID + " = ?", new String[]{String.valueOf(user_id)});
                    }
                    cash.add(cursor.getInt(cursor.getColumnIndex(USER_ID)));
                } while (cursor.moveToNext());
            }
        }
        if (!cash.contains(user_id)) {

            ContentValues cv = new ContentValues();
            cv.put(FIRST_MAME, fistname);
            cv.put(LAST_NAME, lastname);
            cv.put(USER_ID, user_id);
            cv.put(URL_PHOTO, URL_photo);
            cv.put(ON_LINE, on_line);

            mDB.insert(TAB_FRIEND, null, cv);
        }
        cursor.close();
    }

    // добавить dialogs в DB

    public void addDialogs() {
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(TAB_FRIEND, COLUMN_ID + " = " + id, null);
    }

    public void test(String id){
        String where = USER_ID  + "=?";
        String whereArgs[] = new  String[]{id};
        Cursor cursor = mDB.query(TAB_FRIEND, null,where, whereArgs, null, null, null);
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(TAB_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
