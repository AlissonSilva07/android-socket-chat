package br.com.amparocuidado.presentation.ui.camera

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.presentation.components.CameraPermissionHandler
import br.com.amparocuidado.presentation.ui.camera.components.CameraPreview
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.X

@Composable
fun CameraScreen(
    onPhotoCaptured: (Uri) -> Unit,
    onNavigateBack: () -> Unit
) {
    var permissionGranted by remember { mutableStateOf(false) }

    CameraPermissionHandler(
        onPermissionGranted = { permissionGranted = true }
    )

    if (permissionGranted) {
        Scaffold(
            containerColor = Color.Black,
            contentColor = MaterialTheme.colorScheme.surface
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                CameraPreview(onPhotoCaptured)
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                    ,
                    onClick = { onNavigateBack() }
                ) {
                    Icon(
                        imageVector = Lucide.X,
                        contentDescription = "Take Photo",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .size(80.dp)
                        .background(Color.White, shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Lucide.Camera,
                        contentDescription = "Take Photo",
                        tint = Color.Black
                    )
                }
            }
        }
    } else {
        Text(
            text = "Camera permission not granted",
            color = Color.White,
        )
    }
}

@Composable
fun CameraPreviewPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text("Camera Preview Placeholder", color = Color.White)
    }
}

@Preview(
    device = PIXEL_7,
    showSystemUi = true
)
@Composable
fun CameraScreenPreview() {
    AmparoCuidadoTheme {
        CameraScreen(
            onPhotoCaptured = {},
            onNavigateBack = {}
        )
    }
}
