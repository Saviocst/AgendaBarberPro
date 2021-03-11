package com.example.agendabarberpro.repository

import androidx.lifecycle.LiveData
import com.example.agendabarberpro.data.db.entity.AgendaEntity

interface AgendaRepository {

    suspend fun insertAgenda(name: String, time: String): Long

    suspend fun updateAgenda(id: Long, name: String, time: String)

    suspend fun deleteAgenda(id: Long)

    suspend fun deleteAllAgenda()

    fun getAllAgenda(): LiveData<List<AgendaEntity>>
}