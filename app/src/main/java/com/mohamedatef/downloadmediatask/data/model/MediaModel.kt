package com.mohamed_atef.nagwatask.data.model
import android.os.Parcel
import android.os.Parcelable
data class MediaModel(
    val id: Int,
    var name: String,
    val type: String,
    val url: String,
    var pathFileLocal: String? = null,
    var startDownload: Boolean = false,
    var isCompleted: Boolean = false,
    var isFailed: Boolean = false,
    var currentDownload: Long = 0,
    var totalFileSize: Long = Long.MAX_VALUE
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(pathFileLocal)
        parcel.writeByte(if (startDownload) 1 else 0)
        parcel.writeByte(if (isCompleted) 1 else 0)
        parcel.writeByte(if (isFailed) 1 else 0)
        parcel.writeLong(currentDownload)
        parcel.writeLong(totalFileSize)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaModel> {
        override fun createFromParcel(parcel: Parcel): MediaModel {
            return MediaModel(parcel)
        }

        override fun newArray(size: Int): Array<MediaModel?> {
            return arrayOfNulls(size)
        }
    }
}
