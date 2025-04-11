package br.com.amparocuidado.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.amparocuidado.presentation.ui.camera.CameraScreen
import br.com.amparocuidado.presentation.ui.chat.ChatScreen
import br.com.amparocuidado.presentation.ui.chat.ChatScreenViewModel
import br.com.amparocuidado.presentation.ui.chatlist.ChatListScreen
import br.com.amparocuidado.presentation.ui.login.LoginScreen
import br.com.amparocuidado.presentation.ui.login.LoginViewModel
import br.com.amparocuidado.presentation.ui.welcome.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel = hiltViewModel(),
    chatViewModel: ChatScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authToken = loginViewModel.authToken.collectAsState()

    val startDestination = if (authToken.value == null) {
        Screen.Welcome.route
    } else {
        Screen.ChatRoutes.Chats
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
                    navController.navigate(Screen.ChatRoutes.Chats) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.ChatRoutes.Chats) {
            ChatListScreen(
                onNavigateToChat = { idChat ->
                    navController.navigate(Screen.ChatRoutes.getChatDetailRoute(idChat))
                }
            )
        }

        composable(
            route = Screen.ChatRoutes.ChatDetail,
            arguments = listOf(navArgument("idChat") { type = NavType.StringType })
        ) {
            val idChat = it.arguments?.getString("idChat")
            ChatScreen(
                idChat = idChat ?: "",
                onNavigateBack = {
                    navController.popBackStack()
                },
                navController = navController,
            )
        }

        composable(Screen.Camera.route) {
            CameraScreen(
                onPhotoCaptured = { uri ->
                    chatViewModel.onImageCaptured(uri)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}