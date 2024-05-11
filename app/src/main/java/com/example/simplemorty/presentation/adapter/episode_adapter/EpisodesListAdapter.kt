package com.example.simplemorty.presentation.adapter.episode_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemorty.databinding.EpisodeItemBinding
import com.example.simplemorty.domain.models.Episode

class EpisodesListAdapter(
    private var episodes: List<Episode>,
    private val onClick: (Episode) -> Unit
) : RecyclerView.Adapter<EpisodesListAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EpisodeItemBinding.inflate(inflater, parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int = episodes.size

    inner class EpisodeViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            with(binding) {
                root.setOnClickListener {
                    onClick(episode)
                }
                textViewAirDate.text = episode.airDate
                textViewEpisodeNumber.text = episode.episode
                textViewEpisodeName.text = episode.name
            }
            itemView.setOnClickListener {
                onClick.invoke(episode)
            }
        }
    }

    fun updateEpisodesList(newEpisodes: List<Episode>) {
        episodes = newEpisodes
        notifyDataSetChanged()
    }
}
