package ru.mirea.lukutin.mireaproject.ui.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.lukutin.mireaproject.R
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.Story

class HistoryAdapter(context: Context?, private val items: List<Story>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val inflater: LayoutInflater

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bigTextView: TextView = itemView.findViewById(R.id.bigTextView)
        val smallTextView : TextView = itemView.findViewById(R.id.smallTextVIew)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = items[position]
        holder.bigTextView.text = item.storyTitle
        holder.smallTextView.text = item.storyText
        holder.dateTextView.text = item.storyDate
    }

    override fun getItemCount(): Int {
        return items.size
    }

    init{
        inflater = LayoutInflater.from(context)
    }
}