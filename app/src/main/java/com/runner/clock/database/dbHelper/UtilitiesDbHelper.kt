package com.runner.clock.database.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.runner.clock.database.objectDB.UtilitiesInfoContract
import com.runner.clock.objects.Utility


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${UtilitiesInfoContract.UtilitiesTableInfoEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${UtilitiesInfoContract.UtilitiesTableInfoEntry.COLUMN_NAME_PROVIDER} TEXT," +
            "${UtilitiesInfoContract.UtilitiesTableInfoEntry.COLUMN_NAME_CITY} TEXT," +
            "${UtilitiesInfoContract.UtilitiesTableInfoEntry.COLUMN_NAME_UTILITY} TEXT)"

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${UtilitiesInfoContract.UtilitiesTableInfoEntry.TABLE_NAME}"

class UtilitiesDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "UtilitiesInfo.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertData(utility: Utility) {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        val columns = UtilitiesInfoContract.UtilitiesTableInfoEntry

        contentValue.apply {
            put(columns.COLUMN_NAME_CITY, utility.cityOfProvision)
            put(columns.COLUMN_NAME_UTILITY, utility.utilityName)
            put(columns.COLUMN_NAME_PROVIDER, utility.provider)
        }

        val result = db.insert(columns.TABLE_NAME, null, contentValue)
        if (result == (0).toLong()) {
            println("Failed")
        } else {
            println("Success")
        }
    }
}