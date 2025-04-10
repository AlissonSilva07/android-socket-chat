package br.com.amparocuidado.presentation.ui.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.amparocuidado.R
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.presentation.components.ButtonVariant
import br.com.amparocuidado.presentation.components.CustomButton
import br.com.amparocuidado.presentation.components.CustomInput
import br.com.amparocuidado.presentation.components.TextFieldType
import br.com.amparocuidado.presentation.utils.isKeyboardOpen
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    onNavigateToChats: () -> Unit = {},
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen = isKeyboardOpen()

    val usuario by loginViewModel.usuario.collectAsState()
    val senha by loginViewModel.senha.collectAsState()
    val loginResponse by loginViewModel.loginResponse.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(loginResponse) {
        when (loginResponse) {
            is Resource.Success -> {
                isLoading = false
                onNavigateToChats()
            }

            is Resource.Error -> {
                Toast.makeText(
                    context,
                    (loginResponse as Resource.Error).msg,
                    Toast.LENGTH_LONG
                ).show()
            }

            is Resource.Loading -> {
                isLoading = true
            }

            is Resource.Idle -> {
                isLoading = false
            }

            Resource.Idle -> {}
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.Top,
        ) {
            AnimatedVisibility(
                visible = !isKeyboardOpen,
                enter = slideInVertically(animationSpec = tween(durationMillis = 50)),
                exit = slideOutVertically(animationSpec = tween(durationMillis = 50)),
                modifier = Modifier
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(animationSpec = tween(durationMillis = 50)),
                exit = slideOutVertically(animationSpec = tween(durationMillis = 50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .then(
                            if (isKeyboardOpen) Modifier.weight(1f) else Modifier
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            onClick = {},
                            shape = RoundedCornerShape(percent = 100),
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                disabledContentColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Lucide.ArrowLeft,
                                    contentDescription = "Toggle Password Visibility"
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomInput(
                            label = "Email",
                            value = usuario.toString(),
                            type = TextFieldType.DEFAULT,
                            onValueChange = { newUsuario ->
                                loginViewModel.updateUsuario(newUsuario)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        CustomInput(
                            label = "Senha",
                            value = senha.toString(),
                            type = TextFieldType.PASSWORD,
                            onValueChange = { newSenha ->
                                loginViewModel.updateSenha(newSenha)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomButton(
                            title = "Entrar",
                            variant = ButtonVariant.DEFAULT,
                            disabled = isLoading,
                            onClick = {
                                if (usuario.isNotEmpty() && senha.isNotEmpty()) {
                                    loginViewModel.logIn()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            icon = {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}