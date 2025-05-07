package com.example.check_vi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ingenieriajhr.blujhr.BluJhr

class inicioActivity2 : AppCompatActivity() {

    private lateinit var btnVincular: Button
    private lateinit var btnDesconectar: Button
    private lateinit var btnComenzar: Button

    private lateinit var blue: BluJhr
    private var dispositivosBluetooth = ArrayList<String>()
    private var isConnected = false  // Nueva variable para rastrear el estado de conexión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita diseño edge-to-edge
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        setContentView(R.layout.activity_inicio2)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Botones de conexión
        btnVincular = findViewById(R.id.Vincular)
        btnDesconectar = findViewById(R.id.desconectar)
        btnComenzar = findViewById(R.id.Comenzar)

        blue = BluJhr(this)
        blue.onBluetooth()

        btnVincular.setOnClickListener {
            blue.initializeBluetooth()
        }

        btnDesconectar.setOnClickListener {
            desconectarBluetooth()
        }

        // Navegación
        findViewById<Button>(R.id.ver).setOnClickListener {
            startActivity(Intent(this, PasosActivity2::class.java))
        }

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
    }

    private fun mostrarDispositivos() {
        dispositivosBluetooth = blue.deviceBluetooth()

        if (dispositivosBluetooth.isEmpty()) {
            Toast.makeText(this, "No hay dispositivos disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona un dispositivo")
        builder.setItems(dispositivosBluetooth.toTypedArray()) { _, which ->
            conectarDispositivo(dispositivosBluetooth[which])
        }
        builder.setCancelable(true)
        builder.show()
    }

    private fun conectarDispositivo(dispositivo: String) {
        blue.connect(dispositivo)
        blue.setDataLoadFinishedListener(object : BluJhr.ConnectedBluetooth {
            override fun onConnectState(state: BluJhr.Connected) {
                when (state) {
                    BluJhr.Connected.True -> {
                        isConnected = true
                        Toast.makeText(applicationContext, "Conexión Exitosa", Toast.LENGTH_SHORT).show()
                    }
                    BluJhr.Connected.False -> {
                        isConnected = false
                        Toast.makeText(applicationContext, "No se pudo conectar", Toast.LENGTH_SHORT).show()
                    }
                    BluJhr.Connected.Disconnect -> {
                        isConnected = false
                        Toast.makeText(applicationContext, "Desconectado", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        })
    }

    private fun desconectarBluetooth() {
        if (isConnected) {
            AlertDialog.Builder(this)
                .setTitle("¿Seguro que deseas desconectarte?")
                .setMessage("Se cerrará la conexión Bluetooth actual.")
                .setPositiveButton("Sí") { _, _ ->
                    blue.closeConnection()
                    isConnected = false
                    Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        } else {
            Toast.makeText(this, "No hay conexión Bluetooth establecida", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (blue.checkPermissions(requestCode, grantResults)) {
            blue.initializeBluetooth()
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                blue.initializeBluetooth()
            } else {
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!blue.stateBluetoooth() && requestCode == 100) {
            blue.initializeBluetooth()
        } else if (requestCode == 100) {
            mostrarDispositivos()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
