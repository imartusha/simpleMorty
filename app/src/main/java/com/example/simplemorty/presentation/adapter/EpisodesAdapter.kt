package com.example.simplemorty.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemorty.databinding.ItemEpisodeBinding
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.presentation.adapter.character_adapter.CharactersAdapter

class EpisodesAdapter(
    private val onClick: (Episode) -> Unit
) : PagingDataAdapter<Episode, RecyclerView.ViewHolder>(EpisodeDiffUtil) {

//    private lateinit var itemEpisodeBinding: ItemEpisodeBinding
//    private val binding get() = itemEpisodeBinding
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is EpisodesAdapter.EpisodeViewHolder -> {
                holder.bind(currentItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
        return EpisodeViewHolder(binding)
    }
    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding, )
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(episodeList: Episode) {
            with(binding) {
                root.setOnClickListener {
                    onClick(episodeList)
                }
                textViewAirDate.text = episodeList.airDate
                textViewEpisodeNumber.text = episodeList.episode
                textViewEpisodeName.text = episodeList.name
            }
            itemView.setOnClickListener {
                onClick?.invoke(episodeList)
            }
        }
    }

    object EpisodeDiffUtil : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem == newItem
    }
}