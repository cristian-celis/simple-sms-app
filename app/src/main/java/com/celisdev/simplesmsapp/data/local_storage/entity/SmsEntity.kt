package com.celisdev.simplesmsapp.data.local_storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.celisdev.simplesmsapp.domain.StatusSMS
import java.time.LocalTime

@Entity(tableName = "sms")
data class SmsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sender: String = "",
    val messageBody: String = "",
    val timestamp: LocalTime? = null,
    val isSentOrReceived: StatusSMS = StatusSMS.SENT
)

@Entity(tableName = "sms_fts")
@Fts4(contentEntity = SmsEntity::class)
data class SmsFTS(
    val sender: String,
    val messageBody: String
)
