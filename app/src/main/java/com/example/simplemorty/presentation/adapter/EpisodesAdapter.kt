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

class EpisodesAdapter(
    private val episodeList: List<Episode>,
    private val onClick: (Episode) -> Unit
) :
    //RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {
    PagingDataAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(EpisodeDiffUtil) {

    private lateinit var itemEpisodeBinding: ItemEpisodeBinding
    private val binding get() = itemEpisodeBinding

    inner class EpisodeViewHolder(
        binding: ItemEpisodeBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episodeList: Episode) {
            with(binding) {
                textViewAirDate.text = episodeList.airDate
                textViewEpisodeNumber.text = episodeList.episode
                textViewEpisodeName.text = episodeList.name
            }
            itemView.setOnClickListener {
                onClick?.invoke(episodeList)
            }
        }
        val textViewEpisodeName = binding.textViewEpisodeName
        val textViewEpisodeNumber = binding.textViewEpisodeNumber
        val textViewAirDate = binding.textViewAirDate

    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): EpisodeViewHolder {
        Log.e("Marta super", "first commit")
        val inflater = LayoutInflater.from(parent.context)
        itemEpisodeBinding = ItemEpisodeBinding.inflate(inflater, parent, false)
        val view = binding.root
        return EpisodeViewHolder(binding, view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem!!)
//
//        holder.textViewEpisodeName.text = episode.name
//        holder.textViewAirDate.text = episode.airDate
//        holder.textViewEpisodeNumber.text = episode.episode
//
//        holder.itemView.setOnClickListener{
//            onClick(episode)
//        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }


    object EpisodeDiffUtil : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem == newItem
    }
}