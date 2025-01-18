package com.celisdev.simplesmsapp.presentation.home.components

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.domain.StatusSMS
import com.celisdev.simplesmsapp.ui.theme.SimpleSMSAppTheme
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun SmsItem(sms: SmsModel) {

    var isFullMessageShown by remember { mutableStateOf(sms.messageBody.length < 80) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "AccountRepresentation",
            modifier = Modifier
                .size(50.dp).padding(start = 5.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text("${if(sms.isSentOrReceived == StatusSMS.SENT) "To" else "From"} ${sms.sender}", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500))
            Spacer(modifier = Modifier.height(4.dp))
            val messageBody =
                if (isFullMessageShown) sms.messageBody else sms.messageBody.take(80) + " ..."
            Text(
                messageBody,
                style = TextStyle(color = MaterialTheme.colorScheme.outline, fontSize = 20.sp),
                modifier = Modifier.clickable {
                    if (!isFullMessageShown) isFullMessageShown = true
                    else if (sms.messageBody.length > 80)
                        isFullMessageShown = false
                })
        }
        val timestamp =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && sms.timestamp != null) {
                Duration.between(sms.timestamp, LocalDateTime.now()).toMinutes().toString() + " min"
            } else {
                "No time"
            }
        Text(
            timestamp,
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.W400),
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

@Preview
@Composable
private fun SmsItemPrev() {
    SimpleSMSAppTheme {
        SmsItem(SmsModel("Telefono", "Mensage"))
    }
}