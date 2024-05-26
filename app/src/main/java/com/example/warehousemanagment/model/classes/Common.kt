package com.example.warehousemanagment.model.classes

import android.content.Context
import android.os.Environment
import java.io.File

object Common
{
    fun getAppPath(context:Context):String
    {
//        val dir= File(android.os.Environment.getExternalStorageDirectory().toString()
//            +File.separator
//            +"magnus"
//            +File.separator)

        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (!dir.exists()){
            dir.mkdir()
        }
        return dir.path+File.separator

    }
}