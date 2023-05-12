package com.pokemanager.ui.species.detail.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.widget.TextView
import com.pokemanager.R
import com.pokemanager.data.domain.PokeMoveDomain
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.databinding.FragmentDetailPokeSpecieSubThreeBinding
import com.pokemanager.utils.Constants.PREFIX_GMAX
import com.pokemanager.utils.VisualUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPokeSpecieSubThreeFragment : DetailPokeSpecieSub<FragmentDetailPokeSpecieSubThreeBinding>(
    FragmentDetailPokeSpecieSubThreeBinding::inflate
) {

    private var pokeSpecie: PokeSpecieDetailDomain? = null
    private var flowViews = mutableListOf<View>()
    private var shouldLoad = true

    override fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit) {
        this.pokeSpecie = pokeSpecie
        shouldLoad = true
    }

    override fun setEvolutionRows(evolutionToShow: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit) { }

    override fun FragmentDetailPokeSpecieSubThreeBinding.loadView() { }

    override fun onResume() {
        super.onResume()
        if (shouldLoad) {
            pokeSpecie?.let { loadView(it) }
        }
    }

    private fun loadView(pokeSpecie: PokeSpecieDetailDomain) = with(binding) {
        if (pokeSpecie.moves.isEmpty() && pokeSpecie.englishName.endsWith(PREFIX_GMAX)) {
            tvGigantamaxMovesMessage.visibility = VISIBLE
            loadingMovesContainer.visibility = GONE
            return@with
        }
        tvGigantamaxMovesMessage.visibility = GONE
        removeAllItems()
        CoroutineScope(Dispatchers.IO).launch {
            pokeSpecie.moves.forEach { move ->
                fillFlowItem(move)
            }
            CoroutineScope(Dispatchers.Main).launch {
                moveSuccessContainer.visibility = VISIBLE
                loadingMovesContainer.visibility = GONE
                shouldLoad = false
            }
        }
    }

    private fun removeAllItems() = with(binding) {
        if(flowViews.isNotEmpty()) {
            flowViews.forEach {
                movesScroll.removeView(it)
                flowMoves.removeView(it)
            }

            flowViews.clear()
        }
    }

    private fun fillFlowItem(move: PokeMoveDomain) = with(binding) {
        inflateTextMode(move).apply {
            val view = this
            CoroutineScope(Dispatchers.Main).launch {
                movesScroll.addView(view)
                flowMoves.addView(view)
            }
        }
    }

    private fun inflateTextMode(move: PokeMoveDomain): View {
        val layout = R.layout.poke_specie_detail_move_item

        return LayoutInflater.from(context)
            .inflate(layout, binding.movesScroll, false).apply {
                this as TextView
                id = generateViewId()
                text = VisualUtils.replaceBetweenWith(move.name, " ")

                flowViews.add(this)
            }
    }
}