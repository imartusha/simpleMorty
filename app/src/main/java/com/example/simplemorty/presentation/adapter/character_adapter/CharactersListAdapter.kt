package com.example.simplemorty.presentation.adapter.character_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.ItemCharacterBinding
import com.example.simplemorty.domain.models.CharacterProfile

class CharactersListAdapter(
    private var characters: List<CharacterProfile>,
    private val onClick: (CharacterProfile) -> Unit
) : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterProfile) {
            with(binding) {
                root.setOnClickListener {
                    onClick(character)
                }
                characterName.text = character.name
                species.text = character.species
                status.text = character.status
                when (character.status) {
                    "Alive" -> {
                        binding.status.setTextColor(android.graphics.Color.parseColor("#00FF00"))
                    }
                    "Dead" -> {
                        binding.status.setTextColor(android.graphics.Color.parseColor("#F00000"))
                    }
                    "unknown" -> {
                        binding.status.setTextColor(android.graphics.Color.parseColor("#000000"))
                    }
                }
                Glide.with(binding.root)
                    .load(character.image)
                    .into(binding.imgChar)
            }
        }
    }
    fun updateCharactersList(newCharacters: List<CharacterProfile>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
}

