package com.pokemanager.ui.setUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pokemanager.R
import com.pokemanager.databinding.FragmentChooseNameLanguagesBinding
import com.pokemanager.ui.setUp.ChooseNameLanguagesFragment.*
import com.pokemanager.ui.setUp.ChooseNameLanguagesFragment.NameLanguagesToListOptions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChooseNameLanguagesFragment : Fragment() {

    private val viewModel: SetUpViewModel by viewModels()
    private lateinit var binding: FragmentChooseNameLanguagesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentChooseNameLanguagesBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        cbOnlyMyLanguage.setLanguageCheckbox(viewModel, ENGLISH)
        cbJapHrKt.setLanguageCheckbox(viewModel, JAP_HR_KT)
        cbJapRoomaji.setLanguageCheckbox(viewModel, JAP_ROOMAJI)

        btnContinue.setOnClickListener {
            viewModel.continueFromChooseNameLanguages()
            findNavController().navigate(R.id.action_chooseNameLanguagesFragment_to_chooseDataAccessModeFragment)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.canContinue.collectLatest {
                btnContinue.isEnabled = it
            }
        }
    }

    enum class NameLanguagesToListOptions {
        ENGLISH,
        JAP_HR_KT,
        JAP_ROOMAJI
    }

}

fun CheckBox.setLanguageCheckbox(viewModel: SetUpViewModel, language: NameLanguagesToListOptions) {
    setOnCheckedChangeListener { _, b -> viewModel.selectNameLanguage(b, language) }
}