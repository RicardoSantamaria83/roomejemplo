package com.ricardo.roomejemplo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PalabraListAdapter : ListAdapter<Palabra, PalabraListAdapter.PalabraViewHolder>(PalabrasComparator())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalabraViewHolder {
        return PalabraViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PalabraViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.palabra)
    }

    class PalabraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val palabraItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            palabraItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PalabraViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return PalabraViewHolder(view)
            }
        }
    }

    class PalabrasComparator : DiffUtil.ItemCallback<Palabra>() {
        override fun areItemsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem.palabra == newItem.palabra
        }
    }
}