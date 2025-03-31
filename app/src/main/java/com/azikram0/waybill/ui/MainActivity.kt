package com.azikram0.waybill.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.azikram0.waybill.presentation.ui.MainScreen
import com.azikram0.waybill.ui.theme.WaybillTheme

class MainActivity : ComponentActivity() {
    private val settings by lazy {
        getSharedPreferences(SH_PREFS, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaybillTheme {
                val perHour = rememberSaveable {
                    mutableStateOf(
                        if (settings.getFloat(FUEL_PER_HOUR, 0f) != 0f) {
                            settings.getFloat(FUEL_PER_HOUR, 0f)
                        } else {
                            null
                        }
                    )
                }
                val per100Km = rememberSaveable {
                    mutableStateOf(
                        if (settings.getFloat(FUEL_PER_100KM, 0f) != 0f) {
                            settings.getFloat(FUEL_PER_100KM, 0f)
                        } else {
                            null
                        }
                    )
                }
                MainScreen(
                    perHour.value,
                    per100Km.value,
                    onSavePrefsClickListener = { perHourPrefs, per100KmPrefs ->
                        val editor = settings.edit()
                        editor.putFloat(FUEL_PER_HOUR, perHourPrefs)
                        editor.putFloat(FUEL_PER_100KM, per100KmPrefs)
                        editor.apply()
                        perHour.value = perHourPrefs
                        per100Km.value = per100KmPrefs
                    }
                )
            }
        }
    }

    companion object {
        private const val SH_PREFS = "sh_prefs"
        private const val FUEL_PER_HOUR = "per_hour"
        private const val FUEL_PER_100KM = "per_100_km"
    }
}