package com.pokemanager.ui.species.detail.tabs

import androidx.core.view.isVisible
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.databinding.FragmentDetailPokeSpecieSubOneBinding
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.VisualUtils
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertWeight

class DetailPokeSpecieSubOneFragment : DetailPokeSpecieSub<FragmentDetailPokeSpecieSubOneBinding>(
    FragmentDetailPokeSpecieSubOneBinding::inflate
) {

    private var pokeSpecie: PokeSpecieDetailDomain? = null
    private var isThereMultipleForms = false
    private lateinit var onChangeForm: () -> Unit

    override fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit) {
        this.pokeSpecie = pokeSpecie
        this.isThereMultipleForms = isThereMultipleForms
        this.onChangeForm = onChangeForm
    }

    override fun setEvolutionRows(evolutionToShow: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit) { }

    override fun FragmentDetailPokeSpecieSubOneBinding.loadView() {
        val pokeSpecie = this@DetailPokeSpecieSubOneFragment.pokeSpecie?: return

        with(pokeSpecie) {
            AndroidUtils.loadImage(root, imageUrl, ivImage1)
            tvNumber.text = defaultFormId.toString()
            btnForm.isVisible = isThereMultipleForms

            AndroidUtils.loadTypes(types, tvType1, tvType2)

            val weight = convertWeight(weight)
            tvWeight.text = weight
            val height = convertHeight(height)
            tvHeight.text = height

            tvCategory.text = genera
            tvAbility.text = VisualUtils.convertAbilities(abilities)
            tvAbility.isSelected = true
            tvCategory.isSelected = true

            btnForm.setOnClickListener { onChangeForm.invoke() }
        }
    }
}