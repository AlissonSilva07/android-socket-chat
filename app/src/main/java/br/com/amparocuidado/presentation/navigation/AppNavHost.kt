package br.com.amparocuidado.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.amparocuidado.presentation.ui.chat.ChatScreen
import br.com.amparocuidado.presentation.ui.chatlist.ChatsScreen
import br.com.amparocuidado.presentation.ui.login.LoginScreen
import br.com.amparocuidado.presentation.ui.login.LoginViewModel
import br.com.amparocuidado.presentation.ui.welcome.WelcomeScreen

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val authToken = loginViewModel.authToken.collectAsState()

    val startDestination = if (authToken.value == null) {
        Screen.Welcome.route
    } else {
        Screen.ChatList.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToChats = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.ChatList.route) {
            ChatsScreen(
                onNavigateToChat = {
                    navController.navigate(Screen.Chat.route)
                }
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen()
        }
    }
}