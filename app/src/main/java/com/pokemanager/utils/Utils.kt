package com.pokemanager.utils

object Utils {

    fun getIdAtEndFromUrl(url: String?): String {
        if (url == null) return ""

        var betweenSlashes = url.split("/")
        if (betweenSlashes.last().isBlank()) {
            betweenSlashes = betweenSlashes.subList(0, betweenSlashes.lastIndex)
        }
        return betweenSlashes.last()
    }
}