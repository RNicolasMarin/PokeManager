package com.pokemanager.ui.species

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.pokemanager.databinding.FragmentListPokeSpeciesBinding
import com.pokemanager.services.DownloadAllService
import com.pokemanager.utils.Constants
import com.pokemanager.utils.Constants.SERVICE_ACTION_START
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListPokeSpeciesFragment : Fragment() {

    private val viewModel: ListPokeSpeciesViewModel by viewModels()
    private lateinit var binding: FragmentListPokeSpeciesBinding
    private lateinit var pokeSpecieAdapter: PokeSpecieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListPokeSpeciesBinding.inflate(inflater, container, false)

        binding.mode.text = Constants.MODE::class.simpleName
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            viewModel.pokeSpecies.collectLatest {
                pokeSpecieAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            pokeSpecieAdapter.loadStateFlow.collect {
                binding.prependProgress.isVisible = it.mediator?.prepend is LoadState.Loading
                binding.appendProgress.isVisible = it.mediator?.append is LoadState.Loading
            }
        }

        sendCommandToService(SERVICE_ACTION_START)

        return binding.root
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), DownloadAllService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun setupRecyclerView() = binding.listPokeSpeciesRv.apply {
        pokeSpecieAdapter = PokeSpecieAdapter()
        adapter = pokeSpecieAdapter
        layoutManager = GridLayoutManager(requireContext(), 3)
    }
}