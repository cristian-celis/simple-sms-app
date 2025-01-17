package com.celisdev.simplesmsapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.presentation.SmsViewModel
import com.celisdev.simplesmsapp.presentation.home.components.CustomOutlinedTextField
import com.celisdev.simplesmsapp.presentation.home.components.SearchBar
import com.celisdev.simplesmsapp.presentation.home.components.SendMessageButton
import com.celisdev.simplesmsapp.presentation.home.components.SmsItem
import com.celisdev.simplesmsapp.presentation.sendSmsDialog.SmsDialog
import com.celisdev.simplesmsapp.utils.Constants

@Composable
fun SmsScreen(viewModel: SmsViewModel) {

    val data by viewModel.dataFlow.collectAsState()
    val query by viewModel.query.collectAsState()
    val messagesSearched by viewModel.messagesSearched.collectAsState()
    val searchMode by viewModel.searchMode.collectAsState()
    val showSmsDialog by viewModel.showSmsDialog.collectAsState()

    val focusManager = LocalFocusManager.current

    if(showSmsDialog){
        SmsDialog(viewModel = viewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            },
    ) {
        Text(
            "Messages received",
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SearchBar(
            modifier = Modifier,
            query = query,
            onValueChange = { viewModel.updateQuery(it) },
            onSearch = {
                viewModel.searchMessage()
                viewModel.updateSearchMode(true)
            },
            onCancel = {
                viewModel.updateQuery("")
                viewModel.updateSearchMode(false)
            })

        Spacer(modifier = Modifier.height(10.dp))

        if(searchMode && messagesSearched == null || data.isEmpty()){
            MessageForEmptyList(modifier = Modifier.weight(1f))
        }else{
            val listToShow: List<SmsModel> = if(messagesSearched == null) data else messagesSearched!!
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(listToShow) { sms ->
                    SmsItem(sms)
                    HorizontalDivider()
                }
            }
        }

        SendMessageButton(modifier = Modifier, text = "New Message") {
            viewModel.updateShowSmsDialog(true)
        }
    }
}

@Composable
fun MessageForEmptyList(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            "No messages",
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        )
    }
}