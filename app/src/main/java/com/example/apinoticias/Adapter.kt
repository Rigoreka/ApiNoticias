package com.example.apinoticias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter (private  val news: List<Article>,private val onItemClick:(Article)-> Unit):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){


    inner class NewsViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val Title = itemView.findViewById<TextView>(R.id.txtTitle)
        private val Img = itemView.findViewById<ImageView>(R.id.imgNoti)


        fun bind(new: Article) {
            Title.text = new.title

            Glide.with(itemView.context)
                .load(new.image_url)
                .into(Img)

            // Configurar el clic en el itemView
            itemView.setOnClickListener {
                onItemClick(new)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_noticia, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val N = news[position]
        holder.bind(N)
    }
}