package com.javahand.epgbrowser.provider

data class Channel(
    val id: Long,
    val displayName: String,
    val displayNumber: String
) : Comparable<Channel>
{
    var selected = false

    override fun compareTo(other: Channel)
            = displayNumber.toInt() - other.displayNumber.toInt()
} // data class ChannelUri
