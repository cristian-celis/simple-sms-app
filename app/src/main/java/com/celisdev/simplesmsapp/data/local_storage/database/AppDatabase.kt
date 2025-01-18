package com.celisdev.simplesmsapp.data.local_storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.celisdev.simplesmsapp.data.local_storage.dao.SmsDao
import com.celisdev.simplesmsapp.data.local_storage.entity.SmsEntity
import com.celisdev.simplesmsapp.data.local_storage.entity.SmsFTS
import com.celisdev.simplesmsapp.utils.Converters

@Database(entities = [SmsEntity::class, SmsFTS::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDao(): SmsDao
}