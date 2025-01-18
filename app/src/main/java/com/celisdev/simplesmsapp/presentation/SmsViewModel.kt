package com.celisdev.simplesmsapp.presentation

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celisdev.simplesmsapp.data.sms_repositories.LocalSmsRepository
import com.celisdev.simplesmsapp.data.sms_repositories.SendSmsRepository
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.domain.StatusSMS
import com.celisdev.simplesmsapp.domain.toEntity
import com.celisdev.simplesmsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sendSmsRepository: SendSmsRepository,
    private val localSmsRepository: LocalSmsRepository
) : ViewModel() {

    private val _sms = MutableStateFlow<SmsModel>(SmsModel("", "", null))
    val sms: StateFlow<SmsModel> = _sms

    private val _dataFlow = MutableStateFlow(emptyList<SmsModel>())
    val dataFlow: StateFlow<List<SmsModel>> = _dataFlow

    private val _messagesSearched = MutableStateFlow<List<SmsModel>?>(null)
    val messagesSearched: StateFlow<List<SmsModel>?> = _messagesSearched

    private val _isReceivedMode = MutableStateFlow(true)
    val isReceivedMode: StateFlow<Boolean> = _isReceivedMode

    private val _query = MutableStateFlow<String>("")
    val query: StateFlow<String> = _query

    private val _searchMode = MutableStateFlow(false)
    val searchMode: StateFlow<Boolean> = _searchMode

    private val _showSmsDialog = MutableStateFlow(false)
    val showSmsDialog: StateFlow<Boolean> = _showSmsDialog

    init {
        getAllReceivedSms()
    }

    fun messageReceived(sms: SmsModel) {
        viewModelScope.launch {
            localSmsRepository.insertSms(sms.copy(isSentOrReceived = StatusSMS.RECEIVED).toEntity())
            Toast.makeText(context, "Message received", Toast.LENGTH_SHORT).show()
            if (_isReceivedMode.value)
                getAllReceivedSms()
        }
    }

    fun searchMessage() {
        viewModelScope.launch {
            _messagesSearched.value = localSmsRepository.searchSms(_query.value)
        }
    }

    fun getAllReceivedSms() {
        viewModelScope.launch {
            _dataFlow.value = localSmsRepository.getAllReceivedSms()
        }
    }

    fun getAllSentSms() {
        viewModelScope.launch {
            _dataFlow.value = localSmsRepository.getAllSentSms()
        }
    }

    fun sendMessage() {
        sendSmsRepository.sendSms(
            sender = _sms.value.sender,
            messageBody = _sms.value.messageBody,
            onResult = {
                if (it) {
                    Log.d(Constants.TAG, "Message sent successfully to ${_sms.value.sender}")
                    Toast.makeText(
                        context,
                        "Message sent successfully to ${_sms.value.sender}",
                        Toast.LENGTH_LONG
                    ).show()
                    updateShowSmsDialog(false)
                } else {
                    Log.e(Constants.TAG, "Failed to send message to ${_sms.value.sender}")
                    Toast.makeText(
                        context,
                        "Failed to send message to ${_sms.value.sender}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
        viewModelScope.launch {
            localSmsRepository.insertSms(
                _sms.value.copy(
                    timestamp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) LocalTime.now() else null,
                    isSentOrReceived = StatusSMS.SENT
                ).toEntity()
            )
            getAllSentSms()
            updateIsReceivedMode(false)
        }
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
        if (!showSmsDialog) {
            _sms.value = SmsModel()
        }
    }

    fun updateIsReceivedMode(isReceivedMode: Boolean) {
        _isReceivedMode.value = isReceivedMode
    }
}