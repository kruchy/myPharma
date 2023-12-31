package com.mypharma.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.mypharma.model.Drug
import com.mypharma.model.Reminder
import java.sql.SQLException


class DatabaseHelper(
    private val context: Context,
    private val csvFileName: String = "Registry.csv",

    ) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), IDatabaseHelper {

    private val mDrugDao: Dao<Drug, Long> by lazy { getDao(Drug::class.java) }
    private val mReminderDao: Dao<Reminder, Long> by lazy { getDao(Reminder::class.java) }

    companion object {
        internal const val DATABASE_NAME = "medications.db"
        private const val DATABASE_VERSION = 1
    }

    private fun checkDatabaseExists(): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        if (checkDatabaseExists()) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, Drug::class.java)
                TableUtils.createTableIfNotExists(connectionSource, Reminder::class.java)
                loadCSVtoDatabase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadCSVtoDatabase() {
        val inputStream = context.assets.open(csvFileName)

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
            .forEach { mDrugDao.create(it) }

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        connectionSource: ConnectionSource,
        oldVersion: Int,
        newVersion: Int
    ) {
        try {
            TableUtils.dropTable<Drug, Long>(connectionSource, Drug::class.java, false)
            TableUtils.dropTable<Reminder, Long>(connectionSource, Reminder::class.java, false)
            onCreate(db, connectionSource)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getDrugDao(): Dao<Drug, Long> = mDrugDao

    override fun getReminderDao(): Dao<Reminder, Long> = mReminderDao

}
