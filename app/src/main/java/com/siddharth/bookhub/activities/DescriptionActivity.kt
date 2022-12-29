package com.siddharth.bookhub.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siddharth.bookhub.R
import com.siddharth.bookhub.database.BookDatabase
import com.siddharth.bookhub.database.BookEntity
import com.siddharth.bookhub.utils.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var Progressbar: ProgressBar
    lateinit var ProgressLayout: RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId: String? = "102"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)


        txtBookName = findViewById(R.id.txtDescBookName)
        txtBookAuthor = findViewById(R.id.txtDescAuthorName)
        txtBookPrice = findViewById(R.id.txtDescBookPrice)
        imgBookImage = findViewById(R.id.imgDescBookCover)
        txtBookDesc = findViewById(R.id.txtDescription)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        Progressbar = findViewById(R.id.descProgressbar)
        txtBookRating=findViewById(R.id.txtDescBookRating)
        Progressbar.visibility = View.VISIBLE
        ProgressLayout = findViewById(R.id.layoutDescProgressbar)
        ProgressLayout.visibility = View.VISIBLE
        toolbar=findViewById(R.id.DescriptionToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"



        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected error occurred!!1",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some  Unexpected  error occurred!!2",
                Toast.LENGTH_SHORT
            ).show()

        }
        println("book id is ${bookId}")

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id",bookId)

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {
        val jsonRequest =object :JsonObjectRequest(Method.POST,url,jsonParams,Response.Listener {


//            try {
                   val success = it.getBoolean("success")
             println("success result is $success")

                   if(success) {
                       val bookJsonObject = it.getJSONObject("book_data")
                       val bookImageUrl = bookJsonObject.getString("image")

                       ProgressLayout.visibility = View.GONE
                       Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImage)
                       txtBookName.text = bookJsonObject.getString("name")
                       txtBookAuthor.text = bookJsonObject.getString("author")
                       txtBookPrice.text = bookJsonObject.getString("price")
                       txtBookRating.text = bookJsonObject.getString("rating")
                       txtBookDesc.text=bookJsonObject.getString("description")


                       val bookEntity=BookEntity(
                       bookId?.toInt() as Int,
                       txtBookName.text.toString(),
                       txtBookAuthor.text.toString(),
                       txtBookPrice.text.toString(),
                           txtBookRating.text.toString(),
                           txtBookDesc.text.toString(),
                           bookImageUrl


                       )

                       val checkFav = DBAsyncTask(applicationContext,bookEntity,1).execute()

                       val isFav = checkFav.get()


                       if(isFav){

                           btnAddToFav.text = "Remove from Favourites"
                           val favColor = ContextCompat.getColor(applicationContext,R.color.color_Favourite)
                           btnAddToFav.setBackgroundColor(favColor)
                       }else
                       {
                           btnAddToFav.text = "Add to Favourites"
                           val nofavColor = ContextCompat.getColor(applicationContext,R.color.color_primary)
                           btnAddToFav.setBackgroundColor(nofavColor)

                       }

                       btnAddToFav.setOnClickListener {
                           if(!DBAsyncTask(applicationContext,bookEntity,1).execute().get())
                           {
                              val async =  DBAsyncTask(applicationContext,bookEntity,2).execute()
                               val result = async.get()

                               if(result)
                               {
                                   Toast.makeText(this@DescriptionActivity,"Book is Added to Favourite",Toast.LENGTH_SHORT).show()
                                   btnAddToFav.text = "Remove from Favourites"
                                   val favColor = ContextCompat.getColor(applicationContext,R.color.color_Favourite)
                                   btnAddToFav.setBackgroundColor(favColor)

                               }

                               else
                               {
                                   Toast.makeText(this@DescriptionActivity,"Some Unexpected error was occurred!!3",Toast.LENGTH_SHORT).show()
                               }
                           }
                           else{

                               val async =  DBAsyncTask(applicationContext,bookEntity,3).execute()
                               val result = async.get()

                               if(result)
                               {
                                   Toast.makeText(this@DescriptionActivity,"Book Removed from Favourites",Toast.LENGTH_SHORT).show()
                                   btnAddToFav.text = "Add to Favourites"
                                   val nofavColor = ContextCompat.getColor(applicationContext,R.color.color_primary)
                                   btnAddToFav.setBackgroundColor(nofavColor)

                               }

                               else
                               {
                                   Toast.makeText(this@DescriptionActivity,"Some Unexpected error was occurred!!4",Toast.LENGTH_SHORT).show()
                               }

                           }
                       }


                   }else {
                       Toast.makeText(this@DescriptionActivity,"Some Error Occurred ! ! !5 ",Toast.LENGTH_SHORT).show()
                   }

//                 }catch (e:Exception)
//               {
//                   Toast.makeText(this@DescriptionActivity,"Some Unexpected error Occurred!!!6",Toast.LENGTH_SHORT).show()
//               }

        },Response.ErrorListener {
            Toast.makeText(this@DescriptionActivity, "Volley error occurred!!!7",Toast.LENGTH_SHORT).show()
        })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Content-type"]= "application/json"
                headers["token"] = "1fcca1c2f8a71c"
                return headers
            }
        }

      queue.add(jsonRequest)

    }else
        {

            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is Not Found")
            dialog.setPositiveButton("Open settings ") {text,listener ->
                //Do Nothing
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Cancel") {text,listener ->
                //Do Nothing
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }

    }

    class DBAsyncTask(val context: Context,val bookEntity: BookEntity,val mode:Int): AsyncTask<Void,Void,Boolean>(){

        /*
        Mode 1 -> Check DB if the book is favourite or not
        Mode 2 -> Save the  book into DB as favourite
        Mode 3-> Remove the favourite book
         */

       val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()



        override fun doInBackground(vararg p0: Void?): Boolean {

            when(mode){

                1 -> {

                   // Check DB if the book is favourite or not

                    val book:BookEntity?= db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()

                    return book != null
                }

                2 -> {

                    //Mode 2 -> Save the  book into DB as favourite

                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }

                3 -> {

                    //Remove the favourite book
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }




            return false
        }



    }
}