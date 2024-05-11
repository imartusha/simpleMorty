package com.example.simplemorty.presentation.screens.location_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemorty.databinding.EpisodeInfoBinding
import com.example.simplemorty.databinding.LocationInfoBinding
import com.example.simplemorty.presentation.adapter.character_adapter.CharactersListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoLocationFragment : Fragment() {

    private lateinit var fragmentInfoLocationBinding: LocationInfoBinding
    private val binding get() = fragmentInfoLocationBinding

    private val args: InfoLocationFragmentArgs by navArgs()

    private val viewModel: InfoLocationViewModel by viewModel<InfoLocationViewModel>()

    private lateinit var adapter: CharactersListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentInfoLocationBinding = LocationInfoBinding.inflate(
            inflater, container, false
        )
        return fragmentInfoLocationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(InfoLocationViewModel.IntentScreenInfoLocation.GetLocation(args.id))
        layoutManager = LinearLayoutManager(activity)

        adapter = CharactersListAdapter(emptyList()) { characterProfile ->
            val action = InfoLocationFragmentDirections
                .actionInfoLocationFragmentToInfoFragment(characterProfile.id!!)
            findNavController().navigate(action)
        }

        val recyclerView = binding.rvCharactersInLocation
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._locationByIdStateFlow
                    .collect { location ->
                        with(binding) {
                            locationName.text = location?.name
                            tvLocationType.text = location?.type
                            tvLocationDimension.text = location?.dimension
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersForLocation.collect { characters ->
                    adapter.updateCharactersList(characters)
                }
            }
        }
    }
}