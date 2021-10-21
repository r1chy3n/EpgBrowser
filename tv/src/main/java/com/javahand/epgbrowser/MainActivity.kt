package com.javahand.epgbrowser

import android.media.tv.TvContract
import android.os.Bundle
import android.widget.TextView
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
{
    private val programAdapter = ProgramAdapter()

    private lateinit var channelList: List<Channel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        channelList = getChannelList()

        val recyclerCh = findViewById<RecyclerView>( R.id.recycler_ch )
        val channelAdapter = ChannelAdapter( channelList )

        recyclerCh.layoutManager = LinearLayoutManager( this )
        recyclerCh.adapter = channelAdapter

        channelAdapter.selectedIndex.observe( this ) {

            updateProgramList( channelList[ it ])

            val textSelectedInfo = findViewById<
                    TextView>( R.id.textSelectedInfo )
            val now = Calendar.getInstance().time

            textSelectedInfo.text = getString( R.string
                .format_selected_info, now,
                channelList[ it ].displayNumber, channelList[ it ].displayName )
        } // channelAdapter.selectedIndex.observe

        val recyclerPg = findViewById<RecyclerView>( R.id.recycler_pg )

        recyclerPg.layoutManager = LinearLayoutManager( this )
        recyclerPg.adapter = programAdapter

        recyclerCh.requestFocus()
    } // fun onCreate( Bundle?)

    private fun updateProgramList( channel: Channel )
    {
        val calendar = Calendar.getInstance()

        calendar.set( Calendar.MILLISECOND, 0 )
        calendar.set( Calendar.SECOND, 0 )
        calendar.set( Calendar.MINUTE, 0 )
        calendar.set( Calendar.HOUR_OF_DAY, 0 )

        val startMillis = calendar.timeInMillis

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
            var program: Program

            while ( cursor.moveToNext() && !cursor.isAfterLast )
            {
                id = cursor.getLong( ProgramColumn.ID )
                title = cursor.getString( ProgramColumn.TITLE )
                startTimeUtcMillis = cursor.getLong(
                    ProgramColumn.START_TIME_UTC_MILLIS )
                endTimeUtcMillis = cursor.getLong(
                    ProgramColumn.END_TIME_UTC_MILLIS )
                program = Program( id, title,
                    startTimeUtcMillis, endTimeUtcMillis )

                programList.add( program )
            } // while
        } // cursor

        programAdapter.setProgramList( programList )
        val recyclerProgram = findViewById<RecyclerView>( R.id.recycler_pg )
        recyclerProgram.scrollToPosition( 0 )
    } // fun updateProgramList( Channel )

    private fun getChannelList(): List<Channel>
    {
        val channelList = ArrayList<Channel>()

        contentResolver.query(
            TvContract.Channels.CONTENT_URI,
            ChannelColumn.PROJECTION,
            null, null, null
        )?.use { cursor ->

            var id: Long
            var displayName: String
            var channel: Channel

            while ( cursor.moveToNext() && !cursor.isAfterLast )
            {
                // 頻道號碼有可能為空
                cursor.getString(
                    ChannelColumn.DISPLAY_NUMBER )?.let { displayNumber ->

                    if (displayNumber != "1-1" && displayNumber.length < 4 )
                    {
                        id = cursor.getLong(ChannelColumn.ID)
                        displayName = cursor
                            .getString(ChannelColumn.DISPLAY_NAME)
                        channel = Channel(id, displayName, displayNumber)

                        channelList.add( channel )
                    } // if
                } // cursor.getString( ....DISPLAY_NUMBER )?.let
            } // while
        } // contentResolver.query( ...Channels..., ... ).use

        channelList.sort()

        return channelList
    } // fun getChannelMap()
} // class MainActivity