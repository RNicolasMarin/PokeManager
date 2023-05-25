package com.pokemanager.ui.species.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.databinding.FragmentListPokeSpeciesBinding
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.AndroidUtils.visibleOrGone
import com.pokemanager.utils.NameLanguagesToList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ListPokeSpeciesFragment : Fragment(), PokeSpecieAdapter.PokeSpecieAdapterListener {

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
                val isFirstLoadError = AndroidUtils.isPagingFirstLoadError(it)
                val isLoading = AndroidUtils.isPagingLoading(it)
                val isFirstSuccess = AndroidUtils.isPagingFirstLoadSuccess(it)
                val isPrepending = AndroidUtils.isPagingPrepending(it)
                val isAppending = AndroidUtils.isPagingAppending(it)
                val isAppendError = AndroidUtils.isPagingAppendError(it)
                val isPrependError = AndroidUtils.isPagingPrependError(it)
                with(binding) {
                    errorContainer.visibleOrGone(isFirstLoadError)
                    loadingContainer.visibleOrGone(isLoading)
                    successContainer.visibleOrGone(isFirstSuccess)

                    pbLoadingPrepend.visibleOrGone(isPrepending)
                    errorBtnRetryPrepend.visibleOrGone(isPrependError)
                    pbLoadingAppend.visibleOrGone(isAppending)
                    errorBtnRetryAppend.visibleOrGone(isAppendError)
                }
            }
        }

        binding.errorBtnRetry.setOnClickListener {
            pokeSpecieAdapter.retry()
        }

        binding.errorBtnRetryPrepend.setOnClickListener {
            pokeSpecieAdapter.retry()
        }

        binding.errorBtnRetryAppend.setOnClickListener {
            pokeSpecieAdapter.retry()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mode.observe(viewLifecycleOwner) {
                binding.tvMode.text = it::class.simpleName
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() = binding.rvSpecies.apply {
        val nameLanguagesToList = pokeManagerPreferences.getNameLanguagesToList()
        pokeSpecieAdapter = PokeSpecieAdapter(
            nameLanguagesToList ?: NameLanguagesToList(),
            this@ListPokeSpeciesFragment
        )
        adapter = pokeSpecieAdapter
        layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun onPokeSpecieClicked(id: Int) {
        val action = ListPokeSpeciesFragmentDirections
            .actionListPokeSpeciesFragmentToDetailPokeSpecieFragment(id)
        findNavController().navigate(action)
    }
}