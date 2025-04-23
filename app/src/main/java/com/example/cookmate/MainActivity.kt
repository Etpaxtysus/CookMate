package com.example.cookmate

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.cookmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var listData: ListData
    var dataArrayList = ArrayList<ListData?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = intArrayOf(
            R.drawable.pasta,
            R.drawable.maggi,
            R.drawable.cake,

            R.drawable.pancake,
            R.drawable.pizza,
            R.drawable.burger,
            R.drawable.fries
        )
        val ingredientList = intArrayOf(
            R.string.pastaIngredients,
            R.string.maggiIngredients,
            R.string.cakeIngredients,
            R.string.pancakeIngredients,
            R.string.pizzaIngredients,
            R.string.burgerIngredients,
            R.string.friesIngredients
        )
        val descList = intArrayOf(
            R.string.pastaDesc,
            R.string.maggieDesc,
            R.string.cakeDesc,
            R.string.pancakeDesc,
            R.string.pizzaDesc,
            R.string.burgerDesc,
            R.string.friesDesc
        )
        val nameList = arrayOf("Pasta", "Maggi", "Cake", "Pancake", "Pizza", "Burgers", "Fries")
        val timeList = arrayOf("30 mins", "2 mins", "45 mins", "10 mins", "60 mins", "45 mins", "30 mins")

        for (i in imageList.indices) {
            listData = ListData(
                nameList[i],
                timeList[i], ingredientList[i], descList[i], imageList[i]
            )
            dataArrayList.add(listData)
        }

        listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        binding.listview.adapter = listAdapter
        binding.listview.isClickable = true

        binding.listview.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@MainActivity, DetailedActivity::class.java)
            intent.putExtra("name", nameList[i])
            intent.putExtra("time", timeList[i])
            intent.putExtra("ingredients", ingredientList[i])
            intent.putExtra("desc", descList[i])
            intent.putExtra("image", imageList[i])
            startActivity(intent)
        }
    }
}