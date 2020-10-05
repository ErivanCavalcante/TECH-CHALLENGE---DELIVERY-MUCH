package com.erivan.santos.deliverymuchtest.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

//Gera o cliente da api pra envio
open class ApiGenerator(private val retrofit: Retrofit, private val gson: Gson, private val tokenInterceptor: TokenInterceptor) {

    //Tipo de token aceito eh o Bearer
    fun <S> criarService(serviceClass: Class<S>, token: String? = null): S {
        tokenInterceptor.token = token

        return retrofit.create(serviceClass)
    }

    class Builder() {
        private var baseUrl = ""
        private val typeAdapterMapper = ArrayList<Pair<Type, Any>>()
        private var dateFormat = "yyyy-MM-dd HH:mm:ss"
        private var connectTimeoutSeconds = 60L
        private var writeTimeoutSeconds = 60L
        private var readTimeoutSeconds = 120L

        fun baseUrl(url: String) : Builder {
            baseUrl = url
            return this
        }

        fun typeAdapter(adapter: Pair<Type, Any>) : Builder {
            typeAdapterMapper.add(adapter)
            return this
        }

        fun dateFormat(format: String) : Builder {
            dateFormat = format
            return this
        }

        fun connectTimeoutSeconds(time: Long) : Builder {
            connectTimeoutSeconds = time
            return this
        }

        fun writeTimeoutSeconds(time: Long) : Builder {
            writeTimeoutSeconds = time
            return this
        }

        fun readTimeoutSeconds(time: Long) : Builder {
            readTimeoutSeconds = time
            return this
        }


        fun build(): ApiGenerator {
            //Cria o json builder e adiciona os type adapter
            val responseBuilder = GsonBuilder()
                .setDateFormat(dateFormat)
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()

            for (adapter in typeAdapterMapper) {
                responseBuilder.registerTypeAdapter(adapter.first, adapter.second)
            }

            val gson = responseBuilder.create()

            val tokenInterceptor = TokenInterceptor()

            //Cria o log do body
            val logging = HttpLoggingInterceptor()

            val httpClient = OkHttpClient.Builder()

            //Testa se ja tem o interceptor
            if (!httpClient.interceptors().contains(logging)) {
                httpClient.addInterceptor(logging)

                //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                logging.level = HttpLoggingInterceptor.Level.BODY
            }

            //Se ainda nao adicionou o token adiciona
            if (!httpClient.interceptors().contains(tokenInterceptor)) {
                httpClient.addInterceptor(tokenInterceptor)
            }

            httpClient.connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)

            //Build do retrofit
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())

            return ApiGenerator(retrofitBuilder.build(), gson, tokenInterceptor)
        }
    }
}
