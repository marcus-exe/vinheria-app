package br.com.vinheriaapp.ui.screens.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarComposable(){
    TopAppBar(
        title = { Text("Agnello App") },
        navigationIcon = {
            IconButton(onClick = { /* handle navigation click */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /* handle action */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    )
}