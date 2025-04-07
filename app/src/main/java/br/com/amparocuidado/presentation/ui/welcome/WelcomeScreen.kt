package br.com.amparocuidado.presentation.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.R
import br.com.amparocuidado.presentation.components.ButtonVariant
import br.com.amparocuidado.presentation.components.CustomButton
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit = {}
) {
    val annotatedText = buildAnnotatedString {
        append("Ao continuar, você declara ter lido e concordado com nossos ")

        withStyle(
            style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)
        ) {
            append("Termos de Uso")
        }

        append(" e nossa ")

        withStyle(
            style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)
        ) {
            append("Política de Privacidade")
        }

        append(".")
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
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Boas-vindas ao app Amparo Cuidado.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                CustomButton(
                    title = "Fazer Login",
                    variant = ButtonVariant.DEFAULT,
                    disabled = false,
                    onClick = { onNavigateToLogin() },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "ou",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline)
                    )
                }
                CustomButton(
                    title = "Cadastrar-se",
                    variant = ButtonVariant.MUTED,
                    disabled = false,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    AmparoCuidadoTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        WelcomeScreen()
    }
}