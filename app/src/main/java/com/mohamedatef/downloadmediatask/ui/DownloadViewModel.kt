package com.mohamedatef.downloadmediatask.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamed_atef.nagwatask.data.model.MediaModel
import com.mohamedatef.downloadmediatask.repository.Repository
import com.mohamedatef.downloadmediatask.util.DownloadUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class DownloadViewModel @Inject constructor(private val application: Application) :
    ViewModel() {
    @Inject
    lateinit var repository: Repository
    var size:Int = 12
    private val percent = ArrayList<MutableLiveData<Int>>()
    private val total = ArrayList<MutableLiveData<Int>>()
    private val error = ArrayList<MutableLiveData<Boolean>>()
    private val success = ArrayList<MutableLiveData<Boolean>>()

    fun download(mediaModel: MediaModel,mediaIndex:Int) {

        percent.get(mediaIndex).postValue(0)
        error.get(mediaIndex).postValue(false)
        success.get(mediaIndex).postValue(false)
        repository.download(mediaModel.url)
            .flatMap(DownloadUtil().processResponse(percent.get(mediaIndex), mediaModel,total.get(mediaIndex),success.get(mediaIndex)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(DownloadUtil().handleResult(error.get(mediaIndex), success.get(mediaIndex)))

    }


    init {
        for (i in 0..size)
        {
            percent.add(i,MutableLiveData<Int>())
            error.add(i,MutableLiveData<Boolean>())
            success.add(i,MutableLiveData<Boolean>())
            total.add(i,MutableLiveData<Int>())
        }

    }

    fun getPercent(mediaIndex: Int): MutableLiveData<Int> {
        return percent.get(mediaIndex)
    }
    fun getTotal(mediaIndex: Int): MutableLiveData<Int> {
        return total.get(mediaIndex)
    }

    fun getError(mediaIndex: Int): MutableLiveData<Boolean> {
        return error.get(mediaIndex)
    }

    fun getSuccess(mediaIndex: Int): MutableLiveData<Boolean> {
        return success.get(mediaIndex)
    }
}