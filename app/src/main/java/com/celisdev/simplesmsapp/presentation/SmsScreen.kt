package com.celisdev.simplesmsapp.presentation

import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.utils.Constants
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun SmsScreen(viewModel: SmsViewModel) {

    val sms by viewModel.sms.collectAsState()
    val data by viewModel.dataFlow.collectAsState()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
    ) {

        Text("Messages", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold))

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = sms.sender,
            onValueChange = { newValue ->
                viewModel.updateSender(newValue.filter { it.isDigit() })
            },
            label = { Text("Phone Number", style = TextStyle(fontSize = 16.sp)) },
            textStyle = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = sms.messageBody,
            onValueChange = {
                viewModel.updateMessageBody(messageBody = it)
            },
            label = { Text("Message", style = TextStyle(fontSize = 16.sp)) },
            textStyle = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (sms.sender.isNotEmpty() && sms.messageBody.isNotEmpty()) {
                    viewModel.sendMessage()
                } else {
                    Log.e(Constants.TAG, "Cant send the message: PhoneNumber o Message are empty")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.surface
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = "Enviar SMS",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Mensajes recibidos",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(10.dp))

        if(data.isEmpty()){
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    "No messages",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W400,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                )
            }
        }else{
            LazyColumn {
                items(data) { sms ->
                    SmsItem(sms)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun SmsItem(sms: SmsModel) {
    var isFullMessageShown by remember { mutableStateOf(sms.messageBody.length < 100) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "AccountRepresentation",
            modifier = Modifier
                .size(45.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(sms.sender, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500))
            Spacer(modifier = Modifier.height(4.dp))
            val messageBody =
                if (isFullMessageShown) sms.messageBody else sms.messageBody.take(100) + "..."
            Text(
                messageBody,
                style = TextStyle(color = MaterialTheme.colorScheme.outline, fontSize = 20.sp),
                modifier = Modifier.clickable {
                    if (!isFullMessageShown) isFullMessageShown = true
                    else if(sms.messageBody.length > 100)
                        isFullMessageShown = false
                })
        }
        val timestamp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Duration.between(sms.timestamp, LocalDateTime.now()).toMinutes().toString() + " min"
        } else {
            "No time"
        }
        Text(timestamp, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.W400))
    }
}