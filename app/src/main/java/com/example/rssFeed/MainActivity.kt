@file:Suppress("DEPRECATION")

package com.example.rssFeed

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssFeed.Adapter.FeedAdapter
import com.example.rssFeed.Common.HTTPDataHandler
import com.example.rssFeed.Model.RSSObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.text.StringBuilder

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val RSS_link = "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml"
    private val RSS_to_JSON_API = " https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = "NEWS"
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycleView.layoutManager = linearLayoutManager

        loadRSS()
    }

    private fun loadRSS() {
        val loadRSSAsync = @SuppressLint("StaticFieldLeak")
        object:AsyncTask<String,String,String>() {
            val mDialog = ProgressDialog(this@MainActivity)
            override fun onPreExecute() {
                mDialog.setMessage("Tsenna ... ")
                mDialog.show()
            }

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                val rssObject:RSSObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java)
                val adapter = FeedAdapter(rssObject, baseContext)
                recycleView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun doInBackground(vararg params: String): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }

        }

        val urlGetData = StringBuilder(RSS_to_JSON_API)
        urlGetData.append(RSS_link)
        loadRSSAsync.execute(urlGetData.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh)
            loadRSS()
        return true
    }
}