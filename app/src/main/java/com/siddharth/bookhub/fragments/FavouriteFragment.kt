package com.siddharth.bookhub.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.siddharth.bookhub.R
import com.siddharth.bookhub.adapters.FavouriteRecyclerAdapter
import com.siddharth.bookhub.database.BookDatabase
import com.siddharth.bookhub.database.BookEntity
import kotlinx.android.synthetic.main.fragment_dashboard.*


class FavouriteFragment : Fragment() {

    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var recyclerFavourite:RecyclerView
    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var favProgressbar: ProgressBar
    lateinit var favProgressLayout: RelativeLayout
    var dbBookList= listOf<BookEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        recyclerFavourite = view.findViewById(R.id.favouriteRecyclerView)
        favProgressLayout =view.findViewById(R.id.favProgressbar_layout)
        favProgressbar = view.findViewById(R.id.favProgressBar)


        layoutManager = GridLayoutManager(activity as Context,2)


        dbBookList = RetrieveFavourite(activity as Context).execute().get() //since we don not use application as context bcoz fragment can't access whole application

        if(activity != null)
        {
            favProgressLayout.visibility = View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }


        return view
    }

    class RetrieveFavourite( val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){


        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context, BookDatabase::class.java,"books-db").build()

            return db.bookDao().getAllBook()

        }

    }



}


