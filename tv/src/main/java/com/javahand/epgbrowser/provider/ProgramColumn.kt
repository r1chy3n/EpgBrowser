package com.javahand.epgbrowser.provider

import android.media.tv.TvContract

object ProgramColumn
{
    const val ID = 0
    const val TITLE = 1
    const val START_TIME_UTC_MILLIS = 2
    const val END_TIME_UTC_MILLIS = 3

    val PROJECTION = arrayOf(
        TvContract.Programs._ID,
        TvContract.Programs.COLUMN_TITLE,
        TvContract.Programs.COLUMN_START_TIME_UTC_MILLIS,
        TvContract.Programs.COLUMN_END_TIME_UTC_MILLIS
    ) // val PROJECTION
}