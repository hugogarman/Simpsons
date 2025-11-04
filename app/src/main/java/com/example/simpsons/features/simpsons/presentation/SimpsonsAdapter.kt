package com.example.simpsons.features.simpsons.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simpsons.databinding.ItemSimpsonBinding
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsAdapter(
    private var simpsons: List<Simpson> = emptyList(),
    private val onSimpsonClick: (Simpson) -> Unit
) : RecyclerView.Adapter<SimpsonsAdapter.SimpsonViewHolder>() {

    inner class SimpsonViewHolder(private val binding: ItemSimpsonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(simpson: Simpson) {
            binding.tvSimpsonName.text = simpson.name
            binding.tvSimpsonOccupation.text = simpson.occupation

            // Cargar imagen con la URL completa
            binding.ivSimpsonPhoto.load(simpson.portraitPath) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_close_clear_cancel)
            }

            binding.btnChoose.setOnClickListener {
                onSimpsonClick(simpson)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonViewHolder {
        val binding = ItemSimpsonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpsonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpsonViewHolder, position: Int) {
        val simpson = simpsons[position]
        holder.bind(simpson)
    }

    override fun getItemCount(): Int = simpsons.size

    fun updateSimpsons(newSimpsons: List<Simpson>) {
        simpsons = newSimpsons
        notifyDataSetChanged()
    }


}