package com.example.juegocolor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Button

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el botón para iniciar la partida.
        // Al hacer clic, navega hacia el GameFragment.
        val startGameButton = view.findViewById<Button>(R.id.btm_inciar_jeugo)
        startGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
        }

        // Configura el botón para mostrar las reglas.
        // Al hacer clic, despliega un diálogo de alerta.
        val showRulesButton = view.findViewById<Button>(R.id.btn_mostrarReglas)
        showRulesButton.setOnClickListener {
            displayRulesDialog()
        }
    }


      //Crea y muestra un AlertDialog con las reglas del juego.

    private fun displayRulesDialog() {
        val rulesTitle = "Reglas del juego"
        val rulesMessage = """
            Encuentra todas las parejas antes de que se acabe el tiempo.
            Cada acierto suma puntos.
            Se genera un historial de mayor puntaje a menos
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle(rulesTitle)
            .setMessage(rulesMessage)
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}