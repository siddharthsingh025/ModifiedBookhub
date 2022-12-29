package com.siddharth.bookhub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.siddharth.bookhub.R
import com.siddharth.bookhub.activities.DescriptionActivity
import com.siddharth.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context: Context,val itemList: ArrayList<Book>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)

        return  DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
       // holder.imgBookImage.setImageResource(book.bookImage)
        println("The book URL is ${book.bookImage}")
        Picasso.get().load(book.bookImage).error(R.drawable.new_book_cover).into(holder.imgBookImage)

        holder.count11.setOnClickListener{
            val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size

    }



    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgRecyclerBookImage)
        val count11:LinearLayout = view.findViewById(R.id.Context11)

    }


}