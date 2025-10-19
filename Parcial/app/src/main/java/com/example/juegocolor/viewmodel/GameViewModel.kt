package com.example.juegocolor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel


class GameViewModel(application: Application) : AndroidViewModel(application) {


    val scores = MutableLiveData<MutableList<Int>>(mutableListOf())

    private val prefs = application.getSharedPreferences("HistorialPuntajes", Context.MODE_PRIVATE)

    init {
        // Carga los puntajes guardados al iniciar el ViewModel
        val savedScores = prefs.getString("lista", "")?.split(",")?.mapNotNull {
            it.toIntOrNull()
        } ?: emptyList()
        scores.value = savedScores.toMutableList()
    }

    fun addScore(score: Int) {
        val currentScores = scores.value ?: mutableListOf()
        currentScores.add(score)

        prefs.edit().putString("lista", currentScores.joinToString(",")).apply()

        // Notificar cambio en la lista
        scores.value = currentScores
    }

}