package br.com.amparocuidado.domain.model

data class User(
    val id: Int,
    val nm_pessoa_fisica: String,
    val cd_pessoa_fisica: String,
    val nr_cpf: String,
    val id_usuario: Int
)
