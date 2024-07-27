package com.example.warehousemanagment.dagger.module

import android.app.Application
import android.content.Context
import com.example.warehousemanagment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule()
{
    lateinit var application:Application
    lateinit var context:Context
    constructor(application: Application ) : this() {
        this.application=application
    }
    @Singleton
    @Provides
    fun mainViewModel(): MainViewModel {
        return MainViewModel(application)
    }






}