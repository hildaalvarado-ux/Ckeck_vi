package com.example.check_vi

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ingenieriajhr.blujhr.BluJhr

class inicioActivity2 : AppCompatActivity() {

    private lateinit var blue: BluJhr
    private var devicesBluetooth = ArrayList<String>()

    private val bluetoothPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            blue.initializeBluetooth()
            mostrarDispositivosBluetooth()
        } else {
            Toast.makeText(this, "Permisos de Bluetooth denegados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        blue = BluJhr(this)
        blue.onBluetooth()

        val btnVincular = findViewById<Button>(R.id.Vincular)
        btnVincular.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                bluetoothPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                    )
                )
            } else {
                blue.initializeBluetooth()
                mostrarDispositivosBluetooth()
            }
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

    private fun mostrarDispositivosBluetooth() {
        devicesBluetooth = blue.deviceBluetooth()

        if (devicesBluetooth.isNotEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona un dispositivo")

            val devicesArray = devicesBluetooth.toTypedArray()
            builder.setItems(devicesArray) { _, which ->
                val selected = devicesBluetooth[which]
                val parts = selected.split("\n")
                if (parts.size == 2) {
                    val macAddress = parts[1]
                    conectarDispositivo(macAddress)
                } else {
                    Toast.makeText(this, "Formato de dispositivo no válido", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()
        } else {
            Toast.makeText(this, "No hay dispositivos vinculados", Toast.LENGTH_SHORT).show()
        }
    }

    private fun conectarDispositivo(macAddress: String) {
        blue.connect(macAddress)

        blue.setDataLoadFinishedListener(object : BluJhr.ConnectedBluetooth {
            override fun onConnectState(state: BluJhr.Connected) {
                when (state) {
                    BluJhr.Connected.Pending -> {
                        Toast.makeText(applicationContext, "Conexión pendiente...", Toast.LENGTH_SHORT).show()
                    }
                    BluJhr.Connected.True -> {
                        Toast.makeText(applicationContext, "¡Conectado!", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            val enviado = blue.bluTx("Hola\n")
                            if (!enviado) {
                                Toast.makeText(applicationContext, "No se pudo enviar el mensaje", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                            }
                        }, 1000) // Esperar 1 segundo después de conectarse
                    }

                    BluJhr.Connected.False -> {
                        Toast.makeText(applicationContext, "No se pudo conectar", Toast.LENGTH_SHORT).show()
                    }
                    BluJhr.Connected.Disconnect -> {
                        Toast.makeText(applicationContext, "Desconectado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!blue.stateBluetoooth() && requestCode == 100) {
            blue.initializeBluetooth()
        } else if (requestCode == 100) {
            devicesBluetooth = blue.deviceBluetooth()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
