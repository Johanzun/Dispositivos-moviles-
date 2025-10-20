package com.example.juegocolor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegocolor.adapter.PuntajeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.juegocolor.viewmodel.GameViewModel
import androidx.fragment.app.activityViewModels



class ResultFragment : Fragment(R.layout.fragment_result) {
    //  Declaración de Vistas y Componentes
    private lateinit var txtPuntajeFinal: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnReintentar: FloatingActionButton
    private lateinit var adapter: PuntajeAdapter
    // Se obtiene una instancia del ViewModel compartida a nivel de Actividad
    // Esto permite que este Fragment y GameFragment usen los mismos datos
    private val viewModel: GameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtPuntajeFinal = view.findViewById(R.id.txt_ultimo_puntaje)
        recyclerView = view.findViewById(R.id.puntajes)
        btnReintentar = view.findViewById(R.id.btn_jugar_vuelta)
    //  Se recupera el puntaje final pasado desde el fragmento anterior
        val puntajeFinal = arguments?.getInt("puntajeFinal") ?: 0
        txtPuntajeFinal.text = puntajeFinal.toString()
        //Configuración del RecyclerView para mostrar los puntajes en una lista vertical.
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //// El bloque de código se ejecutará automáticamente cada vez que la lista de puntajes cambie
        //viewLifecycleOwner' asegura que la observación sea segura y se detenga al destruir la vista
        viewModel.scores.observe(viewLifecycleOwner) { lista ->
            val listaOrdenada = lista.sortedDescending() // mayor a menor
            adapter = PuntajeAdapter(listaOrdenada)
            recyclerView.adapter = adapter
        }
        //Navega de regreso al GameFragment usando la acción definida en el nav_graph.
        btnReintentar.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }



    }
}