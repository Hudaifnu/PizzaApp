package com.example.pizzaapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context): SQLiteOpenHelper(
    context,DATABASE_NAME, null,DATABASE_VERSION
) {
    companion object{
            private val DATABASE_NAME = "pizza"
            private val DATABASE_VERSION = 1
            //table name
            private val TABLE_ACCOUNT = "account"
            //column account table
            private val COLUMN_EMAIL = "email"
            private val COLUMN_NAME = "name"
            private val COLUMN_LEVEL = "level"
            private val COLUMN_PASSWORD = "password"
    }
    //creat table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_EMAIL + "TEXT PRIMARY KEY, "+ COLUMN_NAME +" TEXT, "
            + COLUMN_LEVEL + "TEXT, "+ COLUMN_PASSWORD +"TEXT, ")

    //drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        onCreate(p0)
    }
}
