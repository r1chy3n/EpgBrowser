package com.javahand.epgbrowser.provider

import android.media.tv.TvContract

object ChannelColumn
{
    const val ID = 0
    const val DISPLAY_NAME = 1
    const val DISPLAY_NUMBER = 2

    val PROJECTION = arrayOf(
        TvContract.Channels._ID,
        TvContract.Channels.COLUMN_DISPLAY_NAME,
        TvContract.Channels.COLUMN_DISPLAY_NUMBER
    ) // val PROJECTION
} // object ChannelColumn