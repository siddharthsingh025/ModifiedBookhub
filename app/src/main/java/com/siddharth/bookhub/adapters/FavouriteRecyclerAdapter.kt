package com.siddharth.bookhub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.bookhub.R
//import com.siddharth.bookhub.activities.DescriptionActivity
//import com.siddharth.bookhub.database.BookEntity
import com.siddharth.bookhub.database.SongEntity
import com.squareup.picasso.Picasso


//class FavouriteRecyclerAdapter (val context :Context,val bookList:List<BookEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
//
//
//    class FavouriteViewHolder(view:View):RecyclerView.ViewHolder(view){
//
//        val txtBookName: TextView = view.findViewById(R.id.txtFavBookTitle)
//        val txtBookAuthor: TextView = view.findViewById(R.id.txtFavBookAuthor)
//        val txtBookPrice: TextView = view.findViewById(R.id.txtFavBookPrice)
//        val txtBookRating: TextView = view.findViewById(R.id.txtFavBookRating)
//        val imgBookImage: ImageView = view.findViewById(R.id.imgFavBookImage)
//        val content11: LinearLayout = view.findViewById(R.id.Fav11Content)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.recycler_favourite_single_row,parent,false)
//
//        return FavouriteViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
//        val book = bookList[position]
//        holder.txtBookName.text=book.bookName
//        holder.txtBookAuthor.text = book.bookAuthor
//        holder.txtBookPrice.text = book.bookPrice
//        holder.txtBookRating.text = book.bookRating
//        Picasso.get().load(book.bookImage).error(R.drawable.new_book_cover).into(holder.imgBookImage)
//        holder.content11.setOnClickListener{
//            val intent = Intent(context, DescriptionActivity::class.java)
//            intent.putExtra("book_id",book.book_id.toString())
//            context.startActivity(intent)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return bookList.size
//    }
//}




class FavouriteRecyclerAdapter (val context :Context,val bookList:List<SongEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {


    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val songName: TextView = view.findViewById(R.id.txtFavBookTitle)
        val songArtist: TextView = view.findViewById(R.id.txtFavBookAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row, parent, false)

        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]
        holder.songName.text = book.songName
        holder.songArtist.text = book.songArtist
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}