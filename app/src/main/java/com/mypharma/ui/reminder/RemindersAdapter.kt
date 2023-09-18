package com.mypharma.ui.reminder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kruchy.mypharma.R
import com.mypharma.model.Reminder

class RemindersAdapter(private val reminders: List<Reminder>) :
    RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.line_view , parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RemindersAdapter", "Binding reminder at position: $position")
        holder.textView.text = reminders[position].name
    }

    override fun getItemCount() = reminders.size
}
