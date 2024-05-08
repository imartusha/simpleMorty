package com.example.simplemorty.presentation.screens.character_info

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
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.CharacterInfoBinding
import com.example.simplemorty.presentation.adapter.episode_adapter.EpisodesAdapter
import com.example.simplemorty.presentation.adapter.episode_adapter.EpisodesListAdapter
import com.example.simplemorty.utils.formatDateString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoCharacterFragment : Fragment() {

    private lateinit var fragmentCharacterInfoBinding: CharacterInfoBinding
    private val binding get() = fragmentCharacterInfoBinding

    private val args: InfoCharacterFragmentArgs by navArgs()

    private val viewModel: InfoCharacterViewModel by viewModel<InfoCharacterViewModel>()

    private lateinit var adapter: EpisodesListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCharacterInfoBinding = CharacterInfoBinding.inflate(
            inflater, container, false
        )
        return fragmentCharacterInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(InfoCharacterViewModel.IntentScreenInfoCharacter.GetCharacter(args.id))
        layoutManager = LinearLayoutManager(activity)

        adapter = EpisodesListAdapter(emptyList()) { episode ->
            val action = InfoCharacterFragmentDirections
                .actionInfoFragmentToInfoEpisodeFragment(episode.id!!)
            findNavController().navigate(action)
        }

        val recyclerView = binding.rvEpisodesInCharacters
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._detailStateFlow
                    .collect { characterProfile ->
                        with(binding) {
                            textViewCharacterName.text = characterProfile?.name
                            textViewGender.text = characterProfile?.gender
                            textViewSpecies.text = characterProfile?.species
                            textViewStatus.text = characterProfile?.status
                            textViewType.text = characterProfile?.type
                            Glide.with(requireContext())
                                .load(characterProfile?.image)
                                .into(imageViewCharacter)
                            textViewCreated.text = characterProfile?.created?.formatDateString()
                            textViewHomeland.text = characterProfile?.homeland?.name
                            textViewLocation.text = characterProfile?.location?.name
                        }
                    }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episodesForCharacterInfo.collectLatest { episodes ->
                    adapter.updateEpisodesList(episodes)
                }
            }
        }
    }
}