package com.example.check_vi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class configuracionActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuracion2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imginicio = findViewById<ImageView>(R.id.inicio)
        imginicio.setOnClickListener {
            val intent = Intent(this, inicioActivity2::class.java)
            startActivity(intent)
        }
        val imghistorial = findViewById<ImageView>(R.id.historial)
        imghistorial.setOnClickListener {
            val intent = Intent(this, historialActivity2::class.java)
            startActivity(intent)
        }
        val imgconfiguracion = findViewById<ImageView>(R.id.configuracion)
        imgconfiguracion.setOnClickListener {
            val intent = Intent(this, configuracionActivity2::class.java)
            startActivity(intent)
        }
        val perfil = findViewById<Button>(R.id.perfil)
        perfil.setOnClickListener {
            val intent = Intent(this, perfilActivity2::class.java)
            startActivity(intent)
        }
        val historial = findViewById<Button>(R.id.historial2)
        historial.setOnClickListener {
            val intent = Intent(this, historialActivity2::class.java)
            startActivity(intent)
        }
        val sobrenosotros = findViewById<Button>(R.id.sobrenosotros)
        sobrenosotros.setOnClickListener {
            val intent = Intent(this, sobrenosotrosMainActivity2::class.java)
            startActivity(intent)
        }
        val preguntas = findViewById<Button>(R.id.preguntas)
        preguntas.setOnClickListener {
            val intent = Intent(this, preguntasActivity2::class.java)
            startActivity(intent)
        }
        val imgvolver = findViewById<ImageView>(R.id.volver)
        imgvolver.setOnClickListener {
            val intent = Intent(this, inicioActivity2::class.java)
            startActivity(intent)
        }
    }
}