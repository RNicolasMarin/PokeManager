package com.pokemanager.ui.species.detail.tabs

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.databinding.FragmentDetailPokeSpecieSubFourBinding
import com.pokemanager.utils.AndroidUtils.visibleOrGone

class DetailPokeSpecieSubFourFragment : DetailPokeSpecieSub<FragmentDetailPokeSpecieSubFourBinding>(
    FragmentDetailPokeSpecieSubFourBinding::inflate
) {

    private var evolutionToShow: EvolutionToShow? = null
    private lateinit var onSpecieClicked: (id: Int) -> Unit

    override fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit) {
        evolutionToShow = null
    }

    override fun setEvolutionRows(evolutionToShow: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit) {
        this.evolutionToShow = evolutionToShow
        this.onSpecieClicked = onSpecieClicked
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            binding.loadView()
        }
    }

    override fun FragmentDetailPokeSpecieSubFourBinding.loadView() {
        val evolution: EvolutionToShow = evolutionToShow?: return
        val evolutionRows = evolution.evolutionRows

        loadingEvolutionContainer.visibleOrGone(false)
        tvOnlyOneEvolutionLabel.visibleOrGone(evolutionRows.size == 1)
        val adapter = EvolutionChainAdapter(evolutionRows, onSpecieClicked)
        with(rvRows) {
            visibleOrGone(true)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}