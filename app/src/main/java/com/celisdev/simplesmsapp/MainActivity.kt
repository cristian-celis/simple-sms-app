package com.celisdev.simplesmsapp

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.celisdev.simplesmsapp.data.sms_repositories.ReceiveSmsRepository
import com.celisdev.simplesmsapp.presentation.home.SmsScreen
import com.celisdev.simplesmsapp.presentation.SmsViewModel
import com.celisdev.simplesmsapp.ui.theme.SimpleSMSAppTheme
import com.celisdev.simplesmsapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var myReceiver: ReceiveSmsRepository
    private val viewModel: SmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            Log.d(Constants.TAG, "Permisos concedidos")
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1006)
        }

        myReceiver = ReceiveSmsRepository(viewModel)

        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")

        registerReceiver(myReceiver, filter)

        setContent {
            SimpleSMSAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmsScreen(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}