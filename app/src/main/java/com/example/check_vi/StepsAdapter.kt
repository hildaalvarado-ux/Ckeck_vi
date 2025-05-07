package com.example.check_vi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StepsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val steps = listOf(
        Triple("Conexión al dispositivo Bluetooth",
            "Antes de comenzar, asegúrate de conectarte a el dispositivo bluetooth llamado HC06 en el botón vincular.",
            R.drawable.paso1),
        Triple("Iniciar la medición",
            "Una vez establecida la conexión, presiona el botón \"Comenzar\" en la pantalla principal para iniciar el proceso de lectura de signos vitales.",
            R.drawable.paso2),
        Triple("Preparación para la medición",
            "Coloca correctamente el manguito del tensiómetro en tu brazo izquierdo y manténlo en reposo. Acerca tu mano al sensor de temperatura y permanece en una posición cómoda y estable mientras se realiza la medición.",
            R.drawable.paso3),
        Triple("Espera los resultados",
            "La aplicación recopilará automáticamente los datos de presión arterial y temperatura corporal. Espera a que finalice el proceso para ver los resultados en pantalla.",
            R.drawable.paso4),
        Triple("Consulta y recomendaciones",
            "Revisa los valores obtenidos y recibe recomendaciones personalizadas en base a tu estado de salud.",
            R.drawable.paso5),
        Triple("Accede a tu historial",
            "Para llevar un seguimiento de tus mediciones anteriores, dirígete a la sección de historial, donde podrás consultar todos los registros guardados.",
            R.drawable.paso6)
    )

    override fun getItemCount(): Int = steps.size

    override fun createFragment(position: Int): Fragment {
        val (title, description, imageResId) = steps[position]
        return StepFragment.newInstance(title, description, imageResId)
    }
}
