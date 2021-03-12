package com.example.agendabarberpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

private lateinit var cardAgendar: CardView
private lateinit var cardSair: CardView
private lateinit var cardList: CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardAgendar = findViewById(R.id.card_view_agendar)
        cardSair = findViewById(R.id.card_view_sair)
        cardList = findViewById(R.id.card_view_lista)

        cardAgendar.setOnClickListener {
            startActivity(Intent(this, AgendaActivity::class.java))
        }

        cardList.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            intent.putExtra("type", DataBaseHelper.TYPE_SCHEDULE)
            startActivity(intent)
        }

        cardSair.setOnClickListener { finish() }






    }

}