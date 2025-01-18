package com.celisdev.simplesmsapp.data.sms_repositories

import android.util.Log
import com.celisdev.simplesmsapp.data.local_storage.dao.SmsDao
import com.celisdev.simplesmsapp.data.local_storage.entity.SmsEntity
import com.celisdev.simplesmsapp.domain.SmsModel
import com.celisdev.simplesmsapp.domain.toDomain
import com.celisdev.simplesmsapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalSmsRepository @Inject constructor(
    private val smsDao: SmsDao
) {
    suspend fun getAllSentSms(): List<SmsModel> {
        return try {
            smsDao.getAllSentSms().map {
                it.toDomain()
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "getAllReceivedSms Error: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAllReceivedSms(): List<SmsModel> {
        return try {
            smsDao.getAllReceivedSms().map {
                it.toDomain()
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "getAllReceivedSms Error: ${e.message}")
            emptyList()
        }
    }


    suspend fun searchSms(query: String): List<SmsModel> {
        return try {
            smsDao.searchSms("*$query*").map {
                it.toDomain()
            }
        }catch (e: Exception) {
            Log.e(Constants.TAG, "SearchSms Error: ${e.message}")
            emptyList()
        }
    }

    suspend fun insertSms(sms: SmsEntity) {
        try {
            smsDao.insertSms(sms)
        }catch (e: Exception){
            Log.e(Constants.TAG, "insertSms Error: ${e.message}")
        }
    }
}