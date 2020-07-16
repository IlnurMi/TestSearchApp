package com.ilnur.data.exstention

import com.ilnur.data.network.exceptions.NoConnectivityException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.returnMessage(): String {
    return when(this) {
        is HttpException -> {
            when(this.code()) {
                else -> "Ошибка соединения с сервером:${this.code()}."
            }
        }
        is NoConnectivityException -> {
            "Отсутствует соединение с Интернетом."
        }
        is SocketTimeoutException -> {
            "Превышено время ожидания запроса."
        }
        is UnknownHostException -> {
            "Не удалось подключиться к серверу."
        }
        else -> {
            "Неизвестная ошибка соединения."
        }
    }
}