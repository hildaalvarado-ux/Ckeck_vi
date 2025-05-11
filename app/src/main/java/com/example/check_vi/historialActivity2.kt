package com.example.check_vi

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class historialActivity2 : AppCompatActivity() {
    private lateinit var dbHelper: SQLite
    private lateinit var db: SQLiteDatabase
    private lateinit var nombreUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nombreUser = findViewById(R.id.nombre_user)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val usuarioActivo = prefs.getString("usuarioActivo", null)

        if (usuarioActivo == null) {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Mostrar directamente el nombre del usuario
        nombreUser.text = usuarioActivo

        // --- Navegación ---
        findViewById<ImageView>(R.id.inicio).setOnClickListener {
            startActivity(Intent(this, inicioActivity2::class.java))
        }
        findViewById<ImageView>(R.id.historial).setOnClickListener {
            startActivity(Intent(this, historialActivity2::class.java))
        }
        findViewById<ImageView>(R.id.configuracion).setOnClickListener {
            startActivity(Intent(this, configuracionActivity2::class.java))
        }
        findViewById<ImageView>(R.id.userButton).setOnClickListener {
            startActivity(Intent(this, perfilActivity2::class.java))
        }
        findViewById<ImageView>(R.id.volver).setOnClickListener {
            startActivity(Intent(this, inicioActivity2::class.java))
        }
    }
}
