package com.celisdev.simplesmsapp.data.local_storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.celisdev.simplesmsapp.data.local_storage.entity.SmsEntity

@Dao
interface SmsDao {

    @Query(
        """
       SELECT * FROM sms WHERE sms.isSentOrReceived = 'SENT'
    """
    )
    suspend fun getAllSentSms(): List<SmsEntity>

    @Query(
        """
       SELECT * FROM sms WHERE sms.isSentOrReceived = 'RECEIVED'
    """
    )
    suspend fun getAllReceivedSms(): List<SmsEntity>

    @Query(
        """
        SELECT * FROM sms
        WHERE id IN (
            SELECT rowid FROM sms_fts
            WHERE sms_fts MATCH :query
        )
    """
    )
    suspend fun searchSms(query: String): List<SmsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSms(sms: SmsEntity)
}