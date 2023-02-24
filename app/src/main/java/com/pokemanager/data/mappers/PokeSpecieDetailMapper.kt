package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity
import com.pokemanager.data.local.entities.PokeSpecieDetailWithTypesAbilitiesMoves
import com.pokemanager.data.remote.responses.*
import com.pokemanager.utils.TextLanguage.*
import com.pokemanager.utils.Utils

//Object:
//Response -> Domain
fun PokemonDetailResponse.toPokeSpecieDetailDomain(pokemonSpecie: PokemonSpecieResponse) = PokeSpecieDetailDomain(
    id = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
    types = types.fromResponseListToPokeTypeDomainList(),
    description = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getEntryByLanguage(ENGLISH, pokemonSpecie) else "",
    weight = weight,
    height = height,
    abilities = abilities.fromResponseListToPokeAbilityDomainList(),
    stats = stats.fromResponseListToPokeStatDomainList(),
    genera = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getGeneraByLanguage(ENGLISH, pokemonSpecie) else "",
    moves = moves.fromResponseListToPokeMoveDomainList(),
)
//Response -> Entity
fun PokemonResponse.toPokeSpecieDetailEntity(pokemonSpecie: PokemonSpecieResponse) = PokeSpecieDetailEntity(
    pokeSpecieId = id,
    englishName = name,
    japHrKtName = Utils.getNameByLanguage(JAP_HR_KT, pokemonSpecie),
    japRoomajiName = Utils.getNameByLanguage(JAP_ROOMAJI, pokemonSpecie),
    imageUrl = sprites.other.officialArtwork.front_default,
    description = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getEntryByLanguage(ENGLISH, pokemonSpecie) else "",
    weight = if (this is PokemonDetailResponse) weight else 0,
    height = if (this is PokemonDetailResponse) height else 0,
    stats = if (this is PokemonDetailResponse) stats.fromResponseListToPokeStatDomainList() else mutableListOf(),
    genera = if (pokemonSpecie is PokemonSpecieDetailResponse) Utils.getGeneraByLanguage(ENGLISH, pokemonSpecie) else ""
)

//Entity -> Domain
fun PokeSpecieDetailEntity.toPokeSpecieDetailDomain() = PokeSpecieDetailDomain(
    id = pokeSpecieId,
    englishName = englishName,
    japHrKtName = japHrKtName,
    japRoomajiName = japRoomajiName,
    imageUrl = imageUrl,
    description = description,
    weight = weight,
    height = height,
    stats = stats,
    genera = genera,
    moves = mutableListOf()
)
//Domain -> Entity

//List:
//Response -> Domain
//Response -> Entity
//Entity -> Domain
//Domain -> Entity

//PokeSpecieDetailWithTypes -> PokeSpecieItemDomain
fun PokeSpecieDetailWithTypesAbilitiesMoves.toPokeSpecieDetailDomain() = pokeSpecie.toPokeSpecieDetailDomain().apply {
    types = pokeTypes.fromEntityListToPokeTypeDomainList()
    abilities = pokeAbilities.fromEntityListToPokeAbilityDomainList()
    moves = pokeMoves.fromEntityListToPokeMoveDomainList()
}
fun MutableList<PokeSpecieDetailWithTypesAbilitiesMoves>.fromPokeSpecieDetailWithTypesListToPokeSpecieDetailDomainList() =
    map { it.toPokeSpecieDetailDomain() }.toMutableList()
