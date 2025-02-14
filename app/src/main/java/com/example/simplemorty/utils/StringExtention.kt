package com.example.simplemorty.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.formatDateString(): String {
    // Парсинг строки в объект Date
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDate: Date = inputFormat.parse(this) ?: Date()

    // Форматирование даты в нужный вам формат
    val outputFormat = SimpleDateFormat("dd MMMM yyyy г., HH:mm:ss", Locale.getDefault())
    return outputFormat.format(parsedDate)
}

