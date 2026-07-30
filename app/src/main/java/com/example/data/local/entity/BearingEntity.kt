package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.Bearing

@Entity(tableName = "bearings")
data class BearingEntity(
    @PrimaryKey
    val number: String,
    val manufacturer: String,
    val boreMm: Double,
    val outsideMm: Double,
    val widthMm: Double,
    val chamferMm: Double,
    val weightKg: Double
) {
    fun toDomainModel(): Bearing {
        return Bearing(
            number = number,
            manufacturer = manufacturer,
            boreMm = boreMm,
            outsideMm = outsideMm,
            widthMm = widthMm,
            chamferMm = chamferMm,
            weightKg = weightKg
        )
    }

    companion object {
        fun fromDomainModel(bearing: Bearing): BearingEntity {
            return BearingEntity(
                number = bearing.number.trim().uppercase(),
                manufacturer = bearing.manufacturer,
                boreMm = bearing.boreMm,
                outsideMm = bearing.outsideMm,
                widthMm = bearing.widthMm,
                chamferMm = bearing.chamferMm,
                weightKg = bearing.weightKg
            )
        }
    }
}
