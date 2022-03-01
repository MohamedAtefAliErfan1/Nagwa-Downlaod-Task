package com.mohamedatef.downloadmediatask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamed_atef.nagwatask.data.model.MediaModel
import com.mohamedatef.downloadmediatask.R

class ListAdapter(private val mList: List<MediaModel>, private val mediaSelectable: MediaSelectable):RecyclerView.Adapter<ListAdapter.MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_files, parent, false)

        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
//        holder.imageView.setImageResource(ItemsViewModel.image)
       holder.name.text = ItemsViewModel.name
        holder.itemView.setOnClickListener {
            mediaSelectable.MediaSelected(
                mList.get(position), position,holder.progress,holder.download,holder.percent
            )
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val download: ImageView = itemView.findViewById(R.id.download)
        val name: TextView = itemView.findViewById(R.id.nameFile)
        val progress:ProgressBar=itemView.findViewById(R.id.progressBar)
        val percent:TextView=itemView.findViewById(R.id.txtProgressPercent)
    }
    interface MediaSelectable {
        fun MediaSelected(mediaModel: MediaModel, mediaIndex: Int, progressBar: ProgressBar, imageView: ImageView,textView: TextView)
    }
}