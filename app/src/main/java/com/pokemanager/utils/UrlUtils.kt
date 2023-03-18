package com.pokemanager.utils

import com.pokemanager.data.remote.responses.SpriteNetwork
import java.lang.NumberFormatException

object UrlUtils {

    fun getIdAtEndFromUrl(url: String): Int {
        var mUrl = url
        val invalidId = 0
        if (mUrl.isBlank()) return invalidId

        if (mUrl.last() == '/') {
            mUrl = mUrl.substring(0, mUrl.lastIndex)
        }

        val slashBeforeIdPosition = mUrl.lastIndexOf('/')
        if (slashBeforeIdPosition == -1 || slashBeforeIdPosition == mUrl.lastIndex) return invalidId

        val possibleId = mUrl.substring(slashBeforeIdPosition + 1, mUrl.length)
        return try {
            possibleId.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun getImageUrl(sprites: SpriteNetwork): String {
        val frontDefaultOther = sprites.other.officialArtwork.frontDefault
        if (frontDefaultOther != null) {
            return frontDefaultOther
        }
        val frontShinyOther = sprites.other.officialArtwork.frontShiny
        if (frontShinyOther != null) {
            return frontShinyOther
        }

        val frontDefault = sprites.frontDefault
        if (frontDefault != null) {
            return frontDefault
        }
        val frontShiny = sprites.frontShiny
        if (frontShiny != null) {
            return frontShiny
        }
        return ""
    }
}