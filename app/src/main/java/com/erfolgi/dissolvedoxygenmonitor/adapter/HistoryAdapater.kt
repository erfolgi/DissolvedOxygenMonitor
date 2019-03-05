package com.erfolgi.dissolvedoxygenmonitor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.erfolgi.dissolvedoxygenmonitor.R
import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HistoryAdapter (private val context: Context, private val items: List<Feed>)
    : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>(){
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_history, parent, false)
        return HistoryHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss", Locale.getDefault())
        //val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())
     //   val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss z")
       // formatter.timeZone = TimeZone.getTimeZone("CET")
//Etc/GMT+7

        val date = LocalDateTime.parse(items[position].createdAt, inputFormatter)
        date.plusHours(7)
        //dateFormatter.
        //val formattedTime = timeFormatter.format(date)
        val formattedDate = dateFormatter.format(date.plusHours(7))

        //println(formattedDate) // prints 10-04-2018
        holder.date.text=formattedDate
        holder.DO.text=items[position].field1
        if (items[position].field3=="2.00"||items[position].field3=="2.0"){
            holder.fuzzy.text="ON"
            if(items[position].field2=="1.00"||items[position].field2=="1.0"){
                holder.fuzzy.text="ON ("+items[position].field4+"ms)"
            }

        }else{
            holder.fuzzy.text="OFF"
        }
        if(items[position].field2=="1.00"||items[position].field2=="1.0"){
            holder.status.text="ON"
        }else{
            holder.status.text="OFF"
        }
        println(formattedDate+"\t"+items[position].field1+"\t"+items[position].field2+"\t"+items[position].field4)
    }
    @SuppressLint("SimpleDateFormat")
    private fun toGMTFormat(datime: String?): Date? {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.parse(datime)
    }

    class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date=itemView.findViewById<TextView>(R.id.h_date)
        val DO=itemView.findViewById<TextView>(R.id.h_do)
        val fuzzy=itemView.findViewById<TextView>(R.id.h_fuzzy)
        val status=itemView.findViewById<TextView>(R.id.h_status)
        fun binder(){

        }
    }
}