package com.example.agendabarberpro

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME(ID INTEGER PRIMARY KEY, NAME TEXT, HOUR TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        //onCreate(db)
    }

    fun insertData(name: String, hour: String) : Long {
        val db = writableDatabase

        db.beginTransaction()

        var calcId: Long = 0

        val values = ContentValues()

        values.put(COLUMN_2, name)
        values.put(COLUMN_3, hour)

        calcId = db.insertOrThrow(TABLE_NAME, null, values)
        db.setTransactionSuccessful()
        db.endTransaction()

        return calcId
    }

    fun updateData(id: String, name:String, hour: String) : Long{
        val db = writableDatabase

        db.beginTransaction()

        var calcId: Long = 0

        val values = ContentValues()

        values.put(COLUMN_1, id)
        values.put(COLUMN_2, name)
        values.put(COLUMN_3, hour)

        calcId = db.update(TABLE_NAME, values, "ID = ?", arrayOf(id)).toLong()

        db.setTransactionSuccessful()
        db.endTransaction()

        return calcId
    }

    fun deleteData(id: String) : Int{
        val db = writableDatabase
        db.beginTransaction()
        var calcId = 0

        calcId = db.delete(TABLE_NAME, "ID = ?", arrayOf(id))

        db.setTransactionSuccessful()
        db.endTransaction()

        return calcId
    }

    val allData : Cursor get() {
        val db = writableDatabase
        val res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        return res
    }

    companion object{
        val DATABASE_NAME = "agend.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "table_agend"
        val COLUMN_1 = "ID"
        val COLUMN_2 = "NAME"
        val COLUMN_3 = "HOUR"
        var INSTANCE : DataBaseHelper? = null

        @JvmStatic
        fun getInstancia(context: Context?): DataBaseHelper? {
            if (INSTANCE == null) INSTANCE = context?.let { DataBaseHelper(it) }
            return INSTANCE
        }
    }




}