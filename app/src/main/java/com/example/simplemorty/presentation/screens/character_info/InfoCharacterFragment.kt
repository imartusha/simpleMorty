package com.example.simplemorty.presentation.screens.character_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.simplemorty.databinding.CharacterInfoBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoCharacterFragment : Fragment() {

    private lateinit var fragmentCharacterInfoBinding: CharacterInfoBinding
    private val binding get() = fragmentCharacterInfoBinding

    private val args: InfoCharacterFragmentArgs by navArgs()

    private val viewModel: InfoCharacterViewModel by viewModel<InfoCharacterViewModel>()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._detailStateFlow
                    .collect { characterProfile ->
                        binding.textViewCharacterName.text = characterProfile?.name
                        binding.textViewGender.text = characterProfile?.gender
                        binding.textViewSpecies.text = characterProfile?.species
                        binding.textViewStatus.text = characterProfile?.status
                        binding.textViewType.text = characterProfile?.type
                        Glide.with(requireContext())
                            .load(characterProfile?.image)
                            .into(binding.imageViewCharacter)
                        binding.textViewCreated.text = characterProfile?.created
                        binding.textViewHomeland.text = characterProfile?.homeland.toString()
                        binding.textViewEpisode.text = characterProfile?.episode.toString()
                        binding.textViewLocation.text = characterProfile?.location.toString()
                    }
            }
        }
    }
}