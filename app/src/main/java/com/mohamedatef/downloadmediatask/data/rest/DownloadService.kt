package com.mohamed_atef.nagwatask.data.rest

import androidx.annotation.Nullable
import dagger.Provides
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.util.*
import javax.inject.Inject

interface DownloadService {
    @GET
    @Streaming
    fun downloadFile(@Url fileUrl: String?): Observable<Response<ResponseBody>>
}