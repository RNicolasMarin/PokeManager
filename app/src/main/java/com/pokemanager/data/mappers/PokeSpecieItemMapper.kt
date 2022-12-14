package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.utils.Utils.getIdAtEndFromUrl

fun PokemonItemResponse.toPokeSpecieItemDomain(): PokeSpecieItemDomain {
    return PokeSpecieItemDomain(
        this.id,
        this.name,
        this.sprites?.other?.officialArtwork?.front_default,
        this.types?.map {
            PokeTypeDomain(
                it.type?.name,
                getIdAtEndFromUrl(it.type?.url)
            )
        } as MutableList<PokeTypeDomain>?
    )
}