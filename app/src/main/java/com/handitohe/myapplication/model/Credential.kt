package com.handitohe.myapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credential(
    @SerialName("user")
    val user: String,
    @SerialName("password")
    val password: String,
)
