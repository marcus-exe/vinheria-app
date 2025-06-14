package br.com.vinheriaapp.ui.screens.resources

import android.net.Uri
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File

@Composable
fun ImageFromFileComposable(
    imagePath: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val context = LocalContext.current

    val fullPath = remember(imagePath) {
        when {
            imagePath.startsWith("file://") || imagePath.startsWith("content://") -> imagePath
            else -> "file:///android_asset/$imagePath"
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(fullPath)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(200.dp)
    )
}