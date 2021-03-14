package com.example.agendabarberpro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME(ID INTEGER PRIMARY KEY,  TYPE TEXT, NAME TEXT, HOUR TEXT, DAY TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(type: String, name: String, hour: String, day: String) : Long {
        val db = writableDatabase

        db.beginTransaction()

        var calcId: Long = 0

        try {

            val values = ContentValues()

            values.put(COLUMN_2, type)
            values.put(COLUMN_3, name)
            values.put(COLUMN_4, hour)
            values.put(COLUMN_5, day)


            calcId = db.insertOrThrow(TABLE_NAME, null, values)
            db.setTransactionSuccessful()


        }catch (aviso: Exception){

            Log.e("SQLite", aviso.message, aviso)

        }finally {
            db.endTransaction()
        }

        return calcId
    }

    fun updateData(type: String, name:String, hour: String, day: String, id: Int) : Long{
        val db = writableDatabase

        db.beginTransaction()

        var calcId: Long = 0

        try {
            val values = ContentValues()

            values.put(COLUMN_1, id)
            values.put(COLUMN_2, type)
            values.put(COLUMN_3, name)
            values.put(COLUMN_4, hour)
            values.put(COLUMN_5, day)



            calcId = db.update(TABLE_NAME, values, "ID = ? AND TYPE = ?", arrayOf(id.toString(), type)).toLong()

            db.setTransactionSuccessful()

        }catch (aviso: Exception){

            Log.e("SQLite", aviso.message, aviso)

        }finally {
            db.endTransaction()
        }
        return calcId

    }

    fun deleteData(id: Int, type: String) : Long{
        val db = writableDatabase
        db.beginTransaction()
        var calcId: Long = 0

        try {
            calcId = db.delete(TABLE_NAME, "ID = ? AND TYPE = ?", arrayOf(id.toString(), type)).toLong()

            db.setTransactionSuccessful()

        }catch (aviso: Exception){

            Log.e("SQLite", aviso.message, aviso)

        }finally {
            db.endTransaction()
        }

        return calcId
    }

    fun allRecords(type: String) : List<Cadastro>{
        val cadastros: MutableList<Cadastro> = ArrayList()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE TYPE = ?", arrayOf(type))

        try {
            if (cursor.moveToFirst()) {
                do {
                    val cadastro = Cadastro()
                    cadastro.id = cursor.getInt(cursor.getColumnIndex(COLUMN_1))
                    cadastro.type = cursor.getString(cursor.getColumnIndex(COLUMN_2))
                    cadastro.name = cursor.getString(cursor.getColumnIndex(COLUMN_3))
                    cadastro.hour = cursor.getString(cursor.getColumnIndex(COLUMN_4))
                    cadastro.day = cursor.getString(cursor.getColumnIndex(COLUMN_5))

                    cadastros.add(cadastro)
                }while (cursor.moveToNext())

                db.setTransactionSuccessful()
            }
        }catch (aviso: Exception){

            Log.e("SQLite", aviso.message, aviso)

        }finally {

            if (cursor != null && !cursor.isClosed) cursor.close()

        }
        return cadastros
    }


    /*val allData : Cursor get() {
        val db = writableDatabase
        val res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        return res
    }*/

    companion object{
        const val DATABASE_NAME = "agend.db"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "table_agend"
        const val COLUMN_1 = "ID"
        const val COLUMN_2 = "TYPE"
        const val COLUMN_3 = "NAME"
        const val COLUMN_4 = "HOUR"
        const val COLUMN_5 = "DAY"
        const val TYPE_SCHEDULE = "client"
        private var INSTANCE : DataBaseHelper? = null

        @JvmStatic
        fun getInstancia(context: Context?): DataBaseHelper? {
            if (INSTANCE == null) INSTANCE = context?.let { DataBaseHelper(it) }
            return INSTANCE
        }

        class Cadastro (var id: Int = 0 , var type: String? = null, var name: String? = null, var hour: String? = null, var day: String? = null)
    }

}