package com.example.simplemorty.presentation.screens.locations_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemorty.databinding.LocationsListBinding
import com.example.simplemorty.presentation.adapter.location_adapter.LocationsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationsFragment : Fragment() {

    private lateinit var fragmentLocationsBinding: LocationsListBinding
    private val binding get() = fragmentLocationsBinding

    private val viewModel: LocationsViewModel by viewModel<LocationsViewModel>()
    private lateinit var adapter: LocationsAdapter

    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

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
        navController = findNavController()
        layoutManager = LinearLayoutManager(activity)
        Log.d("Location", "создаю адаптер")
        adapter = LocationsAdapter { location ->
            val action = LocationsFragmentDirections
                .actionLocationsFragmentToInfoLocationFragment(location.id)
            findNavController().navigate(action)
        }
        Log.d("Location", "после создания адаптера, создаю рв")

        val recyclerView = binding.rvLocations
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        Log.d("Location", "рв создан ")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationListStateFlow.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
        Log.d("Location", "вызываю IntentScreenLocations.GetAllLocations")
        viewModel.dispatch(IntentScreenLocations.GetAllLocations)
    }
}