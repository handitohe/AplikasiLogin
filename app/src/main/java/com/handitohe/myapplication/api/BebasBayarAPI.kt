package com.handitohe.myapplication.api

import com.handitohe.myapplication.model.Credential
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class BebasBayarApi(private val client: HttpClient) {

    private companion object {
        const val BASE_URL = "https://devel.bebasbayar.com"
    }

    suspend fun login(credential: Credential): HttpResponse = client.post(forPath("/web/test_programmer.php")) {
        setBody(credential)
    }

    suspend fun getData(): HttpResponse = client.get(forPath("/web/test_programmer.php"))

    private fun forPath(path: String): String {
        return BASE_URL + path
    }
}
