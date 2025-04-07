package br.com.amparocuidado.data.remote.dto

data class LoginRequest(
    val usuario: String,
    val senha: String
)