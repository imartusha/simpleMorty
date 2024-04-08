package com.example.simplemorty.presentation.screens.locations_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemorty.databinding.LocationsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.example.simplemorty.presentation.adapter.LocationsAdapter


class LocationsFragment : Fragment() {

    private lateinit var fragmentLocationsBinding: LocationsListBinding
    private val binding get() = fragmentLocationsBinding

    private val viewModel: LocationsViewModel by viewModel<LocationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLocationsBinding = LocationsListBinding.inflate(
            inflater, container, false
        )
        return fragmentLocationsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(IntentScreenLocations.GetAllLocations)

        viewModel.locationsListLiveData.observe(viewLifecycleOwner,
            Observer {locationsList ->
                val adapter = LocationsAdapter(locationsList)
                val recyclerView = binding.rvLocations
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = adapter

            })
    }
}