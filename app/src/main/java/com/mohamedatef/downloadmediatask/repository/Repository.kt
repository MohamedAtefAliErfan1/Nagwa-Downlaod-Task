package com.mohamedatef.downloadmediatask.repository

import com.mohamed_atef.nagwatask.data.model.MediaModel
import com.mohamed_atef.nagwatask.data.rest.DownloadService
import com.mohamed_atef.nagwatask.util.ListClass
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(val downloadService: DownloadService) {
    @Inject
    lateinit var listClass: ListClass
    fun download(url: String): Observable<Response<ResponseBody>> {
        return downloadService.downloadFile(url)
    }

    fun getList(): List<MediaModel> {
        return listClass.getDataFromFileJson()
    }
}