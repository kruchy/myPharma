package com.mypharma.registry

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.mypharma.medicine.Drug
import java.io.InputStream
import java.sql.SQLException


class DatabaseHelper(
    context: Context,
    val inputStream: InputStream
) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var drugDao: Dao<Drug, Long>? = null


    companion object {
        internal const val DATABASE_NAME = "medications.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTable(connectionSource, Drug::class.java)
            loadCSVtoDatabase()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadCSVtoDatabase() {
        val csvMapper = CsvMapper().apply {
            enable(CsvParser.Feature.TRIM_SPACES)
            enable(CsvParser.Feature.SKIP_EMPTY_LINES)
        }
        val schema = CsvSchema.builder().addNumberColumn("Identyfikator Produktu Leczniczego")
            .addColumn("Nazwa Produktu Leczniczego")
            .addColumn("Nazwa powszechnie stosowana")
            .addColumn("Moc")
            .addColumn("Postać farmaceutyczna")
            .addColumn("Kod ATC")
            .addColumn("Podmiot odpowiedzialny")
            .addColumn("Opakowanie")
            .addColumn("Substancja czynna")
            .addColumn("Ulotka")
            .addColumn("Charakterystyka")
            .build()

        csvMapper.readerFor(Drug::class.java)
            .with(schema.withSkipFirstDataRow(true))
            .readValues<Drug>(inputStream)
            .readAll()
            .map { getDao()!!.create(it) }


    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        connectionSource: ConnectionSource,
        oldVersion: Int,
        newVersion: Int
    ) {
        try {
            TableUtils.dropTable<Drug, Int>(connectionSource, Drug::class.java, false)
            onCreate(db, connectionSource)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun getDao(): Dao<Drug, Long>? {
        if (drugDao == null) {
            try {
                drugDao = getDao(Drug::class.java)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        return drugDao
    }

}
