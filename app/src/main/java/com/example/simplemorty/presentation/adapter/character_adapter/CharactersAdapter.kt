package com.example.simplemorty.presentation.adapter.character_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.ItemCharacterBinding
import com.example.simplemorty.domain.models.CharacterProfile
import androidx.compose.ui.graphics.Color


class CharactersAdapter(
    private val onClick: (CharacterProfile) -> Unit
) : PagingDataAdapter<CharacterProfile, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterProfile) {
            binding.root.setOnClickListener {
                onClick(character)
            }
            binding.characterName.text = character.name
            binding.species.text = character.species
            binding.status.text = character.status
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

    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterProfile>() {
        override fun areItemsTheSame(
            oldItem: CharacterProfile,
            newItem: CharacterProfile
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterProfile,
            newItem: CharacterProfile
        ): Boolean {
            return oldItem == newItem
        }
    }
}