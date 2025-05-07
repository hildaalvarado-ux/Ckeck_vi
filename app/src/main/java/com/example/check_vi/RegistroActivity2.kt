package com.example.check_vi

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity2 : AppCompatActivity() {
    var usuarioN: EditText? = null
    var contraN: EditText? = null
    var edadN: EditText? = null
    var registrar: Button? = null
    var radioGroupSexo: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        usuarioN = findViewById(R.id.usuarioN)
        contraN = findViewById(R.id.contraN)
        edadN = findViewById(R.id.edadN)
        registrar = findViewById(R.id.registrar)
        radioGroupSexo = findViewById(R.id.radioGroupSexo)

        registrar?.setOnClickListener {
            insertar(it)
        }

        val volverText = findViewById<TextView>(R.id.volver)
        volverText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    fun insertar(view: View) {
        val con = SQLite(this, "usuarios", null, 1)
        val baseDatos = con.writableDatabase
        val usuario = usuarioN?.text.toString()
        val contraseña = contraN?.text.toString()
        val edad = edadN?.text.toString()

        // Obtener el sexo seleccionado
        val selectedSexoId = radioGroupSexo?.checkedRadioButtonId
        val sexo = if (selectedSexoId != -1) {
            val radioButtonSexo = findViewById<RadioButton>(selectedSexoId!!)
            radioButtonSexo.text.toString()
        } else {
            ""
        }

        // Validación
        if (usuario.isNotEmpty() && contraseña.isNotEmpty() && edad.isNotEmpty() && sexo.isNotEmpty()) {
            val registro = ContentValues()
            registro.put("usuario", usuario)
            registro.put("contraseña", contraseña)
            registro.put("edad", edad)
            registro.put("sexo", sexo) // Agregar el campo sexo

            baseDatos.insert("usuarios", null, registro)

            // Limpiar los campos después de insertar
            usuarioN?.setText("")
            contraN?.setText("")
            edadN?.setText("")
            radioGroupSexo?.clearCheck() // Limpiar la selección de sexo

            // Mostrar mensaje
            Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()

            // Enviar los datos al MainActivity (o a la actividad que necesites)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("sexo", sexo)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show()
        }
    }
}
