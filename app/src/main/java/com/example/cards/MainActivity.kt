package com.example.cards

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.cards.databinding.ActivityMainBinding
import com.example.cards.db.CardPair
import com.example.cards.viewmodel.CardViewmodel
import com.example.cards.viewmodel.CounterViewModel
import com.example.cards.viewmodel.WordViewModelFactory


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val counterViewModel: CounterViewModel by viewModels()

    private val hashMap = HashMap<String,String>()

    private var currentPairIndex =0
    private lateinit var currentPair:Map.Entry<String,String>

    var isFront=true
    private val totalPairs = 17 // change the value to the actual number of entries in your hashMap

    private val cardViewModel: CardViewmodel by viewModels {
        WordViewModelFactory((application as CardsApplication).repository)
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hashmap of strings that will shown on cardview front and back side
        hashMap["What"] = "Kas"
        hashMap["When"] = "Kai"
        hashMap["Where"] = "Kur"
        hashMap["Who"] = "Kas"
        hashMap["Whom"] = "Kam"
        hashMap["Why"] = "Kodėl"
        hashMap["How"] = "Kaip"
        hashMap["Which"] = "Kuris/kuri"
        hashMap["Whose"] = "Kieno"
        hashMap["I"] = "aš"
        hashMap["you (singular)"] = "tu/jūs (informal/formal)"
        hashMap["he"] = "jis"
        hashMap["she"] = "ji"
        hashMap["it "] = "tai"
        hashMap["we"] = "mes"
        hashMap["you (plural)"] = "jūs"
        hashMap["they"] = "jie"

        counterViewModel.counter.observe(this){count->
            binding.textCounter.text = count.toString()
        }



        val front_animation = AnimatorInflater.loadAnimator(this,R.anim.front_animator) as AnimatorSet
        val back_animation = AnimatorInflater.loadAnimator(this,R.anim.back_animator)as AnimatorSet

        currentPair = hashMap.entries.elementAt(currentPairIndex)
        binding.textCardFront.text = currentPair.key
        binding.textCardBack.text = hashMap[currentPair.key]

        binding.imageFlashCard.setOnClickListener {
            counterViewModel.incrementCounter()

            val front = binding.textCardFront.text.toString()
            val back = binding.textCardBack.text.toString()

            val pair = CardPair(front, back)
            cardViewModel.insertCards(pair)
            Toast.makeText(this@MainActivity,"saved data",Toast.LENGTH_SHORT).show()
        }

        //onclick listener for the Flip button
        with(binding) {
            btnFlip.setOnClickListener {
                val progress = ((currentPairIndex + 1) * 100) / totalPairs
                binding.progressHorizontal.progress = progress

                // initialize currentPairIndex to 0 if it hasn't been initialized yet
                if (currentPairIndex < 0) {
                    currentPairIndex = 0
                }
                if (isFront) {
                    front_animation.setTarget(textCardFront)
                    back_animation.setTarget(textCardBack)
                    front_animation.start()
                    back_animation.start()
                    isFront = false
                    textCardBack.visibility = View.VISIBLE
                    textCardFront.visibility = View.GONE
                    imageFlashCard.visibility = View.GONE
                    cardViewQuestions.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.pink))

                } else {
                    currentPairIndex = (currentPairIndex + 1) % hashMap.size
                    textCardFront.visibility = View.VISIBLE
                    textCardBack.visibility = View.GONE
                    imageFlashCard.visibility = View.VISIBLE
                    cardViewQuestions.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.blue_card))
                    front_animation.setTarget(textCardBack)
                    back_animation.setTarget(textCardFront)
                    back_animation.start()
                    front_animation.start()
                    isFront = true
                }
                // retrieve the current pair from the hashMap
                currentPair = hashMap.entries.elementAt(currentPairIndex)
                binding.textCardFront.text = currentPair.key
                binding.textCardBack.text = hashMap[currentPair.key]
            }
        }

        binding.cardLearning.setOnClickListener {
            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }

    }
}