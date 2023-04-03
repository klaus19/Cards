package com.example.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.adapter.CardListAdapter
import com.example.cards.databinding.SecondBinding
import com.example.cards.db.CardPair
import com.example.cards.viewmodel.CardViewmodel
import com.example.cards.viewmodel.WordViewModelFactory

class SecondActivity:AppCompatActivity() {

    lateinit var binding: SecondBinding
    val cardViewmodel:  CardViewmodel by viewModels{
        WordViewModelFactory((application as CardsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = SecondBinding.inflate(layoutInflater)
         setContentView(binding.root)

        val adapter = CardListAdapter()
        binding.recyclerview1.adapter = adapter
        binding.recyclerview1.layoutManager = LinearLayoutManager(this)

        cardViewmodel.allWords.observe(this) { cardPairs ->

            for (cardpair in cardPairs) {
                 adapter.submitList(cardPairs)
            }

        }

        val inflator = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.recyclerview,null)

        val imageDelete = view.findViewById<ImageView>(R.id.imageDelete)

        imageDelete.setOnClickListener {

            // Get the position of the clicked item
            val position = binding.recyclerview1.getChildAdapterPosition(view)

            // Get the CardPair object from the adapter using the position
            val cardPair = adapter.currentList[position]

            // Call the delete() method on your CardViewmodel to delete the CardPair from the database
            cardViewmodel.deleteCards(cardPair)

            // Notify the adapter that the data has changed
            adapter.notifyItemRemoved(position)

        }
    }
}