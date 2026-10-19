package com.av.movie.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

fun formatDate(pattern: String, date: String, originPattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(originPattern)
    val requiredSdf = SimpleDateFormat(pattern)

    return try {
        val dateObj: Date? = sdf.parse(date)
        if (dateObj == null) "" else requiredSdf.format(dateObj)
    } catch (e: ParseException) {
        ""
    }
}

fun formatTime(timeInt: Int): String {
    return "${timeInt / 60}h ${timeInt % 60}m"
}

fun parseDateString(dateString: String, pattern: String = "yyyy-MM-dd"): Date? {
    val sdf = SimpleDateFormat(pattern)

    return try {
        sdf.parse(dateString)
    } catch (e: ParseException) {
        null
    }
}
