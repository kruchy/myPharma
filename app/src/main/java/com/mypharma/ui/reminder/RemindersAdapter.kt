package com.mypharma.ui.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kruchy.mypharma.R
import com.mypharma.model.Reminder

class RemindersAdapter(private var reminders: List<Reminder>) :
    RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_view, parent, false)
        val textView = view.findViewById<TextView>(R.id.textContainer)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = "${reminders[position].date} ${reminders[position].drug?.popularName}"
    }

    override fun getItemCount() = reminders.size

}
