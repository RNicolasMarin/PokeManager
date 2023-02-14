package com.pokemanager.ui.species

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pokemanager.databinding.FragmentDetailPokeSpecieBinding

class DetailPokeSpecieFragment : Fragment() {

    private lateinit var binding: FragmentDetailPokeSpecieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailPokeSpecieBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() {

    }

}