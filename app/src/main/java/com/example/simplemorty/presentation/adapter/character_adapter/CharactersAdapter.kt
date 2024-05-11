package com.example.simplemorty.presentation.adapter.character_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.CharacterItemBinding
import com.example.simplemorty.domain.models.CharacterProfile

class CharactersAdapter(
    private val onClick: (CharacterProfile) -> Unit
) : PagingDataAdapter<CharacterProfile, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = getItem(position)

        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

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
