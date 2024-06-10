package com.example.warehousemanagment.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.example.warehousemanagment.model.classes.*
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.models.login.login.Permissions
import com.example.warehousemanagment.model.models.login.login.LoginModel
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch


class LoginViewModel(application: Application,context:Context): AndroidViewModel(application)
{
    private val repository=MyRepository()
    private var loginModel=MutableLiveData<LoginModel>()
    private var context:Context=context

    private var disposable=CompositeDisposable()




    fun dispose(){
        disposable.clear()
    }

    fun getLoginResult(): LiveData<LoginModel> {
        return loginModel.distinctUntilChanged()
    }

    fun login(
        baseUrl:String,username: String, password: String, submit: View, progressBar: ProgressBar)
    {
        viewModelScope.launch {
            val jsonObject=JsonObject()
            jsonObject.addProperty("Username",username)
            jsonObject.addProperty("Password",password)
            showProgress(true, submit, progressBar)
            repository.login(baseUrl,jsonObject)
                .subscribe(
                    {
                    showProgress(false, submit, progressBar)

                        loginModel.value=it

                        log("login", it.toString())

                    },
                    {
                        showProgress(false, submit, progressBar)
                        showErrorMsg(it, "login", context)

                    },
                ).let {disposable.add(it) }
        }

    }

}