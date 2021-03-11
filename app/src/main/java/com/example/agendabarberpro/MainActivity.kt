package com.example.agendabarberpro

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.cardview.widget.CardView

lateinit var cardAgendar: CardView
lateinit var cardSair: CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardAgendar = findViewById(R.id.card_view_agendar)
        cardSair = findViewById(R.id.card_view_sair)

        cardAgendar.setOnClickListener {
            startActivity(Intent(this, AgendaActivity::class.java ))
        }

        cardSair.setOnClickListener { finish() }




    }

}