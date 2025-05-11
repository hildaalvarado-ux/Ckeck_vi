package com.example.check_vi

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class perfilActivity2 : AppCompatActivity() {

    private lateinit var dbHelper: SQLite
    private lateinit var db: SQLiteDatabase
    private lateinit var nombreUser: TextView
    private lateinit var contraActual: TextInputEditText
    private lateinit var nuevaContra: TextInputEditText
    private lateinit var btnCambiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar referencias
        nombreUser = findViewById(R.id.nombre_user)
        contraActual = findViewById(R.id.contraseñaactual)
        nuevaContra = findViewById(R.id.contraN)
        btnCambiar = findViewById(R.id.btncambiar)

        contraActual.isEnabled = false // solo lectura

        // Obtener usuario de SharedPreferences
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val usuarioActivo = prefs.getString("usuarioActivo", null)

        if (usuarioActivo == null) {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        dbHelper = SQLite(this, "usuarios", null, 1)
        db = dbHelper.writableDatabase

        // Obtener datos actuales del usuario
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE usuario = ?",
            arrayOf(usuarioActivo)
        )

        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("usuario"))
            val contraseña = cursor.getString(cursor.getColumnIndexOrThrow("contraseña"))

            nombreUser.text = nombre
            contraActual.setText(contraseña)
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }

        cursor.close()

        // Botón cambiar contraseña
        btnCambiar.setOnClickListener {
            val nueva = nuevaContra.text.toString()

            if (nueva.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce una nueva contraseña", Toast.LENGTH_SHORT).show()
            } else {
                val values = ContentValues()
                values.put("contraseña", nueva)

                val resultado = db.update("usuarios", values, "usuario = ?", arrayOf(usuarioActivo))

                if (resultado > 0) {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                    contraActual.setText(nueva)
                    nuevaContra.setText("")
                } else {
                    Toast.makeText(this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navegación
        findViewById<ImageView>(R.id.inicio).setOnClickListener {
            startActivity(Intent(this, inicioActivity2::class.java))
        }
        findViewById<ImageView>(R.id.historial).setOnClickListener {
            startActivity(Intent(this, historialActivity2::class.java))
        }
        findViewById<ImageView>(R.id.configuracion).setOnClickListener {
            startActivity(Intent(this, configuracionActivity2::class.java))
        }
        findViewById<ImageView>(R.id.volver).setOnClickListener {
            startActivity(Intent(this, inicioActivity2::class.java))
        }
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
