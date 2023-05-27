package com.pokemanager.utils

sealed class DataState<out T> {
    data class Success<out T>(val data: T): DataState<T>()
    object Loading: DataState<Nothing>()
    object Error: DataState<Nothing>()
}
