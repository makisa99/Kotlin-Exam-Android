package com.metropolitan.cs330pz3599.baza

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Baza(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "MYDatabase.db"
        val TABLE_NAME = "rezultati"
        val ID = "ID"
        val BR_INDEKSA = "BR_INDEKSA"
        val REZULTAT = "REZULTAT"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,BR_INDEKSA TEXT,REZULTAT TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // db?.execSQL("INSERT INTO $TABLE_NAME(BR_INDEKSA, REZULTAT) VALUES")
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    public fun insertData(br_indeksa: String, rezultat: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(BR_INDEKSA, br_indeksa)
        contentValues.put(REZULTAT, rezultat)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllData(): ArrayList<RezultatInfo> {
        val stuList: ArrayList<RezultatInfo> = arrayListOf<RezultatInfo>()
        val cursor: Cursor = getReadableDatabase().query(
            TABLE_NAME,
            arrayOf(ID, BR_INDEKSA, REZULTAT),
            null,
            null,
            null,
            null,
            null
        )
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    do {
                        val id: Int = cursor.getInt(cursor.getColumnIndex(ID))
                        val brind: String = cursor.getString(cursor.getColumnIndex(BR_INDEKSA))
                        val rez: String = cursor.getString(cursor.getColumnIndex(REZULTAT))
                        var rezInfo = RezultatInfo(brind, rez)
                        stuList.add(rezInfo)
                    } while ((cursor.moveToNext()))
                }
            }
        } finally {
            cursor.close()
        }

        return stuList
    }
}