package br.com.amparocuidado.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object ChatList : Screen("chatlist")
    data object Chat : Screen("chat")
}