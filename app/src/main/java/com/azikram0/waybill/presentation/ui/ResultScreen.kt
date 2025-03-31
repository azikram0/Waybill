package com.azikram0.waybill.presentation.ui

import androidx.activity.compose.BackHandler
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
import com.azikram0.waybill.ui.theme.Blue
import com.azikram0.waybill.ui.theme.White

@Composable
fun ResultScreen(fuelLeftAfter: String, onBackClickListener: () -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = fuelLeftAfter,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = White
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = onBackClickListener,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Назад",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
            )
        }
    }
    BackHandler { onBackClickListener() }
}