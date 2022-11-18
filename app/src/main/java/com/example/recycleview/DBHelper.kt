package com.example.recycleview

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // статические константы имеет смысл хранить так:
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Numbers"
        const val TABLE_NAME = "Numbers"
        // названия полей
        const val KEY_ID = "id"
        const val KEY_TEXT = "Number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME(
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,            
            $KEY_TEXT TEXT NOT NULL
            ) """)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun GetAll():List<Contact>{
        val result = mutableListOf<Contact>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(TABLE_NAME, null, null, null,
            null, null, null)
        if (cursor.moveToFirst()){
            val IdIndex: Int = cursor.getColumnIndex(KEY_ID)
            val TextIndex: Int = cursor.getColumnIndex(KEY_TEXT)
            do {
                val number = Contact(
                    cursor.getLong(IdIndex),
                    cursor.getString(TextIndex)
                )
                result.add(number)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun add (text:String):Long{
        val database =this.writableDatabase
        val cv = ContentValues()
        cv.put (KEY_TEXT,text)
        val id = database.insert(TABLE_NAME,null,cv)
        close()
        return id
    }

    fun remove (id:Long){
        val database = this.writableDatabase
        database.delete(TABLE_NAME,"$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAll() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
}