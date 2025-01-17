package com.celisdev.simplesmsapp.presentation

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celisdev.simplesmsapp.data.SendSmsRepository
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sendSmsRepository: SendSmsRepository
) : ViewModel() {

    private val _sms = MutableStateFlow<SmsModel>(SmsModel("", "", null))
    val sms: StateFlow<SmsModel> = _sms

    private val _dataFlow = MutableStateFlow(emptyList<SmsModel>())
    val dataFlow: StateFlow<List<SmsModel>> = _dataFlow

    private val _messagesSearched = MutableStateFlow<List<SmsModel>?>(null)
    val messagesSearched: StateFlow<List<SmsModel>?> = _messagesSearched

    private val _query = MutableStateFlow<String>("")
    val query: StateFlow<String> = _query

    private val _searchMode = MutableStateFlow(false)
    val searchMode: StateFlow<Boolean> = _searchMode

    private val _showSmsDialog = MutableStateFlow(false)
    val showSmsDialog: StateFlow<Boolean> = _showSmsDialog

    fun messageReceived(message: SmsModel) {
        _dataFlow.value += message
    }

    fun searchMessage() {
        val regex = Regex(_query.value, RegexOption.IGNORE_CASE)
        _messagesSearched.value = _dataFlow.value.filter { item ->
            regex.containsMatchIn(item.sender) || regex.containsMatchIn(item.messageBody)
        }
    }

    fun sendMessage() {
        Log.d(Constants.TAG, "Message to be sent")
            sendSmsRepository.sendSms(
                sender = _sms.value.sender,
                messageBody = _sms.value.messageBody,
                onResult = {
                    if (it) {
                        Log.d(Constants.TAG, "Message sent successfully to ${_sms.value.sender}")
                        Toast.makeText(context, "Message sent successfully to ${_sms.value.sender}", Toast.LENGTH_LONG).show()
                        updateShowSmsDialog(false)
                    } else {
                        Log.e(Constants.TAG, "Failed to send message to ${_sms.value.sender}")
                        Toast.makeText(context, "Failed to send message to ${_sms.value.sender}", Toast.LENGTH_LONG).show()
                    }
                }
            )
    }

    fun updateQuery(query: String) {
        _query.value = query
        if (query.isBlank()) {
            _messagesSearched.value = null
        }
    }

    fun updateSender(sender: String) {
        _sms.update {
            it.copy(sender = sender)
        }
    }

    fun updateMessageBody(messageBody: String) {
        _sms.update {
            it.copy(messageBody = messageBody)
        }
    }

    fun updateSearchMode(searchMode: Boolean) {
        _searchMode.value = searchMode
    }

    fun updateShowSmsDialog(showSmsDialog: Boolean) {
        _showSmsDialog.value = showSmsDialog
        if(!showSmsDialog){
            _sms.value = SmsModel()
        }
    }
}