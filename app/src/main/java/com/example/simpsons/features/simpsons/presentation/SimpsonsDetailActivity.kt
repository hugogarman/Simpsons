
package com.example.simpsons.features.simpsons.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.simpsons.R

class SimpsonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simpsons_detail)

        val simpsonImage = findViewById<ImageView>(R.id.ivSimpsonPhoto)
        val simpsonName = findViewById<TextView>(R.id.simpsonName)
        val simpsonPhrase = findViewById<TextView>(R.id.simpsonPhrase)

        val name = intent.getStringExtra("SIMPSON_NAME") ?: "Desconocido"
        val occupation = intent.getStringExtra("SIMPSON_OCCUPATION") ?: "Sin ocupación"

        simpsonName.text = name
        simpsonPhrase.text = occupation
        simpsonImage.setImageResource(android.R.drawable.ic_menu_gallery)
    }
}