package com.example.rssfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var Binding: ActivityMainBinding
    lateinit var recViewFeed: RecyclerView
    val FeedList = ArrayList<FeedData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)
        recViewFeed = findViewById(R.id.rvMain)
        recViewFeed.layoutManager = LinearLayoutManager(this)
        getFeedData()

    }


    fun getFeedData() {
        CoroutineScope(Dispatchers.IO).launch {

            var dataFeed = async { fetchFeedData() }.await()

            withContext(Dispatchers.Main)
            {

                recViewFeed.adapter = RVAdapter(FeedList)
                recViewFeed.adapter!!.notifyDataSetChanged()

            }
        }

    }

    fun fetchFeedData() {
        var feedTitle = ""
        var rankFeed = 0
        var text = ""
        try {
            val parsingFactory = XmlPullParserFactory.newInstance()
            val parsing = parsingFactory.newPullParser()
            val url = URL("https://stackoverflow.com/feeds")
            parsing.setInput(url.openStream(), null)
            var eventType = parsing.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parsing.name
                when (eventType) {
                    XmlPullParser.TEXT -> {
                        text = parsing.text
                    }
                    XmlPullParser.END_TAG -> when (tagName) {
                        "title" -> {
                            feedTitle = text.toString()
                        }
                        "rank" -> {
                            rankFeed = text.toInt()

                        }
                        else -> {

                            if (!feedTitle.isEmpty()) {
                                val studen = FeedData(feedTitle, rankFeed)
                                FeedList.add(studen)
                            }
                            feedTitle = ""


                        }
                    }
                    else -> {

                    }
                }
                eventType = parsing.next()
            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

}