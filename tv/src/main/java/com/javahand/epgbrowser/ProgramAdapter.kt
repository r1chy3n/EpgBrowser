package com.javahand.epgbrowser

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.javahand.epgbrowser.provider.Program
import java.util.*
import kotlin.collections.ArrayList

class ProgramAdapter : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>()
{
    var selectedLongDescription = MutableLiveData( "" )

    private var programList = ArrayList<Program>()

    override fun onCreateViewHolder( parent: ViewGroup,
                                     viewType: Int ): ProgramViewHolder
    {
        val inflater = LayoutInflater.from( parent.context )
        val programItem = inflater.inflate(
            R.layout.item_program, parent, false )

        return ProgramViewHolder(programItem)
    } // fun onCreateViewHolder( ViewGroup, Int )

    override fun onBindViewHolder( holder: ProgramViewHolder, position: Int )
    {
        holder.bind( programList[ position ])
    } // fun onBindViewHolder( ProgramViewHolder, Int )

    override fun getItemCount() = programList.size

    inner class ProgramViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)
    {
        private val textId = itemView
            .findViewById<TextView>( R.id.textProgramId )
        private val textTitle = itemView
            .findViewById<TextView>( R.id.textProgramTitle )
        private val textSchedule = itemView
            .findViewById<TextView>( R.id.textProgramSchedule )
        private val textLongDescription = itemView
            .findViewById<TextView>( R.id.textProgramLongDescription )

        fun bind( program: Program )
        {
            val startDate = Date( program.startTimeUtcMillis )
            val endDate = Date( program.endTimeUtcMillis )

            textId.text = program.id.toString()
            textTitle.text = program.title
            textSchedule.text = itemView.context.getString(
                R.string.format_program_schedule, startDate, endDate )

            if ( TextUtils.isEmpty( program.longDescription ))
            {
                textLongDescription.visibility = View.INVISIBLE

                itemView.setOnClickListener( null )
            }
            else
            {
                textLongDescription.visibility = View.VISIBLE

                itemView.setOnClickListener {

                    selectedLongDescription.value = program.longDescription
                } // linearItem.setOnClickListener
            } // if - else
        } // fun bind( Program )
    } // class ProgramViewHolder

    @SuppressLint("NotifyDataSetChanged")
    fun setProgramList(pgList: ArrayList<Program>)
    {
        programList = pgList

        notifyDataSetChanged()
    } // fun setProgramList( ArrayList<Program>)
} // class RecyclerProgramAdapter