package com.example.simplemorty.presentation.screens.episodes_list

import android.os.Bundle
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
import com.example.simplemorty.databinding.EpisodesListBinding
import com.example.simplemorty.presentation.adapter.EpisodesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class EpisodesFragment : Fragment() {

    private lateinit var fragmentEpisodesBinding: EpisodesListBinding
    private val binding get() = fragmentEpisodesBinding

    private val viewModel: EpisodesViewModel by viewModel<EpisodesViewModel>()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEpisodesBinding = EpisodesListBinding.inflate(
            inflater, container, false
        )
        return fragmentEpisodesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.dispatch(IntentScreenEpisodes.GetAllEpisodes)

        val adapter = EpisodesAdapter(episodesList) { episode ->
            val action = EpisodesFragmentDirections
                .actionEpisodesFragmentToInfoEpisodeFragment(episode.id)
            findNavController().navigate(action)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodesListStateFlow
                    .collectLatest { pagingData ->
                        adapter.submitData(pagingData)

                        val recyclerView = binding.rvEpisodes
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = adapter
                    }
            }
        }
    }
}