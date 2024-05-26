//package com.example.kotlin_room
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.warehousemanagment.model.constants.Utils
//
//@Database(entities = [PermissionDao::class], version = 4, exportSchema = false)
//abstract class BillDatabase:RoomDatabase()
//{
//    abstract fun billDao():PermissionDao
//
//    companion object
//    {
//        @Volatile
//        private var INSTANCE:BillDatabase ?= null
//
//        fun getDatabase(context:Context):BillDatabase
//        {
//            val tempInstance= INSTANCE
//            if (tempInstance!=null){
//                return tempInstance
//            }
//            synchronized(this)
//            {
//                val instance= Room.databaseBuilder(
//                    context.applicationContext,
//                    BillDatabase::class.java, Utils.DB_NAME).build()
//                INSTANCE=instance
//                return instance
//            }
//
//
//        }
//    }
//
//
//}