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
        const val DATABASE_NAME = "Contacts"
        const val TABLE_NAME = "Contacts"

        // названия полей
        const val KEY_ID = "id"
        const val KEY_FIRSTNAME = "First Name"
        const val KEY_LASTNAME = "Last Name"
        const val KEY_BIRTHDAYDATE = "BD Date"
        const val KEY_PHONENUMBER = "Phone number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,            
            $KEY_FIRSTNAME TEXT NOT NULL,
            $KEY_LASTNAME TEXT NOT NULL,
            $KEY_BIRTHDAYDATE TEXT NOT NULL,
            $KEY_PHONENUMBER TEXT NOT NULL
            ) """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun GetAll(): List<Contact> {
        val result = mutableListOf<Contact>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val IdIndex: Int = cursor.getColumnIndex(KEY_ID)
            val FirstNameIndex: Int = cursor.getColumnIndex(KEY_FIRSTNAME)
            val LastNameIndex: Int = cursor.getColumnIndex(KEY_LASTNAME)
            val BirthdayDateIndex: Int = cursor.getColumnIndex(KEY_BIRTHDAYDATE)
            val PhoneNumberIndex: Int = cursor.getColumnIndex(KEY_PHONENUMBER)
            do {
                val number = Contact(
                    cursor.getLong(IdIndex),
                    cursor.getString(FirstNameIndex),
                    cursor.getString(LastNameIndex),
                    cursor.getString(BirthdayDateIndex),
                    cursor.getString(PhoneNumberIndex)
                )
                result.add(number)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun add(firstName: String,lastName:String,birthdayDate:String,phoneNumber:String): Long {
        val database = this.writableDatabase
        val rowData = ContentValues()
        rowData.put(KEY_FIRSTNAME, firstName)
        rowData.put(KEY_LASTNAME,lastName)
        rowData.put(KEY_BIRTHDAYDATE,birthdayDate)
        rowData.put(KEY_PHONENUMBER,phoneNumber)
        val id = database.insert(TABLE_NAME, null, rowData)
        close()
        return id
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAll() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
}