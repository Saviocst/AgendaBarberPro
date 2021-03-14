package com.example.agendabarberpro.model

interface EventClick {
    fun onClick(id: Int, type: String, name: String, hour: String, day: String)
    fun onLongClick(position: Int, type: String, id: Int)
}