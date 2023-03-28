package com.pokemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import com.pokemanager.R
import com.pokemanager.data.DataAccessMode.*
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pokeManagerPreferences: PokeManagerPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //Setup splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToFragment()
    }

    private fun navigateToFragment() {
        //if there's no languages for name selected
        pokeManagerPreferences.getNameLanguagesToList() ?: return

        //if there's no mode saved it shows the first screen
        val mode = pokeManagerPreferences.getDataAccessMode()
        if (mode == null) {
            navigateTo(R.id.action_global_chooseDataAccessModeFragment)
            return
        }

        //if the mode is requestAndDownload or OnlyRequest navigate to listFragment
        if (mode is RequestAndDownload || mode is OnlyRequest) {
            navigateTo(R.id.action_global_listPokeSpeciesFragment)
            return
        }

        //if it's AllDownload to the downloading
        val downloadAllProgress = pokeManagerPreferences.getDownloadAllProgress()
        if (downloadAllProgress == null || !downloadAllProgress.isFinished) {
            //if it's still downloading to the downloading screen
            navigateTo(R.id.action_global_downloadingAllDataFragment)
        } else {
            //if it's not to the listing
            navigateTo(R.id.action_global_listPokeSpeciesFragment)
        }

    }
}

fun AppCompatActivity.navigateTo(actionId: Int) {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
    navHostFragment?.findNavController()?.navigate(actionId)
}