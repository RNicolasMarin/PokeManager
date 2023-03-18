package com.pokemanager.utils

import com.pokemanager.data.base_models.PokeSpecieBase
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import kotlin.math.max

object KeyUtils {

    fun getNextKey(items: MutableList<PokeSpecieBase>, lastValidId: Int = Constants.LAST_VALID_POKEMON_NUMBER): Int? {
        val last = items.lastOrNull() ?: return null

        val id = last.getModelId()
        return if (id >= lastValidId) {
            null
        } else {
            id
        }
    }

    fun getPrevKey(offset: Int, loadSize: Int): Int? {
        return when (offset) {
            POKEMON_PAGING_STARTING_KEY -> null
            else -> ensureValidKey(key = offset - loadSize)
        }
    }

    private fun ensureValidKey(key: Int) = max(POKEMON_PAGING_STARTING_KEY, key)
}