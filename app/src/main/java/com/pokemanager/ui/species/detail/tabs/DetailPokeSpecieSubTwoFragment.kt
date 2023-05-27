package com.pokemanager.ui.species.detail.tabs

import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.databinding.FragmentDetailPokeSpecieSubTwoBinding
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.Constants.STAT_ATTACK
import com.pokemanager.utils.Constants.STAT_DEFENSE
import com.pokemanager.utils.Constants.STAT_HP
import com.pokemanager.utils.Constants.STAT_SPEED
import com.pokemanager.utils.Constants.STAT_SP_ATTACK
import com.pokemanager.utils.Constants.STAT_SP_DEFENSE
import com.pokemanager.utils.VisualUtils

class DetailPokeSpecieSubTwoFragment : DetailPokeSpecieSub<FragmentDetailPokeSpecieSubTwoBinding>(
    FragmentDetailPokeSpecieSubTwoBinding::inflate
) {

    private var pokeSpecie: PokeSpecieDetailDomain? = null

    override fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit) {
        this.pokeSpecie = pokeSpecie
    }

    override fun setEvolutionRows(evolutionToShow: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit) { }

    override fun FragmentDetailPokeSpecieSubTwoBinding.loadView() {
        val pokeSpecie = this@DetailPokeSpecieSubTwoFragment.pokeSpecie?: return

        with(pokeSpecie) {
            AndroidUtils.loadImage(root, imageUrl, ivImage2)
            tvDescription.text = description
            tvHp.text = VisualUtils.getStatFromList(stats, STAT_HP)
            tvSpeed.text = VisualUtils.getStatFromList(stats, STAT_SPEED)
            tvAttack.text = VisualUtils.getStatFromList(stats, STAT_ATTACK)
            tvDefense.text = VisualUtils.getStatFromList(stats, STAT_DEFENSE)
            tvSpAttack.text = VisualUtils.getStatFromList(stats, STAT_SP_ATTACK)
            tvSpDefense.text = VisualUtils.getStatFromList(stats, STAT_SP_DEFENSE)
        }
    }

}