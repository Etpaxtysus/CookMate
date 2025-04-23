package com.example.cookmate

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SearchView
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.cookmate.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private var dataArrayList = ArrayList<ListData?>()
    private var filteredList = ArrayList<ListData?>()

    // Debounce variables
    private val debouncePeriod: Long = 300 // milliseconds
    private var searchJob: Job? = null

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
            val listData = ListData(
                nameList[i],
                timeList[i], ingredientList[i], descList[i], imageList[i]
            )
            dataArrayList.add(listData)
        }

        listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        binding.listview.adapter = listAdapter

        // Implement search functionality with debouncing
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel() // Cancel the previous search job
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(debouncePeriod)
                    performSearch(newText)
                }
                return true
            }
        })

        binding.listview.onItemClickListener = OnItemClickListener { _, _, i, _ ->
            val selectedRecipe = if (filteredList.isEmpty()) dataArrayList[i] else filteredList[i]
            val intent = Intent(this@MainActivity, DetailedActivity::class.java)
            intent.putExtra("name", selectedRecipe?.name)
            intent.putExtra("time", selectedRecipe?.time)
            intent.putExtra("ingredients", selectedRecipe?.ingredients)
            intent.putExtra("desc", selectedRecipe?.desc)
            intent.putExtra("image", selectedRecipe?.image)
            startActivity(intent)
        }
    }

    private fun performSearch(query: String?) {
        filteredList.clear()
        if (query.isNullOrEmpty()) {
            listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        } else {
            val lowercaseQuery = query.lowercase()
            filteredList.addAll(dataArrayList.filter { it?.name?.lowercase()?.contains(lowercaseQuery) == true })
            listAdapter = ListAdapter(this@MainActivity, filteredList)
        }
        binding.listview.adapter = listAdapter
    }
}