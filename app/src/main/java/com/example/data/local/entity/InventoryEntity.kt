package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.model.Inventory

@Entity(
    tableName = "inventories",
    foreignKeys = [
        ForeignKey(
            entity = BearingEntity::class,
            parentColumns = ["number"],
            childColumns = ["bearingNumber"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["bearingNumber"])]
)
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bearingNumber: String,
    val condition: String = "New",
    val quantity: Int? = null,
    val sellingPrice: Double? = null,
    val shelfLocation: String = "",
    val currency: String = "EGP"
) {
    fun toDomainModel(): Inventory {
        return Inventory(
            condition = condition,
            quantity = quantity,
            sellingPrice = sellingPrice,
            shelfLocation = shelfLocation,
            currency = currency
        )
    }

    companion object {
        fun fromDomainModel(bearingNumber: String, inventory: Inventory): InventoryEntity {
            return InventoryEntity(
                bearingNumber = bearingNumber.trim().uppercase(),
                condition = inventory.condition,
                quantity = inventory.quantity,
                sellingPrice = inventory.sellingPrice,
                shelfLocation = inventory.shelfLocation,
                currency = inventory.currency
            )
        }
    }
}
