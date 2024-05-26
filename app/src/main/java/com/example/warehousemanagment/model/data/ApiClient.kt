package com.example.warehousemanagment.model.data

import com.example.warehousemanagment.model.constants.Utils
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class ApiClient
{
    companion object{
        val baseUrl=Utils.FIRST_DOMAIN

        private var retrofit: Retrofit?=null

        fun getRetrofit() : Retrofit?
        {
            if(retrofit== null)
            {
                val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()

                retrofit= Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit

        }





    }


}