package com.satou.materialcatalog.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.satou.materialcatalog.Constant;
import com.satou.materialcatalog.base.Application;
import com.satou.materialcatalog.model.entity.SaveStruct;
import com.satou.materialcatalog.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsuki on 2018/3/14.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String NAME = "CatalogData.db";
    private static final int VERSION = 1;

    private final String BASETABLE = "basedata";
    private final String TYPETABLE = "typetable";
    private final String SCENETABLE = "scenetable";
    private final String TYPEDATA = "typedata";
    private final String SCENEDATA = "scenedata";

    private DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    private static class DatabaseHelperHolder {
        private static final DatabaseHelper INSTANCE = new DatabaseHelper(Application.getInstance());
    }

    public synchronized static final DatabaseHelper getInstance() {
        return DatabaseHelperHolder.INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate");
        //创建基础数据表
        String createBaseDataTable = "create table " +
                BASETABLE + "(id INTEGER PRIMARY KEY autoincrement NOT NULL," +
                "name TEXT NOT NULL,season TEXT NOT NULL," +
                "episode TEXT NOT NULL,time TEXT NOT NULL)";
        //创建类型表
        String createTypeTalbe = "create table " +
                TYPETABLE + "(name TEXT PRIMARY KEY UNIQUE NOT NULL)";
        //创建场景表
        String createSceneTable = "create table " +
                SCENETABLE + "(name TEXT PRIMARY KEY UNIQUE NOT NULL)";
        //创建类型数据表
        String createTypeDataTable = "create table " +
                TYPEDATA + "(id INTEGER PRIMARY KEY autoincrement NOT NULL," +
                "name TEXT NOT NULL,bid INTEGER NOT NULL,time TEXT NOT NULL," +
                "FOREIGN KEY(name) REFERENCES " + TYPETABLE + "(name)," +
                "FOREIGN KEY(bid) REFERENCES " + BASETABLE + "(id))";
        //创建场景数据表
        String createSceneDataTable = "create table " +
                SCENEDATA + "(id INTEGER PRIMARY KEY autoincrement NOT NULL," +
                "name TEXT NOT NULL,bid INTEGER NOT NULL,time TEXT NOT NULL," +
                "FOREIGN KEY(name) REFERENCES " + SCENETABLE + "(name)," +
                "FOREIGN KEY(bid) REFERENCES " + BASETABLE + "(id))";
        sqLiteDatabase.execSQL(createBaseDataTable);
        sqLiteDatabase.execSQL(createTypeTalbe);
        sqLiteDatabase.execSQL(createSceneTable);
        sqLiteDatabase.execSQL(createTypeDataTable);
        sqLiteDatabase.execSQL(createSceneDataTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS study");
        onCreate(sqLiteDatabase);
    }

    public synchronized long addNormalData(String str, int page) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", str + "");
            long l = -1;
            if (page == Constant.REQUEST_CODE_ADD_TYPE)
                l = db.insert(TYPETABLE, null, cv);
            else if (page == Constant.REQUEST_CODE_ADD_SCENE)
                l = db.insert(SCENETABLE, null, cv);
            return l;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public synchronized List<String> getNormalData(int page) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            List<String> data = new ArrayList<>();

            if (page == Constant.REQUEST_CODE_ADD_TYPE)
                cursor = db.query(TYPETABLE, null, null, null, null, null, null);
            else if (page == Constant.REQUEST_CODE_ADD_SCENE)
                cursor = db.query(SCENETABLE, null, null, null, null, null, null);
            if (cursor == null) {
                db.close();
                return data;
            }
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null)
                    data.add(cursor.getString(0));
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            cursor.close();
            db.close();
        }
    }

    public synchronized int delNormalData(String str, int page) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            int i = 0;
            String whereClause = "name=?";
            String[] whereArgs = {str};
            if (page == Constant.REQUEST_CODE_ADD_TYPE)
                i = db.delete(TYPETABLE, whereClause, whereArgs);
            else if (page == Constant.REQUEST_CODE_ADD_SCENE)
                i = db.delete(SCENETABLE, whereClause, whereArgs);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            db.close();
        }
    }

    public synchronized long subData(SaveStruct saveStruct) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            if (saveStruct == null) return -1;

            ContentValues cv = new ContentValues();
            cv.put("name", saveStruct.getName() + "");
            cv.put("season", saveStruct.getSeason() + "");
            cv.put("episode", saveStruct.getEpisode() + "");
            cv.put("time", saveStruct.getTime() + "");
            db.insert(BASETABLE, null, cv);
            String[] columns = {"id"};
            String selection = "name=? and season=? and episode=? and time=?";
            String[] selectionArgs = {saveStruct.getName() + "", saveStruct.getSeason() + "",
                    saveStruct.getEpisode() + "", saveStruct.getTime() + ""};
            cursor = db.query(BASETABLE,  columns, selection, selectionArgs, null, null, null);
            cursor.moveToFirst();
            for (String type : saveStruct.getType()){
                cv.clear();
                cv.put("name", type);
                cv.put("bid", cursor.getInt(0));
                cv.put("time", System.currentTimeMillis());
                db.insert(TYPEDATA, null, cv);
            }
            for (String scene : saveStruct.getScene()){
                cv.clear();
                cv.put("name", scene);
                cv.put("bid", cursor.getInt(0));
                cv.put("time", System.currentTimeMillis());
                db.insert(SCENEDATA, null, cv);
            }
            db.setTransactionSuccessful();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            db.endTransaction();
            cursor.close();
            db.close();

        }
    }
}
