package com.example.historicalpetersburg.tools

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

class DbHelper(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1
    }

    init {
        if (!databaseExists()) {
        }
        copyDatabase()
    }

    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    private fun databaseExists(): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    private fun copyDatabase() {
        val dbPath = context.getDatabasePath(DATABASE_NAME)

        if (!dbPath.exists()) {
            dbPath.parentFile?.mkdirs()
        }

        context.assets.open(DATABASE_NAME).use { input ->
            FileOutputStream(dbPath).use { output ->
                input.copyTo(output)
            }
        }
    }
}
