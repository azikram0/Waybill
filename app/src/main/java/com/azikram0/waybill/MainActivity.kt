package com.azikram0.waybill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azikram0.waybill.ui.theme.WaybillTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaybillTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val isInputScreen = rememberSaveable { mutableStateOf(true) }
    val kmBefore = rememberSaveable { mutableStateOf("") }
    val kmAfter = rememberSaveable { mutableStateOf("") }
    val fuelLeftBefore = rememberSaveable { mutableStateOf("") }
    val motoHours = rememberSaveable { mutableStateOf("") }
    val refueledFuel = rememberSaveable { mutableStateOf("") }
    val fuelPerMotoHour = rememberSaveable { mutableStateOf("") }
    val fuelPer100Km = rememberSaveable { mutableStateOf("") }
    val fuelLeftAfter = rememberSaveable { mutableStateOf("") }

    Scaffold { paddingValues ->
        AnimatedContent(
            targetState = isInputScreen.value,
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) { state ->
            if (state) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        InputScreen(
                            kmBefore.value, { kmBefore.value = it },
                            kmAfter.value, { kmAfter.value = it },
                            fuelLeftBefore.value, { fuelLeftBefore.value = it },
                            fuelPerMotoHour.value, { fuelPerMotoHour.value = it },
                            fuelPer100Km.value, { fuelPer100Km.value = it },
                            motoHours.value, { motoHours.value = it },
                            refueledFuel.value, { refueledFuel.value = it },
                            onCalculateClickListener = {
                                val kmBeforeVal = kmBefore.value.toDoubleOrNull() ?: 0.0
                                val kmAfterVal = kmAfter.value.toDoubleOrNull() ?: 0.0
                                val fuelLeftBeforeVal = fuelLeftBefore.value.toDoubleOrNull() ?: 0.0
                                val motoHoursVal = motoHours.value.toDoubleOrNull() ?: 0.0
                                val refueledFuelVal = refueledFuel.value.toDoubleOrNull() ?: 0.0
                                val fuelPerMotoHourVal =
                                    fuelPerMotoHour.value.toDoubleOrNull() ?: 0.0
                                val fuelPer100KmVal = fuelPer100Km.value.toDoubleOrNull() ?: 0.0

                                val distanceTraveled = kmAfterVal - kmBeforeVal
                                val fuelUsedForDistance = (distanceTraveled / 100) * fuelPer100KmVal
                                val fuelUsedForMotoHours = fuelPerMotoHourVal * motoHoursVal
                                val totalFuelUsed = fuelUsedForDistance + fuelUsedForMotoHours
                                val fuelLeftAfterVal =
                                    fuelLeftBeforeVal + refueledFuelVal - totalFuelUsed

                                fuelLeftAfter.value =
                                    "Остаток топлива после поездки: %.2f л".format(fuelLeftAfterVal)
                                isInputScreen.value = false
                            }
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ResultScreen(
                            fuelLeftAfter.value,
                            onBackClickListener = { isInputScreen.value = true }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultScreen(fuelLeftAfter: String, onBackClickListener: () -> Unit) {
    Text(fuelLeftAfter, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
    BackHandler { onBackClickListener() }
}

@Composable
private fun InputScreen(
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
        FuelInputField("Начальный километраж (км)", kmBefore, onKmBeforeChange)
        FuelInputField("Конечный километраж (км)", kmAfter, onKmAfterChange)
        FuelInputField("Остаток топлива при выезде (л)", fuelLeftBefore, onFuelLeftBeforeChange)
        FuelInputField("Расход на 1 моточас (л)", fuelPerMotoHour, onFuelPerMotoHourChange)
        FuelInputField("Расход на 100 км (л)", fuelPer100Km, onFuelPer100KmChange)
        FuelInputField("Количество моточасов", motoHours, onMotoHoursChange)
        FuelInputField("Заправлено топлива (л)", refueledFuel, onRefueledFuelChange)
        Button(onClick = onCalculateClickListener, modifier = Modifier.fillMaxWidth()) {
            Text("Рассчитать")
        }
    }
}

@Composable
fun FuelInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}
