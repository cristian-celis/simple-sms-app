package com.celisdev.simplesmsapp.domain

import java.time.LocalDateTime
import java.time.LocalTime

data class SmsModel(
    val sender: String = "",
    val messageBody: String = "",
    val timestamp: LocalTime? = null
)
