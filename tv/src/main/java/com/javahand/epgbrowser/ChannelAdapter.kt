package com.javahand.epgbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.javahand.epgbrowser.provider.Channel

class ChannelAdapter(private val channelList: List<Channel>) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>()
{
    var selectedIndex = MutableLiveData<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelViewHolder
    {
        val inflater = LayoutInflater.from( parent.context )
        val channelItem = inflater.inflate(
            R.layout.item_channel, parent, false )

        return ChannelViewHolder( channelItem )
    } // fun onCreateViewHolder( ViewGroup, Int )

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int)
    {
        holder.bind( channelList[ position ])
        holder.itemView.setOnClickListener {

            selectedIndex.value?.let {
                itemSelected( it, false )
            }

            itemSelected( position, true )
        } // holder.itemView.setOnClickListener
    } // fun onBindViewHolder( ChannelViewHolder, Int )

    private fun itemSelected( position: Int, selected: Boolean )
    {
        channelList[ position ].selected = selected

        notifyItemChanged( position )

        if ( selected )
        {
            selectedIndex.value = position
        } // if
    } // fun itemSelected( Int, Boolean )

    override fun getItemCount() = channelList.size

    class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val textNum = itemView
            .findViewById<TextView>( R.id.channel_num )
        private val textName = itemView
            .findViewById<TextView>( R.id.channel_name )
        private val imageSelected = itemView
            .findViewById<ImageView>( R.id.channel_selected )

        fun bind( channel: Channel )
        {
            textNum.text = channel.displayNumber
            textName.text = channel.displayName
            imageSelected.visibility = if ( channel
                    .selected ) View.VISIBLE else View.INVISIBLE
        } // fun onBind( Channel )
    } // class ChannelViewHolder
} // class RecyclerChannelAdapter