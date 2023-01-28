package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.utils.Utils.getIdAtEndFromUrl

fun PokemonItemResponse.toPokeSpecieItemDomain(): PokeSpecieItemDomain {
    return PokeSpecieItemDomain(
        this.id,
        this.name,
        this.sprites.other.officialArtwork.front_default,
        this.types.map {
            PokeTypeDomain(
                it.type.name,
                getIdAtEndFromUrl(it.type.url)
            )
        } as MutableList<PokeTypeDomain>
    )
}

fun PokemonItemResponse.toPokeSpecieEntity(): PokeSpecieEntity {
    return PokeSpecieEntity(
        this.id,
        this.name,
        this.sprites.other.officialArtwork.front_default,
        /*this.types?.map {
            PokeTypeDomain(
                it.type?.name,
                getIdAtEndFromUrl(it.type?.url)
            )
        } as MutableList<PokeTypeDomain>?*/
    )
}

//entity to domain
fun PokeSpecieEntity.toPokeSpecieItemDomain(): PokeSpecieItemDomain {
    return PokeSpecieItemDomain(
        this.pokeSpecieId,
        this.name,
        imageUrl
    )
}