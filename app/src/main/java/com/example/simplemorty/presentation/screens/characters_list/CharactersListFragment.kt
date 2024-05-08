package com.example.simplemorty.presentation.screens.characters_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemorty.databinding.CharacterslistBinding
import com.example.simplemorty.presentation.adapter.character_adapter.CharactersAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersListFragment : Fragment() {

    private lateinit var fragmentCharactersBinding: CharacterslistBinding
    private val binding get() = fragmentCharactersBinding

    private val viewModel: CharactersViewModel by viewModel<CharactersViewModel>()
    private lateinit var adapter: CharactersAdapter
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCharactersBinding = CharacterslistBinding.inflate(
            inflater, container, false
        )
        return fragmentCharactersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        layoutManager = LinearLayoutManager(activity)
        Log.e("MyTag", "до создлания адаптера")
        adapter = CharactersAdapter() { characterProfile ->
            val action = CharactersListFragmentDirections
                .actionCharactersListFragmentToInfoFragment(characterProfile.id!!)
            findNavController().navigate(action)
        }
        Log.e("MyTag", "после создлания адаптера, создаем рв")

        val recyclerView = binding.rvCharacters
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        Log.e("MyTag", "после  рв")

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collect { characterProfileList ->
                    adapter.submitData(characterProfileList)
                    Log.e("MyTag", " adapter.submitData(characterProfileList)")
                    }
            }
        }

        viewModel.dispatch(IntentScreenCharacters.GetAllCharacters)
    }
}
