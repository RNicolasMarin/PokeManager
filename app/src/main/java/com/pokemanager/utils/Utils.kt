package com.pokemanager.utils

import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import kotlin.math.ceil

object Utils {

    fun getTotalStepsAtDownloadingAll(): Int {
        val result : Double = LAST_VALID_POKEMON_NUMBER.toDouble() / POKEMON_PAGING_PAGE_SIZE.toDouble()
        return ceil(result).toInt()
    }

}