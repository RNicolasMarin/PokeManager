package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeSpecieWithTypes
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonSpecieItemResponse
import com.pokemanager.utils.TextLanguage.*
import com.pokemanager.utils.Utils

//Object:
//Response -> Domain
fun PokemonItemResponse.toPokeSpecieItemDomain(pokemonSpecie: PokemonSpecieItemResponse) = PokeSpecieItemDomain(
    id = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
    types = types.fromResponseListToPokeTypeDomainList()
)

//Response -> Entity
fun PokemonItemResponse.toPokeSpecieEntity(pokemonSpecie: PokemonSpecieItemResponse) = PokeSpecieEntity(
    pokeSpecieId = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
)
//Entity -> Domain
fun PokeSpecieEntity.toPokeSpecieItemDomain() = PokeSpecieItemDomain(
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

//PokeSpecieWithTypes -> PokeSpecieItemDomain
fun PokeSpecieWithTypes.toPokeSpecieItemDomain() = pokeSpecie.toPokeSpecieItemDomain().apply {
    types = pokeTypes.fromEntityListToPokeTypeDomainList()
}
fun MutableList<PokeSpecieWithTypes>.fromPokeSpecieWithTypesListToPokeSpecieItemDomainList() =
    map { it.toPokeSpecieItemDomain() }.toMutableList()

