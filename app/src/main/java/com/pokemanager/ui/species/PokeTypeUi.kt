package com.pokemanager.ui.species

import com.pokemanager.R

enum class PokeTypeUi(
    val id: Int,
    val uiNameId: Int,
    val backgroundId: Int,
    val textColorId: Int,
) {
    NORMAL(1, R.string.type_normal, R.drawable.type_background_normal, R.color.black),
    FIGHTING(2, R.string.type_fighting, R.drawable.type_background_fighting, R.color.white),
    FLYING(3, R.string.type_flying, R.drawable.type_background_flying, R.color.black),
    POISON(4, R.string.type_poison, R.drawable.type_background_poison, R.color.white),
    GROUND(5, R.string.type_ground, R.drawable.type_background_ground, R.color.white),
    ROCK(6, R.string.type_rock, R.drawable.type_background_rock, R.color.white),
    BUG(7, R.string.type_bug, R.drawable.type_background_bug, R.color.white),
    GHOST(8, R.string.type_ghost, R.drawable.type_background_ghost, R.color.white),
    STEEL(9, R.string.type_steel, R.drawable.type_background_steel, R.color.black),
    FIRE(10, R.string.type_fire, R.drawable.type_background_fire, R.color.white),
    WATER(11, R.string.type_water, R.drawable.type_background_water, R.color.white),
    GRASS(12, R.string.type_grass, R.drawable.type_background_grass, R.color.black),
    ELECTRIC(13, R.string.type_electric, R.drawable.type_background_electric, R.color.black),
    PSYCHIC(14, R.string.type_psychic, R.drawable.type_background_psychic, R.color.white),
    ICE(15, R.string.type_ice, R.drawable.type_background_ice, R.color.black),
    DRAGON(16, R.string.type_dragon, R.drawable.type_background_dragon, R.color.white),
    DARK(17, R.string.type_dark, R.drawable.type_background_dark, R.color.white),
    FAIRY(18, R.string.type_fairy, R.drawable.type_background_fairy, R.color.black)
}