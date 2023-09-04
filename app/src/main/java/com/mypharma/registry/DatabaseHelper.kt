package com.mypharma.registry

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.BufferedReader
import java.io.InputStreamReader

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val mContext: Context = context

    companion object {
        internal const val DATABASE_NAME = "medications.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = """
            CREATE TABLE medications (
                id INTEGER PRIMARY KEY,
                product_name TEXT,
                common_name TEXT,
                strength TEXT,
                form TEXT,
                atc_code TEXT,
                responsible_entity TEXT,
                packaging TEXT,
                active_substance TEXT,
                leaflet TEXT,
                characteristics TEXT
            )
        """
        db.execSQL(createTableSQL)

        loadCSVtoDatabase(db)
    }

    private fun loadCSVtoDatabase(db: SQLiteDatabase) {
        val inputStream = mContext.assets.open("Registry.csv")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.readLine()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            val tokens = line?.split(",") ?: continue
            val insertSQL = """
                INSERT INTO medications (id, product_name, common_name, strength, form, atc_code, responsible_entity, packaging, active_substance, leaflet, characteristics)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """
            db.execSQL(
                insertSQL,
                arrayOf(
                    tokens[0],
                    tokens[1],
                    tokens[2],
                    tokens[3],
                    tokens[4],
                    tokens[5],
                    tokens[6],
                    tokens[7],
                    tokens[8],
                    tokens[9],
                    tokens[10]
                )
            )
        }
        bufferedReader.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
}
