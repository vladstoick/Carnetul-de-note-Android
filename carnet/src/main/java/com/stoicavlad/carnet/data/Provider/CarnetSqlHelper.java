package com.stoicavlad.carnet.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stoicavlad.carnet.data.provider.CarnetContract.AbsentaEntry;
import com.stoicavlad.carnet.data.provider.CarnetContract.MaterieEntry;
/**
 * Created by Vlad on 30-Jul-14.
 */
public class CarnetSqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "carnet.db";

    public CarnetSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_ABSENTE_TABLE = "CREATE TABLE " + AbsentaEntry.TABLE_NAME + " (" +
                AbsentaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AbsentaEntry.COLUMN_DATE + " LONG NOT NULL )";

        final String SQL_CREATE_MATERIE_TABLE = "CREATE TABLE " + MaterieEntry.TABLE_NAME + " ( " +
                MaterieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MaterieEntry.COLUMN_NAME + " TEXT NOT NULL ," +
                MaterieEntry.COLUMN_TEZA + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_ABSENTE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MATERIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AbsentaEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MaterieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
