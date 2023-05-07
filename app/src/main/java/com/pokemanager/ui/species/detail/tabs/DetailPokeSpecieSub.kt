package com.pokemanager.ui.species.detail.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.pokemanager.data.domain.PokeSpecieDetailDomain

abstract class DetailPokeSpecieSub<T : ViewBinding>(
    private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding : T? = null
    val binding : T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflateMethod.invoke(inflater, container, false)

        // Calling the extension function
        binding.loadView()

        // replaced _binding!! with binding
        return binding.root
    }

    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Make it open, so it can be overridden in child fragments
    abstract fun T.loadView()

    abstract fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit)

    abstract fun setEvolutionRows(evolutionToShow: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit)
}