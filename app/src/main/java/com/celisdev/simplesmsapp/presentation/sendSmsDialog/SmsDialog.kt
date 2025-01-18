package com.celisdev.simplesmsapp.presentation.sendSmsDialog

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.celisdev.simplesmsapp.presentation.SmsViewModel
import com.celisdev.simplesmsapp.presentation.home.components.CustomOutlinedTextField
import com.celisdev.simplesmsapp.presentation.home.components.SendMessageButton
import com.celisdev.simplesmsapp.presentation.sendSmsDialog.components.HeaderDialog
import com.celisdev.simplesmsapp.utils.Constants

@Composable
fun SmsDialog(modifier: Modifier = Modifier, viewModel: SmsViewModel) {

    val sms by viewModel.sms.collectAsState()
    val context = LocalContext.current

    Dialog(
        onDismissRequest = {
            viewModel.updateShowSmsDialog(false)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 7.dp
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f),
        ) {
            HeaderDialog {
                viewModel.updateShowSmsDialog(false)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 5.dp)
            ) {
                CustomOutlinedTextField(
                    value = sms.sender, label = "Phone number", keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                ) { newValue ->
                    viewModel.updateSender(newValue.filter { it.isDigit() })
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomOutlinedTextField(
                    value = sms.messageBody,
                    label = "Message",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                ) {
                    viewModel.updateMessageBody(messageBody = it)
                }

                Spacer(modifier = Modifier.height(18.dp))

                SendMessageButton(text = "Send SMS") {
                    if (sms.sender.isNotEmpty() && sms.messageBody.isNotEmpty()) {
                        viewModel.sendMessage()
                    } else {
                        Toast.makeText(context, "Phone number and Message are required", Toast.LENGTH_LONG).show()
                        Log.e(Constants.TAG, "Cant send the message: PhoneNumber o Message are empty")
                    }
                }
            }
        }
    }
}