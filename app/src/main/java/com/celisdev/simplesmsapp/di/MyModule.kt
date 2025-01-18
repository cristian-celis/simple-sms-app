package com.celisdev.simplesmsapp.di

import android.content.Context
import androidx.room.Room
import com.celisdev.simplesmsapp.data.sms_repositories.SendSmsRepository
import com.celisdev.simplesmsapp.data.local_storage.dao.SmsDao
import com.celisdev.simplesmsapp.data.local_storage.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    private const val PRODUCTS_DATABASE_NAME = "sms"

    @Singleton
    @Provides
    fun provideSendSmsRepo(@ApplicationContext context: Context): SendSmsRepository {
        return SendSmsRepository(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, PRODUCTS_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): SmsDao{
        return appDatabase.myDao()
    }
}