package com.example.simplemorty.presentation.adapter.episode_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemorty.databinding.EpisodeItemBinding
import com.example.simplemorty.domain.models.Episode

class EpisodesAdapter(
    private val onClick: (Episode) -> Unit
) : PagingDataAdapter<Episode, RecyclerView.ViewHolder>(EpisodeDiffUtil) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is EpisodeViewHolder -> {
                holder.bind(currentItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EpisodeItemBinding.inflate(inflater, parent, false)
        return EpisodeViewHolder(binding)
    }

    inner class EpisodeViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                onClick.invoke(episodeList)
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