package com.handitohe.myapplication.repository

import com.handitohe.myapplication.api.BebasBayarApi
import com.handitohe.myapplication.model.Credential
import com.handitohe.myapplication.model.DataItem
import com.handitohe.myapplication.model.DataItemWithDate
import com.handitohe.myapplication.model.LoginResult
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

class BebasBayarRepository @Inject constructor(
    private val bebasBayarApi: BebasBayarApi,
    private val dispatcher: CoroutineDispatcher,
) {

    private companion object {
        private const val RC_SUCCESS = "00"
    }

    fun login(credential: Credential): Flow<LoginResult> = flow {
        val result = bebasBayarApi.login(credential).body<LoginResult>()
        when (result.code) {
            RC_SUCCESS -> emit(result)
            else -> error(result.message)
        }
    }.flowOn(dispatcher)

    fun getData(): Flow<List<DataItemWithDate>> = flow {
        val result = bebasBayarApi.getData().body<JsonObject>()
        val listOfDataItem = result.map { (key, value) ->
            Json.decodeFromJsonElement<List<DataItem>>(value).map { DataItemWithDate(date = key, dateItem = it) }
        }.flatten()
        emit(listOfDataItem)
    }.flowOn(dispatcher)
}
