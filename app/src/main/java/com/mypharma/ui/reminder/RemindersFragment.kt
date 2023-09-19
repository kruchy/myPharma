package com.mypharma.ui.reminder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kruchy.mypharma.R
import com.kruchy.mypharma.databinding.FragmentRemindersBinding
import com.mypharma.MainActivity
import com.mypharma.model.Drug
import com.mypharma.model.Reminder
import com.mypharma.ui.DividerItemDecoration
import java.time.Instant
import java.util.Date

class RemindersFragment : Fragment() {

    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RemindersViewModel by viewModels {
        val mainActivity = activity as MainActivity
        val databaseHelper = mainActivity.getDatabaseHelper()
        val reminderDao = databaseHelper.getReminderDao()!!
        RemindersViewModelFactory(reminderDao)
    }

    private val remindersAdapter = RemindersAdapter(emptyList())

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

        val recyclerView = binding.remindersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = remindersAdapter
//        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
//        if (divider != null) {
//            val itemDecoration = DividerItemDecoration(divider)
//            recyclerView.addItemDecoration(itemDecoration)
//        }
        viewModel.reminders.observe(viewLifecycleOwner) { reminders ->
            Log.d("RemindersFragment", "Received reminders: $reminders")
            val remindersAdapter = RemindersAdapter(reminders)
            recyclerView.adapter = remindersAdapter
            recyclerView.requestLayout()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

