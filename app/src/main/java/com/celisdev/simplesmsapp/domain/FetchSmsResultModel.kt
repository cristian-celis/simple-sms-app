package com.celisdev.simplesmsapp.domain

import java.lang.Exception

sealed class FetchSmsResultModel<out T>{
    data class Success<out T>(val data: T): FetchSmsResultModel<T>()
    data class Error(val exception: Exception): FetchSmsResultModel<Nothing>()
}