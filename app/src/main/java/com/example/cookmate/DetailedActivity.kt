package com.example.cookmate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookmate.databinding.ActivityDetailBinding

class DetailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")
        val ingredients = intent.getIntExtra("ingredients", 0)
        val desc = intent.getIntExtra("desc", 0)
        val image = intent.getIntExtra("image", 0)

        val nameTextView = findViewById<TextView>(R.id.detailName)
        val timeTextView = findViewById<TextView>(R.id.detailTime)
        val ingredientsTextView = findViewById<TextView>(R.id.detailIngredients)
        val descTextView = findViewById<TextView>(R.id.detailDesc)
        val imageView = findViewById<ImageView>(R.id.detailImage)

        nameTextView.text = name
        timeTextView.text = time
        ingredientsTextView.text = getString(ingredients)
        descTextView.text = getString(desc)
        imageView.setImageResource(image)
    }
}