package com.satou.materialcatalog.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.satou.materialcatalog.base.Application;
import com.satou.materialcatalog.widget.LoadingDialog;

/**
 * Created by Mitsuki on 2018/3/14.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
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

    private static class DatabaseHelperHolder{
        private static final DatabaseHelper INSTANCE = new DatabaseHelper(Application.getInstance());
    }

    public synchronized static final DatabaseHelper getInstance(){
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
                "name TEXT NOT NULL,bid INTEGER NOT NULL," +
                "FOREIGN KEY(name) REFERENCES " + TYPETABLE+"(name)," +
                "FOREIGN KEY(bid) REFERENCES " + BASETABLE + "(id))";
        //创建场景数据表
        String createSceneDataTable = "create table " +
                SCENEDATA + "(id INTEGER PRIMARY KEY autoincrement NOT NULL," +
                "name TEXT NOT NULL,bid INTEGER NOT NULL," +
                "FOREIGN KEY(name) REFERENCES " + SCENETABLE+"(name)," +
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
}
