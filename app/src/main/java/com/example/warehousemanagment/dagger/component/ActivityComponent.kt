package com.example.currencykotlin.model.di.component

 import com.example.warehousemanagment.dagger.module.ActivityModule
 import com.example.warehousemanagment.ui.activity.MainActivity
 import dagger.Component
import javax.inject.Singleton


@Component(modules = [ActivityModule::class])
@Singleton
interface ActivityComponent
{
     fun inject(activity: MainActivity)

}