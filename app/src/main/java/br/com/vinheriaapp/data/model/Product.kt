package br.com.vinheriaapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.UUID

@Entity
data class Product(
    @PrimaryKey val id: UUID,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
    val description: String,
    val imgSrc: String? = null
)