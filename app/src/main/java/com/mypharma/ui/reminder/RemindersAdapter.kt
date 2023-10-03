package com.mypharma.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kruchy.mypharma.R
import com.mypharma.model.Reminder
import java.text.SimpleDateFormat
import java.util.Locale

class RemindersAdapter(private var reminders: List<Reminder>) :
    RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_view, parent, false)
        val textView = view.findViewById<TextView>(R.id.textContainer)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = reminders[position]
        val formattedDate = reminder.date?.let {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
                it
            )
        }
        val drugName = reminder.drug?.popularName ?: "Brak informacji o leku"

        holder.textView.text = "Nazwa: ${reminder.name ?: "Brak nazwy"}, Data: $formattedDate, Lek: $drugName"

        holder.itemView.setOnClickListener {
            val reminderId = reminders[position].id

            val bundle = Bundle()
            bundle.putLong("reminderId", reminderId!!)

            it.findNavController().navigate(R.id.editReminderFragment, bundle)

        }
    }

    override fun getItemCount() = reminders.size

}
