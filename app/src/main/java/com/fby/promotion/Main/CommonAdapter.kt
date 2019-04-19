package com.fby.promotion.Main

import android.support.v7.widget.RecyclerView
import android.view.View


/**
 *    author : fby
 *    date   : 2019/4/16
 *    desc   :
 *    version: 1.0
 */


 abstract  class CommonAdapter<T> (open var items : List<T>, open var itemClickListener: (T)->Unit)
    : RecyclerView.Adapter<CommonAdapter.ViewHolder<T>>() {

     val HEADER = 0
     val NORMAL = 1
     val FOOTER = 2

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(items[position],position)
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> {
            HEADER
        }
        itemCount - 1 -> {
            FOOTER
        }
        else -> {
            NORMAL
        }
    }


    abstract  class ViewHolder<T>(open val view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(data: T,position: Int) {

        }
    }



}