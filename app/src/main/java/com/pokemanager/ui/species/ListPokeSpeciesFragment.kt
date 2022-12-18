package com.pokemanager.ui.species

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.pokemanager.databinding.FragmentListPokeSpeciesBinding
import com.pokemanager.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPokeSpeciesFragment : Fragment() {

    private val viewModel: ListPokeSpeciesViewModel by viewModels()
    private lateinit var binding: FragmentListPokeSpeciesBinding
    private lateinit var pokeSpecieAdapter: PokeSpecieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPokeSpeciesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        viewModel.pokeSpecies.observe(viewLifecycleOwner) {
            if (it is DataState.Success) {
                pokeSpecieAdapter.submitList(it.data)
            }
        }
        return binding.root
    }

    private fun setupRecyclerView() = binding.listPokeSpeciesRv.apply {
        pokeSpecieAdapter = PokeSpecieAdapter()
        adapter = pokeSpecieAdapter
        layoutManager = GridLayoutManager(requireContext(), 3)
    }
}