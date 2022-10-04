package com.javahand.epgbrowser

import android.media.tv.TvContract
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.database.getBlobOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.javahand.epgbrowser.provider.Channel
import com.javahand.epgbrowser.provider.ChannelColumn
import com.javahand.epgbrowser.provider.Program
import com.javahand.epgbrowser.provider.ProgramColumn
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : FragmentActivity()
{ // class MainActivity
    private val programAdapter = ProgramAdapter()

    private lateinit var channelList: List<Channel>

    private var focusedPgItem: View? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerPg = findViewById<RecyclerView>( R.id.recycler_pg )

        findViewById<TextView>( R.id
            .textMainLongDescription ).setOnKeyListener { _, _, _ ->

            findViewById<TextView>( R.id
                .textMainLongDescription ).visibility = View.GONE

            findViewById<RecyclerView>( R.id.recycler_pg ).requestFocus()

            focusedPgItem?.requestFocus()

            true
        }

        programAdapter.selectedLongDescription.observe( this ) {

            focusedPgItem = recyclerPg.focusedChild

            val textLongDescriptor
            = findViewById<TextView>( R.id.textMainLongDescription )

            textLongDescriptor.text = it
            textLongDescriptor.visibility = View.VISIBLE

            textLongDescriptor.requestFocus()
        } // observe

        channelList = getChannelList()

        val recyclerCh = findViewById<RecyclerView>( R.id.recycler_ch )
        val channelAdapter = ChannelAdapter( channelList )

        recyclerCh.layoutManager = LinearLayoutManager( this )
        recyclerCh.adapter = channelAdapter

        channelAdapter.selectedIndex.observe( this ) {

            Log.i( "EpgBrowser", channelList[ it ].toString())

            updateProgramList( channelList[ it ])

            val textSelectedInfo = findViewById<
                    TextView>( R.id.textSelectedInfo )
            val now = Calendar.getInstance().time

            textSelectedInfo.text = getString( R.string
                .format_selected_info, now,
                channelList[ it ].displayNumber, channelList[ it ].displayName )
        } // channelAdapter.selectedIndex.observe


        recyclerPg.layoutManager = LinearLayoutManager( this )
        recyclerPg.adapter = programAdapter

        recyclerCh.requestFocus()
    } // fun onCreate( Bundle?)

    private fun updateProgramList(channel: Channel )
    {
        // 今天時間
        val calendar = Calendar.getInstance()

        // 今天凌晨零點零分
        calendar.set( Calendar.MILLISECOND, 0 )
        calendar.set( Calendar.SECOND, 0 )
        calendar.set( Calendar.MINUTE, 0 )
        calendar.set( Calendar.HOUR_OF_DAY, 0 )

        val startMillis = calendar.timeInMillis

        // 七天後
        calendar.add( Calendar.HOUR, 24 * 7 )

        val endMillis = calendar.timeInMillis
        val chPgUri = TvContract.buildProgramsUriForChannel(
            channel.id, startMillis, endMillis )
        val programList = ArrayList<Program>()

        contentResolver.query( chPgUri, ProgramColumn.PROJECTION,
            null, null, null
        )?.use { cursor ->

            var id: Long
            var title: String
            var startTimeUtcMillis: Long
            var endTimeUtcMillis: Long
            var longDescription: String?
            var program: Program

            while ( cursor.moveToNext() && !cursor.isAfterLast )
            {
                id = cursor.getLong( ProgramColumn.ID )
                title = cursor.getString( ProgramColumn.TITLE )
                startTimeUtcMillis = cursor
                    .getLong( ProgramColumn.START_TIME_UTC_MILLIS )
                endTimeUtcMillis = cursor
                    .getLong( ProgramColumn.END_TIME_UTC_MILLIS )
                longDescription = cursor
                    .getString( ProgramColumn.LONG_DESCRIPTION )
                program = Program( id, title,
                    startTimeUtcMillis, endTimeUtcMillis, longDescription )

                programList.add( program )
            } // while
        } // cursor

        if (programList.isNotEmpty())
        {
            checkProgram(programList, startMillis )
        } // if

        programAdapter.setProgramList( programList )

        val recyclerProgram = findViewById<RecyclerView>( R.id.recycler_pg )

        recyclerProgram.scrollToPosition( 0 )
    } // fun updateProgramList( Channel )

    private fun checkProgram( pgList: ArrayList<Program>, startMillis: Long )
    {
        var prevPg = pgList.first()
        var count = 0

        if ( startMillis < prevPg.startTimeUtcMillis )
        {
            val noDataPg = Program( -1,
                "* 無資料", startMillis, prevPg.startTimeUtcMillis )

            pgList.add( 0, noDataPg )

            prevPg = noDataPg
        } // if

        for ( i in 1 until pgList.size)
        {


            if ( prevPg.endTimeUtcMillis != pgList[ i ].startTimeUtcMillis )
            {
                prevPg.title = "* " + prevPg.title
                pgList[ i ].title = "* " + pgList[ i ].title
                count += 2
            } // if

            prevPg = pgList[ i ]
        } // for

        findViewById<TextView>( R.id
            .text_problem_count ).text = count.toString()
    } // Method checkProgram( ArrayList<Program>, Long, Long )

    private fun getChannelList(): List<Channel>
    {
        val channelList = ArrayList<Channel>()

        contentResolver.query(
            TvContract.Channels.CONTENT_URI,
            ChannelColumn.PROJECTION,
            null, null, null
        )?.use { cursor ->

            // #01
            var id: Long    // 1
            // #21
            var description: String?    // 2
            var displayName: String?    // 3
            var displayNumber: String?  // 4
            var inputId: String         // 5
            var internalProviderData: Boolean   // 6
            var networkAffiliation: String?     // 7
            var originalNetworkId: Int?         // 8
            var packageName: String             // 9
            var searchable: Boolean             // 10
            var serviceId: Int?                 // 11
            var serviceType: String             // 12
            var transportStreamId: Int?         // 13
            var type: String                    // 14
            var versionNumber: Int              // 15
            var videoFormat: String?            // 16
            // #23
            var appLinkColor: Int               // 17
            var appLinkIconUri: String?         // 18
            var appLinkIntentUri: String?       // 19
            var appLinkPosterArtUri: String?    // 20
            var appLinkText: String?            // 21
            var internalProviderFlag1: Int      // 22
            var internalProviderFlag2: Int      // 23
            var internalProviderFlag3: Int      // 24
            var internalProviderFlag4: Int      // 25
            // #26
            var browsable: Boolean              // 26
            var internalProviderId: String?     // 27
            var locked: Boolean                 // 28
            var transient: Boolean              // 29

            var channel: Channel

            while ( cursor.moveToNext() && !cursor.isAfterLast )
            {
                id = cursor.getLong( ChannelColumn.ID ) // 1
                description = cursor.getStringOrNull(
                    ChannelColumn.DESCRIPTION )         // 2
                displayName = cursor.getStringOrNull(
                    ChannelColumn.DISPLAY_NAME )        // 3
                displayNumber = cursor.getStringOrNull(
                    ChannelColumn.DISPLAY_NUMBER )      // 4
                inputId = cursor.getString( ChannelColumn.INPUT_ID )    // 5
                internalProviderData = cursor.getBlobOrNull(
                    ChannelColumn.INTERNAL_PROVIDER_DATA ) == null      // 6
                networkAffiliation = cursor.getStringOrNull(
                    ChannelColumn.NETWORK_AFFILIATION )                 // 7
                originalNetworkId = cursor.getIntOrNull(
                    ChannelColumn.ORIGINAL_NETWORK_ID )                 // 8
                packageName = cursor.getString( ChannelColumn.PACKAGE_NAME )
                searchable = cursor.getInt( ChannelColumn.SEARCHABLE ) == 1
                serviceId = cursor.getIntOrNull( ChannelColumn.SERVICE_ID )
                serviceType = cursor.getString( ChannelColumn.SERVICE_TYPE )
                transportStreamId = cursor.getIntOrNull(
                    ChannelColumn.TRANSPORT_STREAM_ID )                 // 13
                type = cursor.getString( ChannelColumn.TYPE )           // 14
                versionNumber = cursor.getInt( ChannelColumn.VERSION_NUMBER )
                videoFormat = cursor.getStringOrNull( ChannelColumn.VIDEO_FORMAT )
                appLinkColor = cursor.getInt( ChannelColumn.APP_LINK_COLOR )
                appLinkIconUri = cursor.getStringOrNull(
                    ChannelColumn.APP_LINK_ICON_URI )                   // 18
                appLinkIntentUri = cursor.getStringOrNull(
                    ChannelColumn.APP_LINK_INTENT_URI )                 // 19
                appLinkPosterArtUri = cursor.getStringOrNull(
                    ChannelColumn.APP_LINK_POSTER_ART_URI )             // 20
                appLinkText = cursor.getStringOrNull(
                    ChannelColumn.APP_LINK_TEXT )                       // 21
                internalProviderFlag1 = cursor.getInt(
                    ChannelColumn.INTERNAL_PROVIDER_FLAG1 )             // 22
                internalProviderFlag2 = cursor.getInt(
                    ChannelColumn.INTERNAL_PROVIDER_FLAG2 )             // 23
                internalProviderFlag3 = cursor.getInt(
                    ChannelColumn.INTERNAL_PROVIDER_FLAG3 )             // 24
                internalProviderFlag4 = cursor.getInt(
                    ChannelColumn.INTERNAL_PROVIDER_FLAG4 )             // 25
                browsable = cursor.getInt( ChannelColumn.BROWSABLE ) == 1
                internalProviderId = cursor.getStringOrNull(
                    ChannelColumn.INTERNAL_PROVIDER_ID )                // 27
                locked = cursor.getInt( ChannelColumn.LOCKED ) == 1     // 28
                transient = cursor.getInt( ChannelColumn.TRANSIENT ) == 1

                channel = Channel( id, description, displayName, displayNumber,
                    inputId, internalProviderData, networkAffiliation,
                    originalNetworkId, packageName, searchable, serviceId,
                    serviceType, transportStreamId, type, versionNumber,
                    videoFormat, appLinkColor, appLinkIconUri, appLinkIntentUri,
                    appLinkPosterArtUri, appLinkText, internalProviderFlag1,
                    internalProviderFlag2, internalProviderFlag3,
                    internalProviderFlag4, browsable, internalProviderId,
                    locked, transient )

                channelList.add( channel )
            } // while
        } // contentResolver.query( ...Channels..., ... ).use

        channelList.sort()

        return channelList
    } // fun getChannelMap()
}