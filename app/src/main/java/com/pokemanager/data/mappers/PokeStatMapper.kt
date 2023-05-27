package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeStatDomain
import com.pokemanager.data.remote.responses.StatNetwork
import com.pokemanager.utils.UrlUtils.getIdAtEndFromUrl

//Object:
//Response -> Domain
fun StatNetwork.toPokeStatDomain() = PokeStatDomain(
    getIdAtEndFromUrl(stat.url),
    stat.name,
    baseStat = baseStat,
    effort = effort
)
//Response -> Entity



//List:
//Response -> Domain
fun MutableList<StatNetwork>.fromResponseListToPokeStatDomainList() =
    map { it.toPokeStatDomain() }.toMutableList()

//Domain -> Entity
//Domain -> String
fun MutableList<PokeStatDomain>.fromDomainListToString(): String {
    val stats = StringBuilder()
    val separator = ": "
    val nextLine = "\n"
    forEach { stats.append(it.name).append(separator).append(it.baseStat).append(nextLine) }
    return stats.removeSuffix(nextLine).toString()
}