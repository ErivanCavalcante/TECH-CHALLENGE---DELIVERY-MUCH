package com.erivan.santos.deliverymuchtest.config

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    var token: String? = null

    init {
        this.token = null
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Pega o request
        val request = chain.request()

        //Se nao tem token nao faz nada
        if (token == null)
            return chain.proceed(request)

        //Adiciona os cabeçalhos
        val novoRequest = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

        return chain.proceed(novoRequest)
    }
}
