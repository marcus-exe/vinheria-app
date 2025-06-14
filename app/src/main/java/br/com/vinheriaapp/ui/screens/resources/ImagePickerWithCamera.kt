package br.com.vinheriaapp.ui.screens.resources

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import br.com.vinheriaapp.ui.screens.ProductEvent
import br.com.vinheriaapp.ui.screens.ProductState
import coil.compose.rememberAsyncImagePainter
import java.io.File


@Composable
fun ImagePickerWithCamera(
    imagePath: String? = null,
    onEvent: (ProductEvent) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Create camera image file and URI
    val imageFile = remember {
        File(context.cacheDir, "camera_image.jpg").apply {
            createNewFile()
            deleteOnExit()
        }
    }
    val cameraImageUri = remember {
        FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
    }

    // Gallery picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = it
            onEvent(ProductEvent.SetProductImageSrc(it.toString()))

        }
    }
    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            imageUri = cameraImageUri
            onEvent(ProductEvent.SetProductImageSrc(imageFile.absolutePath))
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Row {

            val displayImagePath = imageUri?.toString() ?: imagePath?.let {
                if (it.startsWith("file://") || it.startsWith("content://")) it
                else "file:///android_asset/$it"
            }

            displayImagePath?.let { path ->
                Image(
                    painter = rememberAsyncImagePainter(path),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.padding(10.dp))
            }

            Column {
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text("Pick from Gallery")
                }

                Spacer(Modifier.width(16.dp))

                Button(onClick = { cameraLauncher.launch(cameraImageUri) }) {
                    Text("Take Photo")
                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
}
