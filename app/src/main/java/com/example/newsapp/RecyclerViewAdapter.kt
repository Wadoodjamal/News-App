package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class RecyclerViewAdapter( private val listener: itemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket,parent,false)
        val viewHolder = NewsViewHolder(rootView)
        viewHolder.itemView.setOnClickListener{
            listener.itemSelected(items[viewHolder.adapterPosition])
        }
        return  viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.author
        holder.description.text = currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.urlToImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(updateItem: ArrayList<News>){
        items.clear()
        items.addAll(updateItem)
        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.itemText)
    val description: TextView = itemView.findViewById(R.id.descriptionText)
    val urlToImage: ImageView = itemView.findViewById(R.id.imageView)
}


interface itemClicked{
    fun itemSelected(item: News)
}