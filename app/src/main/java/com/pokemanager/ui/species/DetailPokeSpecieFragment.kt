package com.pokemanager.ui.species

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.pokemanager.R
import com.pokemanager.data.domain.PokeMoveDomain
import com.pokemanager.data.mappers.fromDomainListToString
import com.pokemanager.databinding.FragmentDetailPokeSpecieBinding
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.AndroidUtils.setUpTextViewForNameDetail
import com.pokemanager.utils.Constants.STAT_ATTACK
import com.pokemanager.utils.Constants.STAT_DEFENSE
import com.pokemanager.utils.Constants.STAT_HP
import com.pokemanager.utils.Constants.STAT_SPEED
import com.pokemanager.utils.Constants.STAT_SP_ATTACK
import com.pokemanager.utils.Constants.STAT_SP_DEFENSE
import com.pokemanager.utils.DataState.*
import com.pokemanager.utils.VisualUtils
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertWeight
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

    private fun setUpViews() = with(binding) {
        viewModel.loadPokeSpecieData(args.pokeSpecieId)

        lifecycleScope.launchWhenCreated {
            viewModel.pokeSpecieDetail.collectLatest {
                loadingContainer.isVisible = it is Loading
                successContainer.isVisible = it !is Loading

                if (it is Success) {
                    val pokeSpecie = it.data
                    //show in each tab
                    loadNames()

                    //show in tab 1
                    AndroidUtils.loadImage(root, pokeSpecie.imageUrl, ivImage1)
                    tvNumber.text = pokeSpecie.defaultFormId.toString()
                    btnForm.isVisible = viewModel.isThereMultipleForms()

                    AndroidUtils.loadTypes(pokeSpecie.types, tvType1, tvType2)

                    val weight = convertWeight(pokeSpecie.weight)
                    tvWeight.text = weight
                    val height = convertHeight(pokeSpecie.height)
                    tvHeight.text = height

                    tvCategory.text = pokeSpecie.genera
                    tvAbility.text = VisualUtils.convertAbilities(pokeSpecie.abilities)
                    tvAbility.isSelected = true
                    tvCategory.isSelected = true

                    //show in tab 2
                    /*AndroidUtils.loadImage(root, pokeSpecie.imageUrl, ivImage2)
                    tvDescription.text = pokeSpecie.description
                    tvHp.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_HP)
                    tvSpeed.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_SPEED)
                    tvAttack.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_ATTACK)
                    tvDefense.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_DEFENSE)
                    tvSpAttack.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_SP_ATTACK)
                    tvSpDefense.text = VisualUtils.getStatFromList(pokeSpecie.stats, STAT_SP_DEFENSE)*/

                    //show in tab 3

                    /*
                    pokeSpecieDetailMoves.text = pokeSpecieDetail.moves.fromDomainListToString()
                    pokeSpecieDetailEvolutionCHain.text = pokeSpecieDetail.evolutionChain.toString()*/
                }
            }
        }

        btnPrevious.setOnClickListener { viewModel.loadPreviousPokeSpecieData() }
        btnNext.setOnClickListener { viewModel.loadNextPokeSpecieData() }
        btnForm.setOnClickListener { viewModel.changeForm() }
    }

    private fun loadNames() = with(binding) {
        val namesToShow = viewModel.getNamesByLanguage()
        with(namesToShow) {
            tvName1.isSelected = true
            tvName1.setUpTextViewForNameDetail(name1)
            tvName2.setUpTextViewForNameDetail(name2)
            tvName3.setUpTextViewForNameDetail(name3)
        }
    }

}