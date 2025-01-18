package com.celisdev.simplesmsapp.data.sms_repositories

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import com.celisdev.simplesmsapp.utils.Constants

class SendSmsRepository(private val context: Context) {

    fun sendSms(
        sender: String,
        messageBody: String,
        onResult: (Boolean) -> Unit
    ) {
        try {
            val smsManager = SmsManager.getDefault()

            val sentIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent("SMS_SENT"),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            smsManager.sendTextMessage(sender, null, messageBody, sentIntent, null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.registerReceiver(object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        when (resultCode) {
                            Activity.RESULT_OK -> onResult(true)
                            else -> onResult(false)
                        }
                        context?.unregisterReceiver(this)
                    }
                }, IntentFilter("SMS_SENT"), Context.RECEIVER_NOT_EXPORTED)
            } else {
                Log.d(Constants.TAG, "Version upper than API 26")
                onResult(true)
            }

        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error sending SMS: ${e.message}")
            onResult(false)
        }
    }
}
