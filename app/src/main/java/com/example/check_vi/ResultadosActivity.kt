package com.example.check_vi

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

class ResultadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultados)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imginicio = findViewById<ImageView>(R.id.inicio)
        imginicio.setOnClickListener {
            startActivity(Intent(this, inicioActivity2::class.java))
        }
        val imghistorial = findViewById<ImageView>(R.id.historial)
        imghistorial.setOnClickListener {
            startActivity(Intent(this, historialActivity2::class.java))
        }
        val imgconfiguracion = findViewById<ImageView>(R.id.configuracion)

        imgconfiguracion.setOnClickListener {
            startActivity(Intent(this, configuracionActivity2::class.java))
        }
        val imgperfil = findViewById<ImageView>(R.id.userButton)
        imgperfil.setOnClickListener {
            val intent = Intent(this, perfilActivity2::class.java)
            startActivity(intent)
        }

        val imgvolver = findViewById<ImageView>(R.id.volver)
        imgvolver.setOnClickListener {
            val intent = Intent(this, inicioActivity2::class.java)
            startActivity(intent)
        }
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)

        val adapter = ResultadoPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Resultados"
                1 -> "Recomendaciones"
                else -> ""
            }
        }.attach()

    }
}