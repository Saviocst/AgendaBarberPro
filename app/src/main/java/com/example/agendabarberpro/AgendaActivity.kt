package com.example.agendabarberpro


import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.*
import androidx.lifecycle.ViewModel
import com.example.agendabarberpro.repository.AgendaRepository
import com.muddzdev.styleabletoastlibrary.StyleableToast


class AgendaActivity : AppCompatActivity() {

    lateinit var buttonAgendar: Button
    lateinit var editName: EditText
    lateinit var editHoras: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        editName = findViewById(R.id.edit_name)
        editHoras = findViewById(R.id.edit_timer)
        buttonAgendar = findViewById(R.id.button_agendar)


        buttonAgendar.setOnClickListener {

            if (!validate()) {
                //Toast.makeText(this, R.string.error_message,Toast.LENGTH_SHORT).show()
                StyleableToast.makeText(this, "Preencha os campos vazios", R.style.ToastCustom)
                    .show()
                return@setOnClickListener
            }

            //val name = editName.text.toString()
            //val time = editHoras.text.toString()
            //var resultd = result(name, time)

            dialogWindow()
        }

        editHoras.addTextChangedListener(object : TextWatcher {

            var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // Quando o texto é alterado o onTextChange é chamado
                // essa flag evita a chamada infinita desse metodo

                if(isUpdating){
                    isUpdating = false
                    return
                }

                // Ao apagar o texto, a mascara é removida então o posicionamento
                // do cursor precisa saber se o texto atual tinha ou não mascara
                val hasMask = s.toString().indexOf(':') > -1

                // Remove o "." e "-" da string
                var str = s.toString().filterNot { it == ':' }


                // Os parametros before e count dizem o tamanho
                // anterior e atual da string digitada, se count > before é
                // porque está digitando, caso contrario esta apagando
                if (count > before){

                    // Se tem mais de 2 coloca o ponto
                    if (str.length > 2){
                        str = "${str.substring(0, 2)}:${str.substring(2)}"
                    }

                    // Seta a flag pata evitar a chamada infinita
                    isUpdating = true

                    // Seta o novo texto
                    editHoras.setText(str)

                    // Seta a posição do cursor
                    editHoras.setSelection(editHoras.text?.length ?: 0)

                } else {
                    isUpdating = true
                    editHoras.setText(str)

                    // Se estiver apagando posiciona o cursor no lugar correto
                    // Isso trata da deleção dos caracteres da máscara
                    editHoras.setSelection(Math.max(0, Math.min(if (hasMask) start - before else start, str.length)))
                }
            }
        })

    }

    private fun dialogWindow(){
        val builder = AlertDialog.Builder(this, R.style.AlertDialogSv)
        val view = LayoutInflater.from(this).inflate(
            R.layout.layout_dialog_result_custom, null
        )

        builder.setView(view)

        view.findViewById<TextView>(R.id.dialog_title_sv).text = resources.getString(R.string.agedamento_confirm)

        view.findViewById<TextView>(R.id.result_agenda).text = result(editName.text.toString(), editHoras.text.toString())
        //view.findViewById<TextView>(R.id.result_agenda).text = resources.getString(R.string.resultado)

        view.findViewById<Button>(R.id.button_ok).text = resources.getString(R.string.btn_ok)
        view.findViewById<Button>(R.id.button_save).text = resources.getString(R.string.btn_salvar)

        view.findViewById<ImageView>(R.id.dialog_image_sv).setImageResource(R.drawable.ic_dialog_done_sv)

        val alertDialog: AlertDialog = builder.create()

        view.findViewById<Button>(R.id.button_ok).setOnClickListener { alertDialog.dismiss() }

        view.findViewById<Button>(R.id.button_save).setOnClickListener { alertDialog.dismiss() }

        if(alertDialog.window != null){
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        alertDialog.show()
    }


    private fun validate(): Boolean {
        if (editName.text.toString().isNotEmpty() && editHoras.text.toString().isNotEmpty()) {
            return true
        }
        return false
    }

    private fun result(name: String, time: String): String {
        return "Cliente: $name \nHoras: $time"
    }

    /*private fun toastCustom(view: View){
        StyleableToast.makeText(this,"Preencha os campos vazios" , R.style.ToastCustom).show()
    }*/
}