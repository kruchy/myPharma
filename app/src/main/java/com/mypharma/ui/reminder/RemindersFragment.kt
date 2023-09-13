package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kruchy.mypharma.databinding.FragmentRemindersBinding
import com.mypharma.database.DatabaseHelper

class RemindersFragment : Fragment() {

    private var _binding: FragmentRemindersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: RemindersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val databaseHelper = DatabaseHelper(context, context.assets.open("Registry.csv"))
        val reminderDao = databaseHelper.getReminderDao()!!

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RemindersViewModel(reminderDao) as T
            }

        })[RemindersViewModel::class.java]

        val remindersList = viewModel.getAllReminders() ?: mutableListOf()
        val adapter = RemindersAdapter(remindersList)
        val recyclerView: RecyclerView = binding.remindersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.reminders.observe(viewLifecycleOwner) { reminders ->
            val remindersAdapter = RemindersAdapter(reminders)
            recyclerView.adapter = remindersAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}