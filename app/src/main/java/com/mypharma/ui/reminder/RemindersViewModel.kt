package com.mypharma.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Drug
import com.mypharma.model.Reminder

class RemindersViewModel(val reminderDao: Dao<Reminder, Long>) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Drugs"
    }
    val text: LiveData<String> = _text

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> get() = _reminders

    init {
        _reminders.value = reminderDao.queryForAll()
    }

    fun getReminderById(id: Long): Reminder? {
        return reminderDao.queryForId(id)
    }

    fun getAllReminders(): MutableList<Reminder> {
        return reminderDao.queryForAll();
    }

    }


