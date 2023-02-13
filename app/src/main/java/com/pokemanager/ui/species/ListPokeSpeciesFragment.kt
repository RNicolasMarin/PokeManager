package com.pokemanager.ui.species

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
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.databinding.FragmentListPokeSpeciesBinding
import com.pokemanager.utils.NameLanguagesToList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ListPokeSpeciesFragment : Fragment() {

    private val viewModel: ListPokeSpeciesViewModel by viewModels()
    private lateinit var binding: FragmentListPokeSpeciesBinding
    private lateinit var pokeSpecieAdapter: PokeSpecieAdapter

    @Inject
    lateinit var pokeManagerPreferences: PokeManagerPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListPokeSpeciesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            viewModel.pokeSpecies.collectLatest {
                pokeSpecieAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            pokeSpecieAdapter.loadStateFlow.collect {
                with(binding) {
                    val isAppend = it.mediator?.append is LoadState.Loading// || it.append is LoadState.Loading
                    prependProgress.isVisible = it.mediator?.prepend is LoadState.Loading
                    appendProgress.isVisible = isAppend
                    viewModel.tryToUpdateShowFirstLoading(isAppend, it.source.refresh is LoadState.Loading)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.showFirstLoading.observe(viewLifecycleOwner) { showFirstLoading ->
                with(binding) {
                    firstLoadPB.isVisible = showFirstLoading
                    listPokeSpeciesRv.isVisible = !showFirstLoading
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mode.observe(viewLifecycleOwner) {
                binding.mode.text = it::class.simpleName
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() = binding.listPokeSpeciesRv.apply {
        val nameLanguagesToList = pokeManagerPreferences.getNameLanguagesToList()
        pokeSpecieAdapter = PokeSpecieAdapter(nameLanguagesToList ?: NameLanguagesToList())
        adapter = pokeSpecieAdapter
        layoutManager = GridLayoutManager(requireContext(), 3)
    }
}