package com.erivan.santos.deliverymuchtest.config

import android.app.Application
import com.erivan.santos.deliverymuchtest.BuildConfig

class AppApplication : Application() {
    private lateinit var api: ApiGenerator

    companion object {
        @JvmStatic
        private var application: AppApplication? = null

        @JvmStatic
        @Synchronized
        fun getInstance(): AppApplication {
            if (application == null)
                throw Exception("Aplicação ainda não foi criada")

            return application!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        //Cria a api para uso
        api = ApiGenerator.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .dateFormat("yyyy-MM-dd HH:mm:ss")
            .connectTimeoutSeconds(30)
            .writeTimeoutSeconds(120)
            .readTimeoutSeconds(120)
            .build()

        //PEga a referencia da aplicação
        application = this;
    }

    @Synchronized
    fun getApi() : ApiGenerator {
        return api
    }
}