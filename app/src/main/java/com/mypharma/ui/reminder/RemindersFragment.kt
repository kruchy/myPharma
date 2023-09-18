package com.mypharma.ui.reminder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kruchy.mypharma.databinding.FragmentRemindersBinding
import com.mypharma.MainActivity

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
        val mainActivity = activity as MainActivity
        val databaseHelper = mainActivity.getDatabaseHelper()
        val reminderDao = databaseHelper.getReminderDao()!!

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RemindersViewModel(reminderDao) as T
            }

        })[RemindersViewModel::class.java]

        val recyclerView: RecyclerView = binding.remindersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
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