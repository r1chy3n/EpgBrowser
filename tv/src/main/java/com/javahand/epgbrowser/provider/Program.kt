package com.javahand.epgbrowser.provider

data class Program(
    val id: Long,
    var title: String,
    val startTimeUtcMillis: Long,
    val endTimeUtcMillis: Long,
    val longDescription: String? = null
)
