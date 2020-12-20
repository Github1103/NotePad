package com.example.notepad

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * 数据库创建
 */

class MyDatabaseHelper(context: Context?, name: String?, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    private val tag = "MyDatabaseHelper"

    private val createDatabase = "CREATE TABLE " +  NotePad.table_name +"(" +
            NotePad.id + " INTEGER PRIMARY KEY autoincrement," +
            NotePad.column_name_title + " TEXT," +
            NotePad.column_name_note + " TEXT," +
            NotePad.column_name_create_date + " INTEGER," +
            NotePad.column_name_modification_date + " INTEGER," +
            NotePad.column_name_background + " INTEGER" +
            ");"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(tag, "Creating Database")
        db.execSQL(createDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(tag, "Upgrading Database")
        db.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

}