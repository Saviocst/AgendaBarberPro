package com.example.agendabarberpro.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.agendabarberpro.data.db.entity.AgendaEntity

@Dao
interface AgendaDAO {
    @Insert
    suspend fun insert(agenda: AgendaEntity):Long

    @Update
    suspend fun update(agenda: AgendaEntity)

    @Query("DELETE FROM agenda WHERE id = :id")
    suspend fun delete(id:Long)

    @Query("DELETE FROM agenda")
    suspend fun deleteAll()

    @Query("SELECT * FROM agenda")
    fun getAll(): LiveData<List<AgendaEntity>>


}