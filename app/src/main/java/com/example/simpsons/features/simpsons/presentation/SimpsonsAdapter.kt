package com.example.simpsons.features.simpsons.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simpsons.databinding.ItemSimpsonBinding
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsAdapter(
    private val onSimpsonSelected: (Simpson) -> Unit
) : RecyclerView.Adapter<SimpsonsAdapter.SimpsonViewHolder>() {

    private val simpsons = mutableListOf<Simpson>()

    fun submitList(newSimpsons: List<Simpson>) {
        simpsons.clear()
        simpsons.addAll(newSimpsons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonViewHolder {
        val binding = ItemSimpsonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SimpsonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpsonViewHolder, position: Int) {
        holder.bind(simpsons[position])
    }

    override fun getItemCount(): Int = simpsons.size

    inner class SimpsonViewHolder(
        private val binding: ItemSimpsonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(simpson: Simpson) {
            binding.ivSimpsonPhoto.load(simpson.portraitPath) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }

            binding.tvSimpsonName.text = simpson.name
            binding.tvSimpsonOccupation.text = simpson.occupation

            binding.btnChoose.setOnClickListener {
                onSimpsonSelected(simpson)
            }
        }
    }
}