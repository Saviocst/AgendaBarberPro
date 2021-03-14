package com.example.agendabarberpro.adapter

import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agendabarberpro.DataBaseHelper
import com.example.agendabarberpro.R
import com.example.agendabarberpro.model.EventClick

class MainViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    fun bind(data: DataBaseHelper.Companion.Cadastro, eventClick: EventClick){
        val nameClient = itemView.findViewById<TextView>(R.id.name_client)
        val hourClient = itemView.findViewById<TextView>(R.id.hour_client)
        val dayWeek = itemView.findViewById<TextView>(R.id.day_client)

        nameClient.text = data.name
        hourClient.text = data.hour
        dayWeek.text = data.day


        itemView.setOnClickListener {
            eventClick.onClick(data.id, data.type.toString(), data.name.toString(), data.hour.toString(), data.day.toString())
        }

        itemView.setOnLongClickListener{
            eventClick.onLongClick(adapterPosition, data.type.toString(), data.id)
            false
        }
    }

}