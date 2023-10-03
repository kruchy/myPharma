package com.mypharma.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Reminder
import kotlinx.coroutines.launch

class RemindersViewModel(private val reminderDao: Dao<Reminder, Long>) : ViewModel() {

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> = _reminders

    init {
        fetchAllReminders()
    }

    private fun fetchAllReminders() = viewModelScope.launch {
        _reminders.postValue(getAllReminders())
    }

    fun getReminderById(id: Long): Reminder? {
        return reminderDao.queryForId(id)
    }

    private  fun getAllReminders(): MutableList<Reminder> {
        return reminderDao.queryForAll()
    }

    fun addReminder(reminder: Reminder) = viewModelScope.launch {
        reminderDao.create(reminder)
        _reminders.postValue(getAllReminders())
    }

    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        reminderDao.update(reminder)
        _reminders.postValue(getAllReminders())
    }

    fun deleteReminder(reminder: Long) = viewModelScope.launch {
        reminderDao.deleteById(reminder)
        _reminders.postValue(getAllReminders())
    }
}
