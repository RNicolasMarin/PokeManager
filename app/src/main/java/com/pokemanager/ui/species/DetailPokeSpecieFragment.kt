package com.pokemanager.ui.species

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.pokemanager.R
import com.pokemanager.data.mappers.fromDomainListToString
import com.pokemanager.databinding.FragmentDetailPokeSpecieBinding
import com.pokemanager.utils.DataState.*
import com.pokemanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailPokeSpecieFragment : Fragment() {

    private val viewModel: DetailPokeSpecieViewModel by viewModels()
    private val args: DetailPokeSpecieFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPokeSpecieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailPokeSpecieBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() {
        viewModel.loadPokeSpecieData(args.pokeSpecieId)

        lifecycleScope.launchWhenCreated {
            viewModel.pokeSpecieDetail.collectLatest {
                with(binding) {
                    pokeSpecieDetailPB.isVisible = it is Loading

                    if (it is Success) {
                        val pokeSpecieDetail = it.data
                        pokeSpecieDetailNumber.text = pokeSpecieDetail.id.toString()
                        pokeSpecieDetailEnglishName.text = pokeSpecieDetail.englishName
                        pokeSpecieDetailJapHrKtName.text = pokeSpecieDetail.japHrKtName
                        pokeSpecieDetailJapRoomajiName.text = pokeSpecieDetail.japRoomajiName
                        pokeSpecieDetailGenera.text = pokeSpecieDetail.genera
                        pokeSpecieDetailDescription.text = pokeSpecieDetail.description

                        val weight = getString(R.string.weight) + Utils.convertWeight(pokeSpecieDetail.weight)
                        pokeSpecieDetailWeight.text = weight
                        val height = getString(R.string.height) + Utils.convertHeight(pokeSpecieDetail.height)
                        pokeSpecieDetailHeight.text = height

                        pokeSpecieDetailTypes.text = pokeSpecieDetail.types.fromDomainListToString()
                        pokeSpecieDetailAbilities.text = pokeSpecieDetail.abilities.fromDomainListToString()
                        pokeSpecieDetailStats.text = pokeSpecieDetail.stats.fromDomainListToString()
                        pokeSpecieDetailMoves.text = pokeSpecieDetail.moves.fromDomainListToString()

                        Glide.with(root).load(pokeSpecieDetail.imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
                            .into(pokeSpecieDetailImage)
                    }
                }
            }
        }
    }

}