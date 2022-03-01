package com.mohamedatef.downloadmediatask.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamed_atef.nagwatask.data.model.MediaModel
import com.mohamed_atef.nagwatask.util.ListClass
import com.mohamedatef.downloadmediatask.R
import com.mohamedatef.downloadmediatask.adapter.ListAdapter
import com.mohamedatef.downloadmediatask.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import java.net.URI
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), ListAdapter.MediaSelectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var listClass: ListClass
    private lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: DownloadViewModel
    private var listofMedia: List<MediaModel> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewmodel =
            ViewModelProvider(this, viewModelFactory)
                .get(DownloadViewModel::class.java)
        listofMedia = listClass.getDataFromFileJson()
        viewmodel.size = listofMedia.size
        binding.mediaList.layoutManager = LinearLayoutManager(this)
        binding.mediaList.adapter = ListAdapter(listofMedia, this)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), 12
        )

    }

    override fun MediaSelected(
        mediaModel: MediaModel,
        mediaIndex: Int,
        progressBar: ProgressBar,
        imageView: ImageView,
        textView: TextView
    ) {


        if (mediaModel.isCompleted) {
            if (!mediaModel.isFailed) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mediaModel.url))
                if (mediaModel.type == "PDF")
                    intent.setDataAndType(Uri.parse(mediaModel.pathFileLocal), "application/pdf")
                else
                    intent.setDataAndType(Uri.parse(mediaModel.pathFileLocal), "video/mp4")
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY;
                startActivity(intent)
            }
        } else {
            Log.e("clicked", mediaModel.name)
            var total = ""
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
            viewmodel.download(mediaModel, mediaIndex)
            viewmodel.getTotal(mediaIndex).observe(this, Observer { t -> total = t.toString() })
            viewmodel.getPercent(mediaIndex).observe(this, Observer { t ->
                progressBar.setProgress(t)
                if (t > 0) {
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.VISIBLE
                    textView.text = total + " MB / " + t.toString() + " %"
                } else
                {
                    textView.visibility = View.GONE
                    progressBar.visibility = View.GONE


                }
            })
            viewmodel.getError(mediaIndex).observe(
                this,
                Observer { t -> if (t == true) imageView.setImageResource(R.drawable.ic_error) })
            viewmodel.getSuccess(mediaIndex).observe(
                this,
                Observer { t -> if (t == true) imageView.setImageResource(R.drawable.ic_done)
                })
        }


    }


}