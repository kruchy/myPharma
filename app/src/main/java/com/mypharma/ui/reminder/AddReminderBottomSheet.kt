package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kruchy.mypharma.R
import com.mypharma.MainActivity
import com.mypharma.model.DrugView
import com.mypharma.model.Reminder
import com.mypharma.ui.drug.PopularDrugsViewModel
import com.mypharma.ui.drug.factory.PopularDrugsViewModelFactory
import com.mypharma.ui.reminder.factory.RemindersViewModelFactory
import java.util.Calendar

class AddReminderBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    private var selectedDrug: DrugView? = null
    private lateinit var popularDrugsViewModel: PopularDrugsViewModel
    private lateinit var remindersViewModel: RemindersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()

        val autoCompleteTextView: AutoCompleteTextView =
            view.findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.threshold = 2



        popularDrugsViewModel.popularDrugs.observe(viewLifecycleOwner) { drugs ->
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_multiline_item,
                R.id.includedLineView,
                popularDrugsViewModel.popularDrugs.value ?: mutableListOf<DrugView>()

            )
            autoCompleteTextView.setAdapter(adapter)
        }

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            selectedDrug = parent.adapter.getItem(position) as? DrugView
        }

        val datePicker: DatePicker = view.findViewById(R.id.datePicker)
        val addButton: Button = view.findViewById(R.id.button)
        addButton.setOnClickListener {
            val date = Calendar.getInstance().apply {
                set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }.time

            val reminder = Reminder().apply {
                this.date = date
                this.drug = selectedDrug?.id?.let { popularDrugsViewModel.getDrugById(it) }
            }
            remindersViewModel.addReminder(reminder)

            dismiss()
        }
    }

    private fun initViewModels() {
        val context = requireActivity() as? MainActivity ?: return
        val databaseHelper = context.getDatabaseHelper()
        val drugViewFactory = PopularDrugsViewModelFactory(databaseHelper.getDrugDao()!!)
        val reminderViewFactory = RemindersViewModelFactory(databaseHelper.getReminderDao()!!)

        popularDrugsViewModel =
            ViewModelProvider(requireActivity(), drugViewFactory)[PopularDrugsViewModel::class.java]
        remindersViewModel = ViewModelProvider(
            requireActivity(),
            reminderViewFactory
        )[RemindersViewModel::class.java]
    }
}



