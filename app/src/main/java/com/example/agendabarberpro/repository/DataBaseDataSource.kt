package com.example.agendabarberpro.repository

import androidx.lifecycle.LiveData
import com.example.agendabarberpro.data.db.dao.AgendaDAO
import com.example.agendabarberpro.data.db.entity.AgendaEntity

class DataBaseDataSource(private val agendaDAO: AgendaDAO) : AgendaRepository {
    override suspend fun insertAgenda(name: String, time: String): Long {
        val agenda = AgendaEntity(
            name = name,
            time = time
        )
         return agendaDAO.insert(agenda)
    }

    override suspend fun updateAgenda(id: Long, name: String, time: String) {
        val agenda = AgendaEntity(
            id = id,
            name = name,
            time = time
        )
    }

    override suspend fun deleteAgenda(id: Long) {
        agendaDAO.delete(id)
    }

    override suspend fun deleteAllAgenda(){
        agendaDAO.deleteAll()
    }

    override fun getAllAgenda(): LiveData<List<AgendaEntity>> {
        return agendaDAO.getAll()
    }

}