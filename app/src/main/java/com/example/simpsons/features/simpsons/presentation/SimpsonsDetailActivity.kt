package com.example.simpsons.features.simpsons.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.example.simpsons.R
import com.example.simpsons.databinding.ActivitySimpsonsDetailBinding

class SimpsonsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpsonsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpsonsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromIntent()
        setupBackButton()
    }

    private fun getDataFromIntent() {
        val name = intent.getStringExtra("SIMPSON_NAME") ?: ""
        val age = intent.getIntExtra("SIMPSON_AGE", 0)
        val occupation = intent.getStringExtra("SIMPSON_OCCUPATION") ?: ""
        val portraitPath = intent.getStringExtra("SIMPSON_PORTRAIT") ?: ""
        val phrases = intent.getStringArrayListExtra("SIMPSON_PHRASES") ?: arrayListOf()

        displayData(name, age, occupation, portraitPath, phrases)
    }

    private fun displayData(
        name: String,
        age: Int,
        occupation: String,
        portraitPath: String,
        phrases: List<String>
    ) {
        binding.ivSimpsonPhoto.load(portraitPath) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_menu_report_image)
        }

        binding.tvSimpsonName.text = name
        binding.tvSimpsonAge.text = "Age: $age"
        binding.tvSimpsonOccupation.text = occupation

        displayPhrases(phrases)
    }

    private fun displayPhrases(phrases: List<String>) {
        binding.llPhrases.removeAllViews()

        phrases.forEach { phrase ->
            val phraseTextView = TextView(this).apply {
                text = "\"$phrase\""
                textSize = 16f
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                setPadding(32, 24, 32, 24)
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.ITALIC)
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            }

            val layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }

            binding.llPhrases.addView(phraseTextView, layoutParams)
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}