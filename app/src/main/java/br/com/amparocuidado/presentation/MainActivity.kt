package br.com.amparocuidado.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import br.com.amparocuidado.presentation.navigation.AppNavigation
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import br.com.amparocuidado.presentation.ui.theme.white
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = white.toArgb(), darkScrim = white.toArgb()),
            navigationBarStyle = SystemBarStyle.light(scrim = white.toArgb(), darkScrim = white.toArgb())
        )
        setContent {
            AmparoCuidadoTheme(darkTheme = false, dynamicColor = false) {
                AppNavigation()
            }
        }
    }
}