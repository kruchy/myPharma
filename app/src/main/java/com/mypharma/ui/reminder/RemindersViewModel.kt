package com.mypharma.ui.reminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Drug
import com.mypharma.model.Reminder

class RemindersViewModel(private val reminderDao: Dao<Reminder, Long>) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Drugs"
    }
    val text: LiveData<String> = _text

    private val _reminders = MutableLiveData<List<Reminder>>()

    init {
        _reminders.value = getAllReminders()
    }

    val reminders: LiveData<List<Reminder>> get() = _reminders

    fun getReminderById(id: Long): Reminder? {
        return reminderDao.queryForId(id)
    }

    fun getAllReminders(): MutableList<Reminder> {
        val remindersList = reminderDao.queryForAll()
        Log.d("RemindersViewModel", "Fetched reminders: $remindersList")
        return remindersList
    }

    fun addReminder(reminder: Reminder) {
        reminderDao.create(reminder)
        _reminders.value = getAllReminders()
    }
}


