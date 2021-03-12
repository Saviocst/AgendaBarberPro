package com.example.agendabarberpro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendabarberpro.DataBaseHelper
import com.example.agendabarberpro.R
import com.example.agendabarberpro.model.EventClick

class MainAdapter (private val data: List<DataBaseHelper.Companion.Cadastro>, private var listner: EventClick) : RecyclerView.Adapter<MainViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_agenda_personalizada, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val dateCurrent = data[position]

        holder.bind(dateCurrent, listner)

    }

    override fun getItemCount(): Int = data.size

}