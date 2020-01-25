package com.tsymbaliuk.soundrecognation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsymbaliuk.soundrecognation.pojo.Result

class SoundRecyclerViewAdapter(private var dataset: List<Result>) :
    RecyclerView.Adapter<SoundRecyclerViewAdapter.RvViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View?, position: Int, id: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SoundRecyclerViewAdapter.RvViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return RvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.singerName.text = dataset[position].artist
        holder.songName.text = dataset[position].title
    }

    override fun getItemCount() = dataset.size

    fun updateData(newdataset: List<Result>) {
        dataset = newdataset
    }

    inner class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(view: View) {
            onItemClickListener?.onItemClick(
                this@SoundRecyclerViewAdapter,
                view,
                adapterPosition,
                view.id
            )
        }

        init {
            itemView.setOnClickListener(this)
        }

        val littleCover = itemView.findViewById<ImageView>(R.id.little_cover)
        val songName = itemView.findViewById<TextView>(R.id.song_name)
        val singerName = itemView.findViewById<TextView>(R.id.singer_name)
        val albumName = itemView.findViewById<TextView>(R.id.album_name)

    }

}