package com.example.juegocolor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juegocolor.R


class PuntajeAdapter(private val scores: List<Int>) :
    RecyclerView.Adapter<PuntajeAdapter.ScoreViewHolder>() {


    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referencia directa al TextView dentro del layout del item
        val scoreItemTextView: TextView = itemView.findViewById(R.id.txt_item)
    }
    ///Infla  el archivo XML del item en un objeto View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puntaje, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        //mostramos el ranking
        holder.scoreItemTextView.text = "Puntaje ${position + 1}: ${scores[position]}"

    }
    // Devuelve el número total de items en la lista de datos
    override fun getItemCount(): Int = scores.size
}