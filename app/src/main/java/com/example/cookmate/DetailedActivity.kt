package com.example.cookmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cookmate.databinding.ActivityDetailBinding

class DetailedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = this.intent
        if (intent != null) {
            val name = intent.getStringExtra("name")
            val time = intent.getStringExtra("time")
            val ingredients = intent.getIntExtra("ingredients", R.string.maggiIngredients)
            val desc = intent.getIntExtra("desc", R.string.maggieDesc)
            val image = intent.getIntExtra("image", R.drawable.maggi)

            binding.detailName.text = name
            binding.detailTime.text = time
            binding.detailDesc.setText(desc)
            binding.detailIngredients.setText(ingredients)
            binding.detailImage.setImageResource(image)
        }
    }
}