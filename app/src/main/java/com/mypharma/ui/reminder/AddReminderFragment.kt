package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kruchy.mypharma.R
import com.kruchy.mypharma.databinding.FragmentAddReminderBinding

class AddReminderFragment : Fragment() {

    private var _binding: FragmentAddReminderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}