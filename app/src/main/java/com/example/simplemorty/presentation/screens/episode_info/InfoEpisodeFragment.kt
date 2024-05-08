package com.example.simplemorty.presentation.screens.episode_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemorty.databinding.EpisodeInfoBinding
import com.example.simplemorty.presentation.adapter.character_adapter.CharactersListAdapter
import com.example.simplemorty.utils.formatDateString
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoEpisodeFragment : Fragment() {

    private lateinit var fragmentInfoEpisodeBinding: EpisodeInfoBinding
    private val binding get() = fragmentInfoEpisodeBinding

    private val args: InfoEpisodeFragmentArgs by navArgs()
    private lateinit var adapter: CharactersListAdapter
    private val viewModel: InfoEpisodeViewModel by viewModel<InfoEpisodeViewModel>()
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentInfoEpisodeBinding = EpisodeInfoBinding.inflate(
            inflater, container, false
        )
        return fragmentInfoEpisodeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(InfoEpisodeViewModel.IntentScreenInfoEpisode.GetEpisode(args.id))
        layoutManager = LinearLayoutManager(activity)

        adapter = CharactersListAdapter(emptyList()) { characterProfile ->
            val action = InfoEpisodeFragmentDirections
                .actionInfoEpisodeFragmentToInfoFragment(characterProfile.id!!)
            findNavController().navigate(action)
        }

        val recyclerView = binding.rvCharactersInEpisodes
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel._characterFromLocationStateFlow.collect { characterProfileList ->
//                    adapter.submitData(characterProfileList)
//                }
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._episodeByIdStateFlow
                    .collect { episode ->
                        binding.episodesName.text = episode?.name
                        binding.episodeEpisode.text = episode?.episode
                        binding.episodeAirDate.text = episode?.airDate
                        binding.episodeCreated.text = episode?.created?.formatDateString()
                    }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersForEpisode.collect { characters ->
                    adapter.updateCharactersList(characters)
                }
            }
        }

    }
}