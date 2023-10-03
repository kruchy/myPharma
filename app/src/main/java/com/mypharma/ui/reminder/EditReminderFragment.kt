package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kruchy.mypharma.R
import com.mypharma.MainActivity
import com.mypharma.model.Reminder
import com.mypharma.ui.drug.PopularDrugsViewModel
import com.mypharma.ui.reminder.factory.RemindersViewModelFactory

class EditReminderFragment : Fragment() {

    private val viewModel: RemindersViewModel by viewModels {
        val mainActivity = activity as MainActivity
        val databaseHelper = mainActivity.getDatabaseHelper()
        val reminderDao = databaseHelper.getReminderDao()!!
        RemindersViewModelFactory(reminderDao)
    }

    private val popularDrugsViewModel: PopularDrugsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val reminderId = arguments?.getLong("reminderId")!!
        val reminder = loadReminderFromDatabase(reminderId)
        val view = inflater.inflate(R.layout.fragment_edit_reminder, container, false)
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.editAutoCompleteTextView)
        autoCompleteTextView.setText(reminder?.name ?: "Name not found")
        val saveButton: Button? = view?.findViewById(R.id.saveButton)
        saveButton?.setOnClickListener {
            updateReminderInDatabase(reminder) // Implement this method

            findNavController().popBackStack()
        }

        val deleteButton: Button? = view?.findViewById(R.id.deleteButton)
        deleteButton?.setOnClickListener {
             deleteReminderFromDatabase(reminderId)

            findNavController().popBackStack()
        }

        return view
    }

    private fun deleteReminderFromDatabase(reminderId: Long) {
        viewModel.deleteReminder(reminderId)
    }

    private fun updateReminderInDatabase(reminder: Reminder?) {
        if(reminder != null) {
            viewModel.updateReminder(reminder)
        }
    }

    private fun loadReminderFromDatabase(reminderId: Long): Reminder? {
        return viewModel.getReminderById(reminderId)
    }
}
