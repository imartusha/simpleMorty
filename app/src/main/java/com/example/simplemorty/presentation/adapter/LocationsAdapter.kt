package com.example.simplemorty.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemorty.databinding.ItemLocationsBinding
import com.example.simplemorty.domain.models.Location

class LocationsAdapter (
    private val locationList: List<Location>
) : RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>() {

    private lateinit var itemLocationsBinding: ItemLocationsBinding
    private val binding get() = itemLocationsBinding

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        itemLocationsBinding = ItemLocationsBinding.inflate(inflater, parent, false)
        val view = binding.root
        return LocationViewHolder(binding, view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

        val locations = locationList[position]

        holder.textViewLocationName.text = locations.name
        holder.textViewType.text = locations.type
        holder.textViewDimension.text = locations.dimension

//        Glide.with(holder.itemView.context)
//            .load(episodes.url)
//            .into(holder.imgChar)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    class LocationViewHolder(
        binding: ItemLocationsBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val textViewLocationName = binding.textViewLocationName
        val textViewType = binding.textViewType
        val textViewDimension = binding.textViewDimension

    }
}