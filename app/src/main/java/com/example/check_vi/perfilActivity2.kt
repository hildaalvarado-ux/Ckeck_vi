package com.example.check_vi

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class perfilActivity2 : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var nombreUser: TextView
    private lateinit var contraActual: TextInputEditText
    private lateinit var nuevaContra: TextInputEditText
    private lateinit var btnCambiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil2)

        // Inicializar vistas
        nombreUser = findViewById(R.id.nombre_user)
        contraActual = findViewById(R.id.contraactual)
        nuevaContra = findViewById(R.id.editcontra)
        btnCambiar = findViewById(R.id.btncambiar)

        // Obtener usuario en sesión
        val prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val usuario = prefs.getString("usuario_logeado", null)

        val admin = SQLite(this, "administracion", null, 1)
        db = admin.readableDatabase

        if (usuario != null) {
            nombreUser.text = usuario

            val cursor = db.rawQuery("SELECT contraseña FROM usuarios WHERE usuario = ?", arrayOf(usuario))
            if (cursor.moveToFirst()) {
                val pass = cursor.getString(0)
                contraActual.setText(pass)
            }
            cursor.close()
        }

        btnCambiar.setOnClickListener {
            val nueva = nuevaContra.text.toString()
            if (nueva.isNotEmpty() && usuario != null) {
                val values = android.content.ContentValues().apply {
                    put("contraseña", nueva)
                }
                db.update("usuarios", values, "usuario = ?", arrayOf(usuario))
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                contraActual.setText(nueva)
                nuevaContra.text?.clear()
            } else {
                Toast.makeText(this, "Ingrese la nueva contraseña", Toast.LENGTH_SHORT).show()
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
}
