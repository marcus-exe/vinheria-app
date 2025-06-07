package br.com.vinheriaapp.ui.screens.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.vinheriaapp.ui.screens.Mode
import br.com.vinheriaapp.ui.screens.ProductEvent
import br.com.vinheriaapp.ui.screens.ProductState

@Composable
fun ProductOverflowMenu(
    state: ProductState,
    onEditClickEvent: (ProductEvent) -> Unit,
    onDeleteClickEvent: (ProductEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More Options")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Edit") },
            onClick = {
                expanded = false
                onEditClickEvent(ProductEvent.SetMode(Mode.EDIT))
            }
        )
        DropdownMenuItem(
            text = { Text("Delete") },
            onClick = {
                expanded = false
                state.productId?.let { ProductEvent.DeleteProduct(it) }
                    ?.let { onDeleteClickEvent(it) }
            }
        )
    }
}
