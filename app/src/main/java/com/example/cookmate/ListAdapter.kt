package com.example.cookmate

import ListData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(
    context: Context,
    private val dataArrayList: ArrayList<ListData?>,
    private val onFavoriteClicked: (ListData) -> Unit // Callback untuk klik favorit
) : ArrayAdapter<ListData?>(context, R.layout.list_item, dataArrayList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val listData = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val listImage = view!!.findViewById<ImageView>(R.id.listImage)
        val listName = view.findViewById<TextView>(R.id.listName)
        val listTime = view.findViewById<TextView>(R.id.listTime)
        val favoriteIcon = view.findViewById<ImageView>(R.id.favoriteIcon)

        listImage.setImageResource(listData!!.image)
        listName.text = listData.name
        listTime.text = listData.time

        // Update ikon favorit berdasarkan status isFavorite
        val favoriteRes = if (listData.isFavorite) R.drawable.starred else R.drawable.unstarred
        favoriteIcon.setImageResource(favoriteRes)

        // Tangani klik pada ikon favorit
        favoriteIcon.setOnClickListener {
            onFavoriteClicked(listData) // Panggil callback untuk item ini
        }

        return view
    }
}