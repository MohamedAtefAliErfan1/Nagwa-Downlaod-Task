package com.mohamed_atef.nagwatask.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamed_atef.nagwatask.data.model.MediaModel
import javax.inject.Inject

class ListClass  @Inject constructor(val context:Context){

    fun getDataFromFileJson(): List<MediaModel> {
        val jsonString: String = context.assets.open("getListOfFilesResponse.json").bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val listPersonType = object : TypeToken<List<MediaModel>>() {}.type
        val items: List<MediaModel> = gson.fromJson(jsonString, listPersonType)
        return items; }
}