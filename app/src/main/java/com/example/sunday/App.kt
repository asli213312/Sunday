package com.example.sunday

import android.app.Application
import com.example.sunday.network.Api
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single {
                    Retrofit
                        .Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://dataservice.accuweather.com/")
                        .build()
                }
                single {
                    val retrofit:Retrofit = get()
                    retrofit.create(Api::class.java)
                }
            })
        }
    }
}