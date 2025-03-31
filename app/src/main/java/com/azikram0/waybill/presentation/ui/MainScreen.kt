package com.azikram0.waybill.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azikram0.waybill.presentation.utils.FuelInputField
import com.azikram0.waybill.ui.theme.Blue
import com.azikram0.waybill.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    perHour: Float? = null,
    per100Km: Float? = null,
    onSavePrefsClickListener: (Float, Float) -> Unit
) {
    val isInputScreen = rememberSaveable { mutableStateOf(true) }
    val kmBefore = rememberSaveable { mutableStateOf("") }
    val kmAfter = rememberSaveable { mutableStateOf("") }
    val fuelLeftBefore = rememberSaveable { mutableStateOf("") }
    val motoHours = rememberSaveable { mutableStateOf("") }
    val refueledFuel = rememberSaveable { mutableStateOf("") }
    val fuelPerMotoHour = rememberSaveable { mutableStateOf(perHour?.toString() ?: "") }
    val fuelPer100Km = rememberSaveable { mutableStateOf(per100Km?.toString() ?: "") }
    val fuelLeftAfter = rememberSaveable { mutableStateOf("") }

    val isPrefsScreen = rememberSaveable { mutableStateOf(false) }
    val perHourPrefs = rememberSaveable { mutableStateOf("") }
    val per100KmPrefs = rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                actions = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onClick = {
                            isPrefsScreen.value = true
                        }
                    ) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Установить расходы")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        if (!isInputScreen.value) {
            BasicAlertDialog(
                onDismissRequest = {
                    isInputScreen.value = true
                }
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    ResultScreen(
                        fuelLeftAfter.value,
                        onBackClickListener = { isInputScreen.value = true }
                    )
                }
            }
        } else if (isPrefsScreen.value) {
            BasicAlertDialog(
                onDismissRequest = {
                    isPrefsScreen.value = false
                }
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Значения по умолчанию",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        FuelInputField(
                            label = "Расход на 1 моточас (л)",
                            value = perHourPrefs.value,
                            onValueChange = { text ->
                                if (text.toFloatOrNull() != null) {
                                    perHourPrefs.value = text
                                }
                                if (text == "" || text[0] == '.' || text[0] == '-' || text[0] == '0') {
                                    perHourPrefs.value = ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        FuelInputField(
                            label = "Расход на 100 км (л)",
                            value = per100KmPrefs.value,
                            onValueChange = { text ->
                                if (text.toFloatOrNull() != null) {
                                    per100KmPrefs.value = text
                                }
                                if (text == "" || text[0] == '.' || text[0] == '-' || text[0] == '0') {
                                    per100KmPrefs.value = ""
                                }
                            }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue,
                                contentColor = White,
                                disabledContainerColor = Blue.copy(alpha = 0.3f),
                                disabledContentColor = White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                onSavePrefsClickListener(
                                    perHourPrefs.value.toFloat(),
                                    per100KmPrefs.value.toFloat()
                                )
                                fuelPerMotoHour.value = perHourPrefs.value
                                fuelPer100Km.value = per100KmPrefs.value
                                isPrefsScreen.value = false
                            },
                            enabled = perHourPrefs.value != "" && per100KmPrefs.value != ""
                                    && perHourPrefs.value.toFloatOrNull() != null
                                    && per100KmPrefs.value.toFloatOrNull() != null,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Сохранить",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue,
                                contentColor = White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                isPrefsScreen.value = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Назад",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
                            )
                        }
                    }
                }
            }
        }

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
    }
}