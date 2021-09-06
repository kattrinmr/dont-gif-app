package com.example.dontgifup

import android.app.Application
import com.example.dontgifup.di.AppComponent
import com.example.dontgifup.di.AppModule
import com.example.dontgifup.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }


}