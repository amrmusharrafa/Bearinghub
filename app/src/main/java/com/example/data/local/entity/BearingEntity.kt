package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.Bearing

@Entity(tableName = "bearings")
data class BearingEntity(
    @PrimaryKey
    val number: String,
    val manufacturer: String,
    val bearingType: String = "Deep Groove Ball Bearings",
    val boreMm: Double,
    val outsideMm: Double,
    val widthMm: Double,
    val chamferMm: Double,
    val weightKg: Double,
    val referenceSpeedRpm: Int = 0,
    val limitingSpeedGreaseRpm: Int = 0,
    val limitingSpeedOilRpm: Int = 0,
    val dynamicLoadC: Double = 0.0,
    val staticLoadC0: Double = 0.0,
    val drawingResName: String? = null,
    val customDrawingUri: String? = null
) {
    fun toDomainModel(): Bearing {
        return Bearing(
            number = number,
            manufacturer = manufacturer,
            bearingType = bearingType,
            boreMm = boreMm,
            outsideMm = outsideMm,
            widthMm = widthMm,
            chamferMm = chamferMm,
            weightKg = weightKg,
            referenceSpeedRpm = referenceSpeedRpm,
            limitingSpeedGreaseRpm = limitingSpeedGreaseRpm,
            limitingSpeedOilRpm = limitingSpeedOilRpm,
            dynamicLoadC = dynamicLoadC,
            staticLoadC0 = staticLoadC0,
            drawingResName = drawingResName,
            customDrawingUri = customDrawingUri
        )
    }

    companion object {
        fun fromDomainModel(bearing: Bearing): BearingEntity {
            return BearingEntity(
                number = bearing.number.trim().uppercase(),
                manufacturer = bearing.manufacturer,
                bearingType = bearing.bearingType,
                boreMm = bearing.boreMm,
                outsideMm = bearing.outsideMm,
                widthMm = bearing.widthMm,
                chamferMm = bearing.chamferMm,
                weightKg = bearing.weightKg,
                referenceSpeedRpm = bearing.referenceSpeedRpm,
                limitingSpeedGreaseRpm = bearing.limitingSpeedGreaseRpm,
                limitingSpeedOilRpm = bearing.limitingSpeedOilRpm,
                dynamicLoadC = bearing.dynamicLoadC,
                staticLoadC0 = bearing.staticLoadC0,
                drawingResName = bearing.drawingResName,
                customDrawingUri = bearing.customDrawingUri
            )
        }
    }
}
