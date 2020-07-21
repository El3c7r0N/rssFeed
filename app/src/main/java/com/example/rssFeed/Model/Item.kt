package com.example.rssFeed.Model

import java.util.*

data class Item(val title:String, val pubDate:String,val link:String, val guid:String, val author: String, val thumbnail:String, val content:String, val enclosure:Object, val categories:List<String>)
