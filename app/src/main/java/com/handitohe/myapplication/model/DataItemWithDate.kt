package com.handitohe.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DataItemWithDate(
    val date: String,
    val dateItem: DataItem,
) : Parcelable
