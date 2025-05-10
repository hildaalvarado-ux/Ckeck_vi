package com.example.check_vi

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var nombre: EditText? = null
    private var contra: EditText? = null
    private var iniciar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Vincular componentes del layout
        nombre = findViewById(R.id.nombre)
        contra = findViewById(R.id.contra)
        iniciar = findViewById(R.id.iniciar)


        iniciar?.setOnClickListener {
            iniciarSesion()
        }

        // Acción para ir a la actividad de registro
        val registroText = findViewById<TextView>(R.id.registro)
        registroText.setOnClickListener {
            val intent = Intent(this, RegistroActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion() {
        val usuarioIngresado = nombre?.text.toString()
        val contraseñaIngresada = contra?.text.toString()

        if (usuarioIngresado.isEmpty() || contraseñaIngresada.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = SQLite(this, "usuarios", null, 1)
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?",
            arrayOf(usuarioIngresado, contraseñaIngresada)
        )

        if (cursor.moveToFirst()) {
            // Guardar usuario en SharedPreferences
            val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
            with(prefs.edit()) {
                putString("usuarioActivo", usuarioIngresado)
                apply()
            }

            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, inicioActivity2::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
        db.close()
    }
}