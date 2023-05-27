package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity
import com.pokemanager.data.local.entities.PokeSpecieItemEntity
import com.pokemanager.data.local.entities.PokeSpecieItemWithTypes
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonSpecieItemResponse
import com.pokemanager.utils.ResponseUtils.getNameByLanguage
import com.pokemanager.utils.TextLanguage.*
import com.pokemanager.utils.UrlUtils.getImageUrl

//Object:
//Response -> Domain
fun PokemonItemResponse.toPokeSpecieItemDomain(pokemonSpecie: PokemonSpecieItemResponse) = PokeSpecieItemDomain(
    id = id,
    englishName = name,
    japHrKtName = getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = getImageUrl(sprites),
    types = types.fromResponseListToPokeTypeDomainList()
)

//Response -> Entity
fun PokemonItemResponse.toPokeSpecieDetailEntity(pokemonSpecie: PokemonSpecieItemResponse) = PokeSpecieDetailEntity(
    pokeSpecieId = id,
    englishName = name,
    japHrKtName = getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = getImageUrl(sprites),
    weight = 0,
    height = 0,
    stats = mutableListOf(),
    genera = "",
    evolutionChainId = 0,
    defaultFormId = 0
)

//Entity -> Domain
fun PokeSpecieItemEntity.toPokeSpecieItemDomain() = PokeSpecieItemDomain(
    id = pokeSpecieId,
    englishName = englishName,
    japHrKtName = japHrKtName,
    japRoomajiName = japRoomajiName,
    imageUrl = imageUrl
)
//Domain -> Entity

//List:
//Response -> Domain
//Response -> Entity
//Entity -> Domain
//Domain -> Entity

//PokeSpecieItemWithTypes -> PokeSpecieItemDomain
fun PokeSpecieItemWithTypes.toPokeSpecieItemDomain() = pokeSpecie.toPokeSpecieItemDomain().apply {
    pokeTypeCross.sortedBy { it.slot }
    types = pokeTypeCross.map { PokeTypeDomain(it.pokeTypeId) }.toMutableList()
}
fun MutableList<PokeSpecieItemWithTypes>.fromPokeSpecieItemWithTypesListToPokeSpecieItemDomainList() =
    map { it.toPokeSpecieItemDomain() }.toMutableList()

