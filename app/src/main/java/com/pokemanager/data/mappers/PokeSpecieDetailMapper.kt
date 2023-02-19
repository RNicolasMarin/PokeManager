package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity
import com.pokemanager.data.local.entities.PokeSpecieDetailWithTypes
import com.pokemanager.data.remote.responses.PokemonSpecieResponse
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonSpecieDetailResponse
import com.pokemanager.utils.TextLanguage.*
import com.pokemanager.utils.Utils

//Object:
//Response -> Domain
fun PokemonItemResponse.toPokeSpecieDetailDomain(pokemonSpecie: PokemonSpecieResponse) = PokeSpecieDetailDomain(
    id = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
    types = types.fromResponseListToPokeTypeDomainList(),
    description = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getEntryByLanguage(ENGLISH, pokemonSpecie) else "",
)
//Response -> Entity
fun PokemonItemResponse.toPokeSpecieDetailEntity(pokemonSpecie: PokemonSpecieResponse) = PokeSpecieDetailEntity(
    pokeSpecieId = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
    description = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getEntryByLanguage(ENGLISH, pokemonSpecie) else "",
)

//Entity -> Domain
fun PokeSpecieDetailEntity.toPokeSpecieDetailDomain() = PokeSpecieDetailDomain(
    id = pokeSpecieId,
    englishName = englishName,
    japHrKtName = japHrKtName,
    japRoomajiName = japRoomajiName,
    imageUrl = imageUrl,
    description = description
)
//Domain -> Entity

//List:
//Response -> Domain
//Response -> Entity
//Entity -> Domain
//Domain -> Entity

//PokeSpecieDetailWithTypes -> PokeSpecieItemDomain
fun PokeSpecieDetailWithTypes.toPokeSpecieDetailDomain() = pokeSpecie.toPokeSpecieDetailDomain().apply {
    types = pokeTypes.fromEntityListToPokeTypeDomainList()
}
fun MutableList<PokeSpecieDetailWithTypes>.fromPokeSpecieDetailWithTypesListToPokeSpecieDetailDomainList() =
    map { it.toPokeSpecieDetailDomain() }.toMutableList()
