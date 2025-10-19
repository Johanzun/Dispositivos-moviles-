package com.example.juegocolor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.juegocolor.viewmodel.GameViewModel
import kotlin.getValue


class GameFragment : Fragment(R.layout.fragment_game) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Encuentra la vista a la que quieres aplicar la animación
        val view = view.findViewById<View>(R.id.cntn_color)
        //carga las animaciones usamos require contexto por que es el contexto del fragment
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)

        // Combinar ambas animaciones
        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(rotate)
        }
            //inicia la animacion en la vista
        view.startAnimation(animationSet)



    }

}