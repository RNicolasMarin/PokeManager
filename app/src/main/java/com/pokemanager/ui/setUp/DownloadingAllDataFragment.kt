package com.pokemanager.ui.setUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pokemanager.R
import com.pokemanager.databinding.FragmentDownloadingAllDataBinding
import com.pokemanager.services.DownloadAllService
import com.pokemanager.utils.Constants.SERVICE_ACTION_START
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadingAllDataFragment : Fragment() {

    private lateinit var binding: FragmentDownloadingAllDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDownloadingAllDataBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        sendCommandToService(SERVICE_ACTION_START)

        DownloadAllService.progress.observe(viewLifecycleOwner) {
            if (it != 0) {
                pbDownloading.max = DownloadAllService.total
                pbDownloading.progress = it
            }
            if (it == DownloadAllService.total) {
                btnContinue.isEnabled = true
            }
        }

        btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_downloadingAllDataFragment_to_listPokeSpeciesFragment)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), DownloadAllService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

}