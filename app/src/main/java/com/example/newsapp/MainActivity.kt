package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import android.view.ViewOutlineProvider
import android.widget.EdgeEffect
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.newsapp.MySingleton.Companion as MySingleton


class MainActivity : AppCompatActivity(), itemClicked {

    private lateinit var madapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
        newsItemRecycler.layoutManager = LinearLayoutManager(this)
        madapter = RecyclerViewAdapter(this)
        newsItemRecycler.adapter = madapter
    }

    private fun fetchData() {

        val url =
            "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=52f9808f3e244e5481f958e928dbc855"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                val articles = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                Toast.makeText(this,"Total Articles Fetch ${articles.length()}",Toast.LENGTH_LONG).show()
                for (i in 0 until articles.length()) {
                    val newJsonObject = articles.getJSONObject(i)
                    val news = News(
                        newJsonObject.getString("author"),
                        newJsonObject.getString("description"),
                        newJsonObject.getString("urlToImage"),
                        newJsonObject.getString("url"))
                    newsArray.add(news)
                }
                madapter.updateData(newsArray)
            },
            Response.ErrorListener { error ->

            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    override fun itemSelected(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customeTabsIntent = builder.build()
        customeTabsIntent.launchUrl(this,Uri.parse(item.url))
    }
}