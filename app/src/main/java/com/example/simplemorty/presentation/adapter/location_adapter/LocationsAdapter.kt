package com.example.simplemorty.presentation.adapter.location_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemorty.databinding.LocationItemBinding
import com.example.simplemorty.domain.models.Location

class LocationsAdapter (
    private val onClick: (Location) -> Unit
) : PagingDataAdapter<Location, RecyclerView.ViewHolder>(LocationDiffUtil) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("Location", "onBindViewHolder")

        val currentItem = getItem(position)

        when (holder) {
            is LocationViewHolder -> {
                holder.bind(currentItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        Log.d("Location", "до onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        Log.d("Location", "после onCreateViewHolder")
        return LocationViewHolder(binding)
    }

    inner class LocationViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(locationList: Location) {
            Log.d("Location", "после LocationViewHolder")

            with(binding) {
                root.setOnClickListener {
                    onClick(locationList)
                }
                textViewLocationName.text = locationList.name
                textViewType.text = locationList.type
                textViewDimension.text = locationList.dimension
            }
            itemView.setOnClickListener {
                onClick.invoke(locationList)
            }
        }
    }

    object LocationDiffUtil : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Location, newItem: Location) =
            oldItem == newItem
    }
}
