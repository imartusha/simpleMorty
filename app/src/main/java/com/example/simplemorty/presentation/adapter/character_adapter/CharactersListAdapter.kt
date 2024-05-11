package com.example.simplemorty.presentation.adapter.character_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.CharacterItemBinding
import com.example.simplemorty.domain.models.CharacterProfile

class CharactersListAdapter(
    private var characters: List<CharacterProfile>,
    private val onClick: (CharacterProfile) -> Unit
) : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    inner class CharacterViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterProfile) {
            with(binding) {
                root.setOnClickListener { onClick(character) }
                characterName.text = character.name
                species.text = character.species
                status.text = character.status
                character.status?.let { setStatusTextColor(it) }
                Glide.with(root)
                    .load(character.image)
                    .into(imgChar)
            }
        }

        private fun setStatusTextColor(status: String) {
            binding.status.setTextColor(
                when (status) {
                    "Alive" -> Color.parseColor("#00FF00")
                    "Dead" -> Color.parseColor("#F00000")
                    else -> Color.parseColor("#000000")
                }
            )
        }
    }

    fun updateCharactersList(newCharacters: List<CharacterProfile>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
}
