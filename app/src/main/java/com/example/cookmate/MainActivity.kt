package com.example.cookmate

import ListData
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter: ListAdapter
    private var dataArrayList = ArrayList<ListData?>()
    private var filteredList = ArrayList<ListData?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dummy data untuk daftar resep
        val imageList = intArrayOf(
            R.drawable.pasta,
            R.drawable.maggi,
            R.drawable.cake,
            R.drawable.pancake,
            R.drawable.pizza,
            R.drawable.burger,
            R.drawable.fries
        )
        val nameList = arrayOf("Pasta", "Maggi", "Cake", "Pancake", "Pizza", "Burgers", "Fries")
        val timeList = arrayOf("30 mins", "2 mins", "45 mins", "10 mins", "60 mins", "45 mins", "30 mins")
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

        for (i in imageList.indices) {
            dataArrayList.add(ListData(nameList[i], timeList[i], ingredientList[i], descList[i], imageList[i]))
        }

        filteredList.addAll(dataArrayList) // Mulai dengan daftar penuh

        // Inisialisasi ListAdapter untuk ListView
        listAdapter = ListAdapter(this, filteredList) { recipe ->
            toggleFavorite(recipe)
        }
        val listView = findViewById<ListView>(R.id.listview)
        listView.adapter = listAdapter

        // Tangani klik pada item untuk membuka detail
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRecipe = filteredList[position]
            if (selectedRecipe != null) {
                val intent = Intent(this, DetailedActivity::class.java)
                intent.putExtra("name", selectedRecipe.name)
                intent.putExtra("time", selectedRecipe.time)
                intent.putExtra("ingredients", selectedRecipe.ingredients)
                intent.putExtra("desc", selectedRecipe.desc)
                intent.putExtra("image", selectedRecipe.image)
                startActivity(intent)
            }
        }

        // FloatingActionButton untuk membuka halaman favorit
        val fabFavorites = findViewById<FloatingActionButton>(R.id.btnFavorites)
        fabFavorites.setOnClickListener {
            openFavoriteRecipes()
        }

        // SearchView untuk mencari resep
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // Tidak melakukan apa-apa saat submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                filteredList.clear()
                filteredList.addAll(dataArrayList.filter {
                    it?.name?.contains(query, ignoreCase = true) == true
                })
                listAdapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun toggleFavorite(recipe: ListData) {
        recipe.isFavorite = !recipe.isFavorite // Toggle status favorite
        listAdapter.notifyDataSetChanged()
    }

    private fun openFavoriteRecipes() {
        val favoriteList = ArrayList(dataArrayList.filter { it?.isFavorite == true })

        if (favoriteList.isEmpty()) {
            Toast.makeText(this, "No favorite recipes available", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, FavoriteRecipesActivity::class.java)
        intent.putExtra("favoriteList", favoriteList)
        intent.putExtra("dataArrayList", dataArrayList)

        // Gunakan startActivityForResult untuk menangani data yang dikembalikan
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Tangani hasil dari FavoriteRecipesActivity
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val updatedList = data?.getSerializableExtra("updatedList") as? ArrayList<ListData?>
            if (updatedList != null) {
                dataArrayList.clear()
                dataArrayList.addAll(updatedList)
                filteredList.clear()
                filteredList.addAll(dataArrayList) // Sinkronkan filteredList
                listAdapter.notifyDataSetChanged()
            }
        }
    }
}