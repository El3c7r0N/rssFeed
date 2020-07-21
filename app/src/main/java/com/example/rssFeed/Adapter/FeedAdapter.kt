package com.example.rssFeed.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.rssFeed.Interface.ItemClickListener
import com.example.rssFeed.Model.RSSObject
import com.example.rssFeed.R
import kotlinx.android.synthetic.main.row.view.*

class FeedViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
{
    var txtTitle:TextView
    var txtContent:TextView
    var txtPubdate:TextView

    private var itemClickListener : ItemClickListener?=null

    init {
        txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
        txtPubdate = itemView.findViewById(R.id.txtPubDate) as TextView
        txtContent = itemView.findViewById(R.id.txtContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }

}

@Suppress("NAME_SHADOWING")
class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context):
    RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater : LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubdate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content

        holder.setItemClickListener(itemClickListener = ItemClickListener { _, position: Int, isLongClick ->

            if(!isLongClick) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(browserIntent)
            }
        })
    }

}