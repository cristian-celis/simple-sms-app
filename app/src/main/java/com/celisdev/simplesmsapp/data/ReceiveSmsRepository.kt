package com.celisdev.simplesmsapp.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.presentation.SmsViewModel
import com.celisdev.simplesmsapp.utils.Constants
import java.time.LocalTime

class ReceiveSmsRepository constructor(private val viewModel: SmsViewModel): BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        for (sms: SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
            Log.d(Constants.TAG, "Sender: ${sms.originatingAddress}")
            Log.d(Constants.TAG, "Message body: ${sms.messageBody}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                viewModel.messageReceived(SmsModel(sms.originatingAddress ?: "NN", sms.messageBody, LocalTime.now()))
            }else{
                viewModel.messageReceived(SmsModel(sms.originatingAddress ?: "NN", sms.messageBody, null))
            }
        }
    }
}
