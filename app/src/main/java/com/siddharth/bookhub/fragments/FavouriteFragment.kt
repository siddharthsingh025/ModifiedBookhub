package com.siddharth.bookhub.fragments

//import com.siddharth.bookhub.database.BookEntity

import android.R.id.text1
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.siddharth.bookhub.R
import com.siddharth.bookhub.adapters.FavouriteRecyclerAdapter
import com.siddharth.bookhub.database.BookDatabase
import com.siddharth.bookhub.database.SongEntity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import java.io.InputStream
import kotlin.concurrent.thread


class FavouriteFragment : Fragment() {


    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerFavourite: RecyclerView
    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var favProgressbar: ProgressBar
    lateinit var favProgressLayout: RelativeLayout
    lateinit var addButton: Button
    lateinit var songEntity: SongEntity
    var dbBookList = listOf<SongEntity>()
    var AudioUri: Uri? = null
//    lateinit var contentResolver: ContentResolver

    private val PICK_AUDIO = 1

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        recyclerFavourite = view.findViewById(R.id.favouriteRecyclerView)
        favProgressLayout = view.findViewById(R.id.favProgressbar_layout)
        favProgressbar = view.findViewById(R.id.favProgressBar)
        addButton = view.btnAdd

        layoutManager = LinearLayoutManager(activity as Context)
        dbBookList = RetrieveFavourite(activity as Context).execute().get() //since we don not use application as context bcoz fragment can't access whole application
        Log.d(ContentValues.TAG, "Songs=: " + dbBookList.toString());
        if (activity != null) {
            favProgressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }



        addButton.setOnClickListener {

            val audio = Intent()
            audio.type = "audio/*"
            audio.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO)
        }





        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val mediaMetadataRetriever = MediaMetadataRetriever()

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_AUDIO && resultCode == Activity.RESULT_OK) {
            // Audio is Picked in format of URI
            if (data != null) {
                AudioUri = data.data

                val inputStream: InputStream? = AudioUri?.let {
                    context?.contentResolver?.openInputStream(
                        it
                    )
                }
                val mp3Data = inputStream?.readBytes()
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(context,AudioUri)

                val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)

                retriever.release()
//                val db= context?.let { Room.databaseBuilder(it, BookDatabase::class.java,"books-db").build() }
//
//                db?.songDao()?.insertSong(SongEntity(title,artist,mp3Data))
                Log.d(ContentValues.TAG, "SongName: " + title + " Artist: " + artist + "   url: " + AudioUri.toString());

                thread {
                    val db= context?.let { Room.databaseBuilder(it, BookDatabase::class.java,"books-db").build() }
//
                     db?.songDao()?.insertSong(SongEntity(title,artist,AudioUri.toString()))
                    Log.d(ContentValues.TAG, "Songs=: " + db?.songDao()?.getAllSongs().toString());
                }
            }
        }
    }




}

//    class RetrieveFavourite( val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){
//
//
//        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
//            val db= Room.databaseBuilder(context, BookDatabase::class.java,"books-db").build()
//
//            return db.bookDao().getAllBook()
//
//            }
//
//        }
//
//
//
//    }



class RetrieveFavourite( val context: Context): AsyncTask<Void, Void, List<SongEntity>>(){


    @SuppressLint("SuspiciousIndentation")
    override fun doInBackground(vararg p0: Void?): List<SongEntity> {

        val db= Room.databaseBuilder(context, BookDatabase::class.java,"books-db").build()

            return db.songDao().getAllSongs()
    }

}




