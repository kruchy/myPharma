package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kruchy.mypharma.R
import com.mypharma.MainActivity
import com.mypharma.model.DrugView
import com.mypharma.database.DatabaseHelper
import com.mypharma.model.Reminder
import java.util.Calendar

class AddReminderBottomSheet() : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }
    private lateinit var popularNames: List<DrugView>
    private var selectedDrug: DrugView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext() as MainActivity
        val databaseHelper = context.getDatabaseHelper()
        popularNames = databaseHelper.getDrugDao()!!.queryForAll().map {
            DrugView(it.id ,it.popularName, it.entityResponsible, it.substance)
        }
        val reminderDao = databaseHelper.getReminderDao()!!
        val drugDao = databaseHelper.getDrugDao()!!

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RemindersViewModel(reminderDao) as T
            }
        })[RemindersViewModel::class.java]
        val autoCompleteTextView: AutoCompleteTextView =
            view.findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.threshold = 2
        val datePicker: DatePicker = view.findViewById(R.id.datePicker)
        val addButton: Button = view.findViewById(R.id.button)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_multiline_item,
            R.id.textContainer,
            popularNames
        )
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            selectedDrug = parent.adapter.getItem(position) as DrugView
        }

        autoCompleteTextView.setAdapter(adapter)


        addButton.setOnClickListener {
            val date = Calendar.getInstance().apply {
                set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }.time

            val reminder = Reminder()
            reminder.date = date
            reminder.drug = drugDao.queryForId(selectedDrug!!.id)
            viewModel.addReminder(reminder)

            dismiss()
        }

    }
}



