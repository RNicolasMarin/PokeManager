package com.pokemanager.ui.species.detail.tabs

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pokemanager.data.domain.PokeSpecieDetailDomain

class DetailPokeSpecieViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = mutableListOf(
        DetailPokeSpecieSubOneFragment(),
        DetailPokeSpecieSubTwoFragment(),
        DetailPokeSpecieSubThreeFragment(),
        DetailPokeSpecieSubFourFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun setPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, isThereMultipleForms: Boolean, onChangeForm: () -> Unit) {
        for (fragment in fragments) {
            fragment.setPokeSpecie(pokeSpecie, isThereMultipleForms, onChangeForm)
        }
    }

    fun setEvolutionRows(dataState: EvolutionToShow, onSpecieClicked: (id: Int) -> Unit) {
        for (fragment in fragments) {
            if (fragment is DetailPokeSpecieSubFourFragment) {
                fragment.setEvolutionRows(dataState, onSpecieClicked)
            }
        }
    }
}