package com.pokemanager.ui.setUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pokemanager.R
import com.pokemanager.data.DataAccessMode
import com.pokemanager.databinding.FragmentChooseHandleDataModeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChooseHandleDataModeFragment : Fragment() {

    private val viewModel: SetUpViewModel by viewModels()
    private lateinit var binding: FragmentChooseHandleDataModeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChooseHandleDataModeBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        rbDownloadAll.setOnClickListener        { selectMode(DataAccessMode.DownloadAll) }
        rbRequestAndDownload.setOnClickListener { selectMode(DataAccessMode.RequestAndDownload) }
        rbOnlyRequest.setOnClickListener        { selectMode(DataAccessMode.OnlyRequest) }

        btnContinue.setOnClickListener {
            viewModel.continueFromChooseHandleDataMode()
            findNavController().navigate(R.id.action_chooseHandleDataModeFragment_to_listPokeSpeciesFragment)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.canContinue.collectLatest {
                btnContinue.isEnabled = it
            }
        }
    }

    private fun selectMode(dataAccessMode: DataAccessMode) = with(viewModel) {
        selectMode(dataAccessMode)
    }

}