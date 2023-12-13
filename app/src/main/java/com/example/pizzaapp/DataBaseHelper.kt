package com.example.pizzaapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class DataBaseHelper(context: Context): SQLiteOpenHelper(
    context,DATABASE_NAME, null,DATABASE_VERSION
) {
    companion object {

        private val DATABASE_NAME = "pizza"
        private val DATABASE_VERSION = 1

        //table name
        private val TABLE_ACCOUNT = "account"

        //column account table
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_LEVEL = "level"
        private val COLUMN_PASSWORD = "password"

        //table menu
        private val TABLE_MENU = "menu"
        //column menu table
        private val COLUMN_ID_MENU = "idMenu"
        private val COLUMN_NAMA_MENU = "menuName"
        private val COLUMN_PRICE_MENU = "price"
        private val COLUMN_IMAGE = "photo"

        //table transaksi
        private val TABLE_TRANS = "transaksi"
        //column tabel transaksi
        private val COLUMN_ID_TRANS = "idTransaksi"
        private val COLUMN_TGL = "tanggal"
        private val COLUMN_USER = "user"

        //table detail transaksi
        private val TABLE_DET_TRANSACTION = "detailTrans"
        //column table detail trans
        private val COLUMN_ID_DET_TRX = "idDetailTrx"
        private val COLUMN_ID_TRX = "idTransaksi"
        private val COLUMN_ID_PESAN = "idMenu"
        private val COLUMN_HARGA_PESAN = "harga"
        private val COLUMN_JUMLAH = "jumlah"

        //create table menu sql query
        private val CREATE_MENU_TABLE = ("CREATE TABLE " + TABLE_MENU + "("
                + COLUMN_ID_MENU + "INT PRIMARY KEY,"+ COLUMN_NAMA_MENU +" TEXT, "
                + COLUMN_PRICE_MENU + " INT,"+ COLUMN_IMAGE +"BLOB")

        //drop table menu sql query
        private val DROP_MENU_TABLE = "DROP TABLE IF EXISTS $TABLE_MENU"
        //create table transaksi sql query
        private val CREATE_TRANSACTION_TABLE = ("CREATE TABLE " + TABLE_TRANS + "("
                + COLUMN_ID_TRANS + " INT PRIMARY KEY, "+ COLUMN_TGL +" TEXT, "
                + COLUMN_USER + " TEXT)")

        //drop table transaksi sql query
        private val DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS $TABLE_TRANS"

        //create table detail transaksi sql query
        private val CREATE_DET_TRANS_TABLE = ("CREATE TABLE " + TABLE_DET_TRANSACTION + "("
                + COLUMN_ID_DET_TRX + " INT PRIMARY KEY, "+ COLUMN_ID_TRX +" INT, "
                + COLUMN_ID_PESAN + " INT, "+ COLUMN_HARGA_PESAN + " INT, "
                + COLUMN_JUMLAH + " INT)")

        //drop table detail transaksi sql query
        private val DROP_DET_TRANS_TABLE = "DROP TABLE IF EXISTS $TABLE_DET_TRANSACTION"

    }

    //creat table account sql query
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_EMAIL + "TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT, "
            + COLUMN_LEVEL + "TEXT, " + COLUMN_PASSWORD + "TEXT, ")

    //drop table account sql query
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
        p0?.execSQL(CREATE_MENU_TABLE)
        p0?.execSQL(CREATE_TRANSACTION_TABLE)
        p0?.execSQL(CREATE_DET_TRANS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        onCreate(p0)
        p0?.execSQL(DROP_TRANSACTION_TABLE)
        p0?.execSQL(DROP_DET_TRANS_TABLE)
        p0?.execSQL(DROP_MENU_TABLE)


        }

    fun checkLogin(email: String, password: String): Boolean {
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        //selection criteria
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        //selection arguments
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(
            TABLE_ACCOUNT,// table to query
            colums, //columns to return
            selection,//columns for WHERE clause
            selectionArgs,//the values for the WHERE clause
            null, //group the rows
            null, //filter by rows groups
            null
        ) //the sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        //check data availabe or not
        if (cursorCount > 0)
            return true
        else
            return false
    }
    //add User
    fun addAccount(email:String, name:String, level:String, password:String){
    val db = this.readableDatabase

    val values = ContentValues()
    values.put(COLUMN_EMAIL, email)
    values.put(COLUMN_NAME, name)
    values.put(COLUMN_LEVEL, level)
    values.put(COLUMN_PASSWORD, password)

    db.insert(TABLE_ACCOUNT, null,values)
    db.close()
    }
    @SuppressLint("Range")
    fun checkData(email: String):String{
        val colums = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        var name:String =""

        val cursor = db.query(TABLE_ACCOUNT, //table to query
            colums, //columns to return
            selection,//columns for WHERE clause
            selectionArgs,//the values for the WHERE clause
            null, //group the rows
            null, //filter by rows groups
            null)//the sort order

        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }
    //add new menu
    fun addMenu(menu:MenuModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_MENU, menu.id)
        values.put(COLUMN_NAMA_MENU, menu.name)
        values.put(COLUMN_PRICE_MENU, menu.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.image.compress(Bitmap.CompressFormat.JPEG, 100,byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)

        val result = db.insert(TABLE_MENU,null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context,"ADD menu Failed", Toast.LENGTH_SHORT).show()
        }

    }
    //add new transaksi
    @SuppressLint("Range")
    fun addTransaction(){
        val dbInsert = this.writableDatabase
        val dbSelect = this.readableDatabase
        //declare var
        var lastIdTrans = 0
        var lastIdDetail = 0
        var newIdTrans = 0
        var newIdDetail = 0
        val values = ContentValues()
        //get last idTransaksi
        val cursorTrans: Cursor = dbSelect.rawQuery(
            "SELECT  * FROM $TABLE_TRANS", null)

        val cursorDetail: Cursor = dbSelect.rawQuery(
            "SELECT *  FROM $TABLE_DET_TRANSACTION", null)

        if (cursorTrans.moveToLast()) {
            lastIdTrans = cursorTrans.getInt(0) //to get id, 0 is the column index
        }

        if (cursorDetail.moveToLast()) {
            lastIdDetail = cursorDetail.getInt(0) //to get id, 0 is the column index
        }


        //set data
        newIdTrans = lastIdTrans + 1
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val tanggal = sdf.format(Date())
        val username = FragmentProfile.email



        //insert data transaksi
        values.put(COLUMN_ID_TRANS, newIdTrans)
        values.put(COLUMN_TGL, tanggal)
        values.put(COLUMN_USER, username)
        val result = dbInsert.insert(TABLE_TRANS,null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Add transaction Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Add transaction Success",Toast.LENGTH_SHORT).show()
        }

        newIdDetail = lastIdDetail + 1
        var i = 0
        val values2 = ContentValues()
        while(i < TransaksiAdapter.listId.count()){
            values2.put(COLUMN_ID_DET_TRX, newIdDetail)
            values2.put(COLUMN_ID_TRX, newIdTrans)
            values2.put(COLUMN_ID_PESAN, TransaksiAdapter.listId[i])
            values2.put(COLUMN_HARGA_PESAN, TransaksiAdapter.listHarga[i])
            values2.put(COLUMN_JUMLAH, TransaksiAdapter.listJumlah[i])
            val result2 = dbInsert.insert(TABLE_DET_TRANSACTION,null, values2)
            //show message
            if (result2==(0).toLong()){
                Toast.makeText(context, "Add detail Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Add detail Success",Toast.LENGTH_SHORT).show()
            }
            newIdDetail += 1
            i+=1
        }
        dbSelect.close()
        dbInsert.close()
    }
}




