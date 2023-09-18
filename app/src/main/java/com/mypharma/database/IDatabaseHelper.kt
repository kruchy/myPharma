package com.mypharma.database

import com.j256.ormlite.dao.Dao
import com.mypharma.model.Drug
import com.mypharma.model.Reminder

interface IDatabaseHelper {
    fun getDrugDao(): Dao<Drug, Long>?
    fun getReminderDao(): Dao<Reminder, Long>?
}
