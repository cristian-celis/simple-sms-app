package com.celisdev.simplesmsapp.presentation

import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(): ViewModel() {

    private val _sms = MutableStateFlow<SmsModel>(SmsModel("", "", null))
    val sms: StateFlow<SmsModel> = _sms

    private val _dataFlow = MutableStateFlow<List<SmsModel>>(emptyList<SmsModel>())
    val dataFlow: StateFlow<List<SmsModel>> = _dataFlow

    fun messageReceived(message: SmsModel) {
        _dataFlow.value += message
    }

    fun updateSender(sender: String){
        _sms.update {
            it.copy(sender = sender)
        }
    }

    fun updateMessageBody(messageBody: String){
        _sms.update {
            it.copy(messageBody = messageBody)
        }
    }

    fun sendMessage(){
        if(_sms.value.sender.isNotBlank() && _sms.value.messageBody.isNotBlank()){
            try {
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(_sms.value.sender, null, _sms.value.messageBody, null, null)
                Log.d(Constants.TAG, "message sent to ${_sms.value.sender}")
            } catch (e: Exception) {
                Log.e(Constants.TAG, "error sending the message: ${e.localizedMessage}")
            }
        }
    }
}