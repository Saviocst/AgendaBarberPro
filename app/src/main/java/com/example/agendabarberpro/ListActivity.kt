package com.example.agendabarberpro

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agendabarberpro.adapter.MainAdapter
import com.example.agendabarberpro.model.EventClick
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity(), EventClick {

    private val data: MutableList<DataBaseHelper.Companion.Cadastro> = ArrayList()
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val builder = intent.extras

        if (builder == null) finish() else{
            val type = builder.getString("type")!!

            rvMain = findViewById(R.id.lista_recycler_agenda)

            val bd = DataBaseHelper.getInstancia(this)
            data.addAll(bd!!.allRecords(type))

            val adapter = MainAdapter(data, this)
            rvMain.adapter = adapter
            rvMain.layoutManager = LinearLayoutManager(this)

        }
    }

    override fun onClick(id: Int, type: String, name: String, hour: String) {
        if (type == DataBaseHelper.TYPE_SCHEDULE){

            val alertEdit = AlertDialog.Builder(this)
                .setMessage("Deseja editar esse cadastro?")
                .setNegativeButton(R.string.sim){ _, _ ->
                    val intent = Intent(this, AgendaActivity::class.java)
                    intent.putExtra("editId", id)
                    startActivity(intent)
                }
                .setPositiveButton(R.string.nao){dialog: DialogInterface, _: Int -> dialog.dismiss()}
                .create()
            alertEdit.show()
        }
    }

    override fun onLongClick(position: Int, type: String, id: Int) {
        val alertDelete = AlertDialog.Builder(this)
            .setMessage("Deseja excluir esse cadastro?")
            .setNegativeButton(R.string.sim){_, _ ->
                val dataBaseHelper = DataBaseHelper.getInstancia(this)
                val calcId = dataBaseHelper?.deleteData(id, type)!!

                if (calcId > 0 ){
                    data.removeAt(position)
                    Toast.makeText(this, "Registro Removido!", Toast.LENGTH_SHORT).show()
                    rvMain.adapter?.notifyDataSetChanged()
                }
            }
            .setPositiveButton(R.string.nao){dialog: DialogInterface, _: Int -> dialog.dismiss()}
            .create()
        alertDelete.show()
    }
}