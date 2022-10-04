package com.javahand.epgbrowser.provider

import android.media.tv.TvContract

object ChannelColumn
{
    val PROJECTION = arrayOf(
        // #01
//        TvContract.Channels._COUNT, // 0
        TvContract.Channels._ID,    // 1
        // #21
        TvContract.Channels.COLUMN_DESCRIPTION,     // 2
        TvContract.Channels.COLUMN_DISPLAY_NAME,    // 3
        TvContract.Channels.COLUMN_DISPLAY_NUMBER,  // 4
        TvContract.Channels.COLUMN_INPUT_ID,        // 5
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_DATA,  // 06
        TvContract.Channels.COLUMN_NETWORK_AFFILIATION,     // 07
        TvContract.Channels.COLUMN_ORIGINAL_NETWORK_ID,     // 08
        TvContract.Channels.COLUMN_PACKAGE_NAME,            // 09
        TvContract.Channels.COLUMN_SEARCHABLE,              // 10
        TvContract.Channels.COLUMN_SERVICE_ID,              // 11
        TvContract.Channels.COLUMN_SERVICE_TYPE,            // 12
        TvContract.Channels.COLUMN_TRANSPORT_STREAM_ID,     // 13
        TvContract.Channels.COLUMN_TYPE,                    // 14
        TvContract.Channels.COLUMN_VERSION_NUMBER,          // 15
        TvContract.Channels.COLUMN_VIDEO_FORMAT,            // 16
        // #23
        TvContract.Channels.COLUMN_APP_LINK_COLOR,          // 17
        TvContract.Channels.COLUMN_APP_LINK_ICON_URI,       // 18
        TvContract.Channels.COLUMN_APP_LINK_INTENT_URI,     // 19
        TvContract.Channels.COLUMN_APP_LINK_POSTER_ART_URI, // 20
        TvContract.Channels.COLUMN_APP_LINK_TEXT,           // 21
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG1, // 22
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG2, // 23
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG3, // 24
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG4, // 25
        // #26
        TvContract.Channels.COLUMN_BROWSABLE,               // 26
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_ID,    // 27
        TvContract.Channels.COLUMN_LOCKED,                  // 28
        TvContract.Channels.COLUMN_TRANSIENT               // 29
        // #30
//        TvContract.Channels.COLUMN_GLOBAL_CONTENT_ID
        // #31
//        TvContract.Channels.COLUMN_BROADCAST_GENRE,
//        TvContract.Channels.COLUMN_CHANNEL_LIST_ID,
//        TvContract.Channels.COLUMN_REMOTE_CONTROL_KEY_PRESET_NUMBER,
//        TvContract.Channels.COLUMN_SCRAMBLED,
//        TvContract.Channels.COLUMN_VIDEO_RESOLUTION,
    ) // val PROJECTION

    // #01
//    val COUNT = PROJECTION.indexOf( TvContract.Channels._COUNT )    // 00
    val ID = PROJECTION.indexOf( TvContract.Channels._ID )          // 01
    // #21
    val DESCRIPTION = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_DESCRIPTION )                    // 02
    val DISPLAY_NAME = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_DISPLAY_NAME )                   // 03
    val DISPLAY_NUMBER = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_DISPLAY_NUMBER )                 // 04
    val INPUT_ID = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INPUT_ID )                       // 05
    val INTERNAL_PROVIDER_DATA = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_DATA )         // 06
    val NETWORK_AFFILIATION = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_NETWORK_AFFILIATION )            // 07
    val ORIGINAL_NETWORK_ID = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_ORIGINAL_NETWORK_ID )            // 08
    val PACKAGE_NAME = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_PACKAGE_NAME )                   // 09
    val SEARCHABLE = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_SEARCHABLE )                     // 10
    val SERVICE_ID = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_SERVICE_ID )                     // 11
    val SERVICE_TYPE = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_SERVICE_TYPE )                   // 12
    val TRANSPORT_STREAM_ID = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_TRANSPORT_STREAM_ID )            // 13
    val TYPE = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_TYPE )                           // 14
    val VERSION_NUMBER = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_VERSION_NUMBER )                 // 15
    val VIDEO_FORMAT = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_VIDEO_FORMAT )                   // 16
    // #23
    val APP_LINK_COLOR = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_APP_LINK_COLOR )                 // 17
    val APP_LINK_ICON_URI = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_APP_LINK_ICON_URI )              // 18
    val APP_LINK_INTENT_URI = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_APP_LINK_INTENT_URI )            // 19
    val APP_LINK_POSTER_ART_URI = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_APP_LINK_POSTER_ART_URI )        // 20
    val APP_LINK_TEXT = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_APP_LINK_TEXT )                  // 21
    val INTERNAL_PROVIDER_FLAG1 = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG1 )        // 22
    val INTERNAL_PROVIDER_FLAG2 = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG2 )        // 23
    val INTERNAL_PROVIDER_FLAG3 = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG3 )        // 24
    val INTERNAL_PROVIDER_FLAG4 = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_FLAG4 )        // 25
    // #26
    val BROWSABLE = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_BROWSABLE )                      // 26
    val INTERNAL_PROVIDER_ID = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_INTERNAL_PROVIDER_ID )           // 27
    val LOCKED = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_LOCKED )                         // 28
    val TRANSIENT = PROJECTION.indexOf(
        TvContract.Channels.COLUMN_TRANSIENT )                      // 29
} // object ChannelColumn