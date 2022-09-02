package com.handitohe.myapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    @SerialName("rc")
    val code: String,
    @SerialName("rd")
    val message: String,
)
