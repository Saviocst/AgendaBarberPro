package com.example.agendabarberpro.ui.agenda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendabarberpro.R
import com.example.agendabarberpro.repository.AgendaRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AgendaViewModel(private val repository: AgendaRepository) : ViewModel() {

    private val _agendaStateEventData = MutableLiveData<AgendaState>()
    val agendaStateEventData: LiveData<AgendaState>
    get() = _agendaStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun  addAgenda(name: String, time: String) = viewModelScope.launch{

        try {
            val id = repository.insertAgenda(name, time)
            if (id > 0){
                _agendaStateEventData.value = AgendaState.Inserted
                _messageEventData.value = R.string.agedamento_confirm_db
            }

        } catch (ex: Exception) {
            _messageEventData.value = R.string.agedamento_error_db
            Log.e(TAG, ex.toString())
        }

    }

    sealed class AgendaState{
        object Inserted : AgendaState()
    }

    companion object{
        private val TAG = AgendaViewModel::class.java.simpleName
    }
}

