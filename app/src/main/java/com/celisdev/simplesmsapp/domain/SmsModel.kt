package com.celisdev.simplesmsapp.domain

import android.annotation.SuppressLint
import com.celisdev.simplesmsapp.data.local_storage.entity.SmsEntity
import java.time.LocalDateTime
import java.time.LocalTime

data class SmsModel(
    val sender: String = "",
    val messageBody: String = "",
    val timestamp: LocalTime? = null,
    val isSentOrReceived: StatusSMS = StatusSMS.SENT
)

fun SmsEntity.toDomain(): SmsModel{
    return SmsModel(
        sender = this.sender,
        messageBody = this.messageBody,
        timestamp = this.timestamp,
        isSentOrReceived = this.isSentOrReceived
    )
}

fun SmsModel.toEntity(): SmsEntity{
    return SmsEntity(
        sender = this.sender,
        messageBody = this.messageBody,
        timestamp = this.timestamp,
        isSentOrReceived = this.isSentOrReceived
    )
}