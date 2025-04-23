package com.example.cookmate

import ListData
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class FavoriteRecipesActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: ListAdapter
    private var favoriteList = ArrayList<ListData?>()
    private var originalDataList = ArrayList<ListData?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Terima data dari Intent
        favoriteList = intent.getSerializableExtra("favoriteList") as? ArrayList<ListData?> ?: ArrayList()
        originalDataList = intent.getSerializableExtra("dataArrayList") as? ArrayList<ListData?> ?: ArrayList()

        // Inisialisasi adapter untuk daftar favorit
        favoriteAdapter = ListAdapter(this, favoriteList) { recipe ->
            toggleFavorite(recipe)
        }

        val listView = findViewById<ListView>(R.id.listviewFavorite)
        listView.adapter = favoriteAdapter

        // Tangani klik pada item untuk membuka detail
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedRecipe = favoriteList[position]
            val intent = Intent(this, DetailedActivity::class.java)
            intent.putExtra("name", selectedRecipe?.name)
            intent.putExtra("time", selectedRecipe?.time)
            intent.putExtra("ingredients", selectedRecipe?.ingredients)
            intent.putExtra("desc", selectedRecipe?.desc)
            intent.putExtra("image", selectedRecipe?.image)
            startActivity(intent)
        }

        // Atur custom handler untuk tombol "Back" menggunakan onBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Kirim data yang diperbarui kembali ke MainActivity
                val intent = Intent()
                intent.putExtra("updatedList", originalDataList)
                setResult(RESULT_OK, intent)
                finish() // Tutup activity
            }
        })
    }

    private fun toggleFavorite(recipe: ListData) {
        recipe.isFavorite = !recipe.isFavorite // Toggle favorite status
        if (!recipe.isFavorite) {
            favoriteList.remove(recipe) // Hapus dari daftar favorit jika dihapus
        }
        favoriteAdapter.notifyDataSetChanged()
    }
}