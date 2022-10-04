package com.javahand.epgbrowser

import android.media.tv.TvContract
import android.os.Bundle
import android.view.View
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

            var id: Long
            var displayName: String
            var displayNumber: String
            var channel: Channel

            while ( cursor.moveToNext() && !cursor.isAfterLast )
            {
                // 頻道號碼 與 頻道名稱 皆有可能為空
                if ( !cursor.isNull( ChannelColumn.DISPLAY_NAME )
                    && !cursor.isNull( ChannelColumn.DISPLAY_NUMBER ))
                {
                    displayNumber = cursor
                        .getString( ChannelColumn.DISPLAY_NUMBER )

                    if ( displayNumber != "1-1" && displayNumber.length < 4 )
                    {
                        id = cursor.getLong(ChannelColumn.ID)
                        displayName = cursor
                            .getString(ChannelColumn.DISPLAY_NAME)
                        channel = Channel(id, displayName, displayNumber)

                        channelList.add( channel )
                    } // if
                } // if
            } // while
        } // contentResolver.query( ...Channels..., ... ).use

        channelList.sort()

        return channelList
    } // fun getChannelMap()
}