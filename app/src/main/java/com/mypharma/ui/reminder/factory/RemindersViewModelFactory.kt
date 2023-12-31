package com.mypharma.ui.reminder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Reminder
import com.mypharma.ui.reminder.RemindersViewModel

class RemindersViewModelFactory(private val reminderDao: Dao<Reminder, Long>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersViewModel::class.java)) {
            return RemindersViewModel(reminderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
