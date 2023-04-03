package com.example.cards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.R
import com.example.cards.db.CardPair

class CardListAdapter : ListAdapter<CardPair,CardListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        val current1 = getItem(position)
        holder.bind(current.front,current1.back)

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        private val deftext: TextView = itemView.findViewById(R.id.text1)

        fun bind(text: String?,text1: String?) {
            wordItemView.text = text
            deftext.text=text1
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<CardPair>() {
        override fun areItemsTheSame(oldItem:CardPair, newItem:CardPair): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem:CardPair, newItem:CardPair): Boolean {
            return oldItem.front == newItem.front && oldItem.back==newItem.back
        }
    }
}