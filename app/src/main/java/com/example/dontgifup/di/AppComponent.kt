package com.example.dontgifup.di

import com.example.dontgifup.ui.view_models.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ApiModule::class])
@Singleton
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}