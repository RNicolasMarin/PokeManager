package com.pokemanager.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pokemanager.data.domain.PokeStatDomain

class PokeStatConverter {

    @TypeConverter
    fun fromPokeStatDomainList(value: List<PokeStatDomain>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokeStatDomain>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPokeStatDomainList(value: String): List<PokeStatDomain> {
        val gson = Gson()
        val type = object : TypeToken<List<PokeStatDomain>>() {}.type
        return gson.fromJson(value, type)
    }
}