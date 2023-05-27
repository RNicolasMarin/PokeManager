package com.pokemanager.ui.setUp

import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
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
import com.pokemanager.utils.Constants
import com.pokemanager.utils.Constants.SERVICE_ACTION_START
import com.pokemanager.utils.VisualUtils.getDownloadPercentage
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
            val percentage = getDownloadPercentage(it, DownloadAllService.total,
                getString(R.string.global_percentage_symbol), getString(R.string.setUp_downloading_tv_progress_done))
            tvProgressDone.text = percentage
            if (it == DownloadAllService.total) {
                btnContinue.isEnabled = true
                val mNotificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                mNotificationManager.cancel(Constants.NOTIFICATION_ID)
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