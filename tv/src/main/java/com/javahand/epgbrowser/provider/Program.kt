package com.javahand.epgbrowser.provider

data class Program(
    val id: Long,
    val title: String,
    val startTimeUtcMillis: Long,
    val endTimeUtcMillis: Long
)
