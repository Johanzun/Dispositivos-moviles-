package com.example.juegocolor

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.juegocolor.viewmodel.GameViewModel
import kotlin.getValue

class GameFragment : Fragment(R.layout.fragment_game) {

    private var currentScore: Int = 0
    private var correctColorName: String = ""
    private var countdownTimer: CountDownTimer? = null

    private lateinit var colorDisplayView: View
    private lateinit var timerTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var optionButtons: List<Button>

    // Animación
    private lateinit var colorChangeAnimation: AnimationSet

    private val viewModel: GameViewModel by activityViewModels()


    // Usamos 'lazy' para que el contexto se obtenga
    private val colorPalette: List<Pair<String, Int>> by lazy {
        listOf(
            "ROJO" to ContextCompat.getColor(requireContext(), R.color.red),
            "AZUL" to ContextCompat.getColor(requireContext(), R.color.blue),
            "VERDE" to ContextCompat.getColor(requireContext(), R.color.green),
            "AMARILLO" to ContextCompat.getColor(requireContext(), R.color.yellow)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inicializa todas las vistas y animaciones
        initializeViews(view)
        initializeAnimations()

        // 2. Configura los listeners para los botones
        setupButtonListeners()

        // 3. Inicia la lógica del juego
        startGame()
    }

    private fun initializeViews(view: View) {

        timerTextView = view.findViewById(R.id.txt_temporizador_cuenta)
        scoreTextView = view.findViewById(R.id.txt_puntaje)
        colorDisplayView = view.findViewById(R.id.cntn_color)

        optionButtons = listOf(
            view.findViewById(R.id.btn_red),
            view.findViewById(R.id.btn_black),
            view.findViewById(R.id.btn_blue),
            view.findViewById(R.id.btn_yellow),

        )
    }

    private fun initializeAnimations() {
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)

        colorChangeAnimation = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(rotate)
        }
    }

    private fun setupButtonListeners() {
        // Mapeamos cada botón a su respectivo nombre de color
        val buttonColorMap = mapOf(
            R.id.btn_red to "ROJO",
            R.id.btn_black to "VERDE",
            R.id.btn_blue to "AZUL",
            R.id.btn_yellow to "AMARILLO",

        )

        optionButtons.forEach { button ->
            val colorName = buttonColorMap[button.id]
            if (colorName != null) {
                button.setOnClickListener { checkAnswer(colorName) }
            }
        }
    }


    private fun startGame() {
        currentScore = 0
        updateScoreDisplay()
        updateRandomColor()

        countdownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun updateRandomColor() {
        val (name, colorValue) = colorPalette.random()
        correctColorName = name
        colorDisplayView.setBackgroundColor(colorValue)

        // aqui se vuelve a ejectuar la animacion cuando el color cambia
        colorDisplayView.startAnimation(colorChangeAnimation)
    }

    private fun checkAnswer(selectedColor: String) {
        if (selectedColor == correctColorName) {
            currentScore++
            updateScoreDisplay()
        }
        // El juego continúa y muestra un nuevo color, haya acertado o no.
        updateRandomColor()
    }


    private fun updateScoreDisplay() {
        scoreTextView.text = currentScore.toString()
    }


    private fun endGame() {
        //cancelamos timer
        countdownTimer?.cancel()
        optionButtons.forEach { it.isEnabled = false }
        Toast.makeText(requireContext(), "Tiempo terminado", Toast.LENGTH_LONG).show()

        viewModel.addScore(currentScore)

        findNavController().navigate(
            R.id.action_gameFragment_to_resultFragment,
            bundleOf("puntajeFinal" to currentScore)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countdownTimer?.cancel()
    }
}