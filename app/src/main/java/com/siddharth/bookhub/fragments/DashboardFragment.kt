package com.siddharth.bookhub.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siddharth.bookhub.R
import com.siddharth.bookhub.adapters.DashboardRecyclerAdapter
import com.siddharth.bookhub.model.Book
import com.siddharth.bookhub.utils.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator


class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard:RecyclerView
    lateinit var layoutManager : RecyclerView.LayoutManager

    lateinit var progressbayLayout :RelativeLayout
    lateinit var progressbar:ProgressBar

    lateinit var recyclerAdapter:DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>()


    val ratingComparator = Comparator<Book>{book1,book2 ->
        if ( book1.bookRating.compareTo(book2.bookRating,true)==0){
            //sort according to name if rating is same
            book1.bookName.compareTo(book2.bookName,true)
        }else
        {  //sort according to rating
            book1.bookRating.compareTo(book2.bookRating,true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //this method is only use to adding menu item in fragment
        setHasOptionsMenu(true)


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)


        progressbayLayout= view.findViewById(R.id.progressbar_layout)
        progressbar = view.findViewById(R.id.ProgressBar)

        progressbayLayout.visibility= View.VISIBLE


        recyclerDashboard= view.findViewById(R.id.dashboardRecyclerView)

        layoutManager = LinearLayoutManager(activity)





        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        if (ConnectionManager().checkConnectivity(activity as Context)) {

            val jsonObjectRequest= object : JsonObjectRequest(Method.GET,url,null,Response.Listener {
                //Here we will handle the Response

                val success = it.getBoolean("success")

                try {
                    progressbayLayout.visibility =View.GONE

                    if (success) {
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")

                            )
                            bookInfoList.add(bookObject)

                            recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

                            recyclerDashboard.adapter = recyclerAdapter
                            recyclerDashboard.layoutManager = layoutManager

                        }
                    }

                    else {
                        Toast.makeText(activity as Context,"Some Error Occurred ! ! ! ",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException)
                {
                    Toast.makeText(activity as Context,"Some Unexpected error Occurred!!!",Toast.LENGTH_SHORT).show()
                }
            }
                ,Response.ErrorListener {
                    //Here we will handle the Errors
                    Toast.makeText(activity as Context, "Volley error occurred!!!",Toast.LENGTH_SHORT).show()
                })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"] = "application/json"
                    headers["token"]="1fcca1c2f8a71c"
                    return headers
                }

            }

            queue.add(jsonObjectRequest)
        }
        else{

            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is Not Found")
            dialog.setPositiveButton("Open settings ") {text,listener ->
                //Do Nothing
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Cancel") {text,listener ->
                //Do Nothing
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        if (id==R.id.action_sort)
        {
            Collections.sort(bookInfoList,ratingComparator)//it does in ascending order
            bookInfoList.reverse()//to revers ascending to descending order

        }

        recyclerAdapter.notifyDataSetChanged()//to tell recycleraddapter to change layout

        return super.onOptionsItemSelected(item)
    }

}