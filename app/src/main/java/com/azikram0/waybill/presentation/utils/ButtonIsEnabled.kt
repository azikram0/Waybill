package com.azikram0.waybill.presentation.utils

fun buttonIsEnabled(
    kmBefore: String,
    kmAfter: String,
    fuelLeftBefore: String,
    fuelPerMotoHour: String,
    motoHours: String,
    refueledFuel: String
): Boolean {
    return kmBefore != "" && kmAfter != "" && fuelLeftBefore != ""
            && fuelPerMotoHour != "" && motoHours != "" && refueledFuel != ""
}