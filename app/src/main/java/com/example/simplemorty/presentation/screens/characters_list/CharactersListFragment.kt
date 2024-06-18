package com.example.simplemorty.presentation.screens.characters_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.simplemorty.databinding.CharacterListBinding
import com.example.simplemorty.presentation.adapter.character_adapter.CharactersAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersListFragment : Fragment() {

    private val binding by viewBinding(CharacterListBinding::bind)
    private val viewModel: CharactersViewModel by viewModel<CharactersViewModel>()
    private val navController by lazy { findNavController() }

    private val adapter by lazy {
        CharactersAdapter(
            onClick = { characterProfile ->
                viewModel.dispatch(
                    IntentScreenCharacters.ClickOnCharacter(characterProfile = characterProfile)
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.layoutManager = LinearLayoutManager(activity)
        binding.rvCharacters.adapter = adapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collect { characterProfileList ->
                    adapter.submitData(characterProfileList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect(::handleEvent)
            }
        }
    }

    private fun handleEvent(event: CharactersScreenEvent) {
        when (event) {
            is CharactersScreenEvent.NavigateToCharacter -> {
                val action = CharactersListFragmentDirections
                    .actionCharactersListFragmentToInfoFragment(event.characterId)
                navController.navigate(action)
            }
        }
    }
}
