package br.com.amparocuidado.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object ChatRoutes {
        const val Chats = "chats"
        const val ChatDetail = "chats/{idChat}"

        fun getChatDetailRoute(idChat: String): String {
            return "chats/$idChat"
        }
    }
    data object Camera : Screen("camera")
}