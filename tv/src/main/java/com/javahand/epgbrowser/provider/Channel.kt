package com.javahand.epgbrowser.provider

data class Channel(
    // #01
    val id: Long,   // 1
    // #21
    val description: String?,   // 2
    val displayName: String?,   // 3
    val displayNumber: String?,     // 4
    val inputId: String,            // 5
    val internalProviderData: Boolean,  // 6
    val networkAffiliation: String?,    // 7
    val originalNetworkId: Int?,        // 8
    val packageName: String,            // 9
    val searchable: Boolean,            // 10
    val serviceId: Int?,                // 11
    val serviceType: String,            // 12
    val transportStreamId: Int?,        // 13
    val type: String,                   // 14
    val versionNumber: Int,             // 15
    val videoFormat: String?,           // 16
    // #23
    val appLinkColor: Int,              // 17
    val appLinkIconUri: String?,        // 18
    val appLinkIntentUri: String?,      // 19
    val appLinkPosterArtUri: String?,   // 20
    val appLinkText: String?,           // 21
    val internalProviderFlag1: Int,     // 22
    val internalProviderFlag2: Int,     // 23
    val internalProviderFlag3: Int,     // 24
    val internalProviderFlag4: Int,     // 25
    // #26
    val browsable: Boolean,             // 26
    val internalProviderId: String?,    // 27
    val locked: Boolean,                // 28
    val transient: Boolean              // 29
) : Comparable<Channel>
{
    var selected = false

    override fun compareTo( other: Channel ): Int
    {
        var result = 0

        if ( displayNumber == null && other.displayNumber == null )
        {
            result = ( id - other.id ).toInt()
        }
        else if ( displayNumber == null )
        {
            result = -1
        }
        else if ( other.displayNumber == null )
        {
            result = 1
        }
        else
        {
            val displayNum = displayNumber?.toIntOrNull()
            val otherDpNum = other.displayNumber?.toIntOrNull()

            result = if ( displayNum != null && otherDpNum != null )
                ( displayNum - otherDpNum )
            else displayNumber.compareTo( other.displayNumber )
        } // if

        return result
    } // Fun compareTo( Channel )
} // data class ChannelUri
