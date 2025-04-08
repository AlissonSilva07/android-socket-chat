package br.com.amparocuidado.data.remote.dto.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val id: Int,
    val usuario: String,
    @SerializedName("cd_pessoa_fisica")
    val cdPessoaFisica: String,
    val perfil: List<String>,
    val accessToken: String,
    val refreshToken: String
)