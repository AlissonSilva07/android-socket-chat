package br.com.amparocuidado.data.utils

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val msg: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Idle : Resource<Nothing>()
}