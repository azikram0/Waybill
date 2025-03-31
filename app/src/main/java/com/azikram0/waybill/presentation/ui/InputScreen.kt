package com.azikram0.waybill.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azikram0.waybill.presentation.utils.FuelInputField
import com.azikram0.waybill.presentation.utils.buttonIsEnabled
import com.azikram0.waybill.ui.theme.Blue
import com.azikram0.waybill.ui.theme.White

@Composable
fun InputScreen(
    kmBefore: String, onKmBeforeChange: (String) -> Unit,
    kmAfter: String, onKmAfterChange: (String) -> Unit,
    fuelLeftBefore: String, onFuelLeftBeforeChange: (String) -> Unit,
    fuelPerMotoHour: String, onFuelPerMotoHourChange: (String) -> Unit,
    fuelPer100Km: String, onFuelPer100KmChange: (String) -> Unit,
    motoHours: String, onMotoHoursChange: (String) -> Unit,
    refueledFuel: String, onRefueledFuelChange: (String) -> Unit,
    onCalculateClickListener: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Расчет остатка топлива",
            fontSize = 28.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(1.dp))
        FuelInputField("Начальный километраж (км)", kmBefore, onKmBeforeChange)
        FuelInputField("Конечный километраж (км)", kmAfter, onKmAfterChange)
        FuelInputField("Остаток топлива перед выездом (л)", fuelLeftBefore, onFuelLeftBeforeChange)
        FuelInputField("Расход на 1 моточас (л)", fuelPerMotoHour, onFuelPerMotoHourChange)
        FuelInputField("Расход на 100 км (л)", fuelPer100Km, onFuelPer100KmChange)
        FuelInputField("Количество моточасов", motoHours, onMotoHoursChange)
        FuelInputField("Заправлено топлива (л)", refueledFuel, onRefueledFuelChange)
        Spacer(modifier = Modifier.padding(2.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = White,
                disabledContentColor = White,
                disabledContainerColor = Blue.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = buttonIsEnabled(
                kmBefore,
                kmAfter,
                fuelLeftBefore,
                fuelPerMotoHour,
                motoHours,
                refueledFuel
            ),
            onClick = onCalculateClickListener,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Рассчитать",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
            )
        }
    }
}