package br.com.vinheriaapp.ui.screens.resources

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.vinheriaapp.ui.screens.Mode
import br.com.vinheriaapp.ui.screens.ProductEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarComposable(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            NavigationIcon(showBackButton = showBackButton, onBackClick)
        },
        actions = { actions() }
    )

}
@Composable
fun NavigationIcon(showBackButton: Boolean, onBackClick: (() -> Unit)?){
    if (showBackButton && onBackClick != null) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
    } else null
}
