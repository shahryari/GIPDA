//package com.example.kotlin_room
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.warehousemanagment.model.constants.Utils
//import com.example.warehousemanagment.model.models.login.login.Permissions
//import java.util.*
//
//@Dao
//interface PermissionDao
//{
//    @Insert()//onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addPermission(user: Permissions)
//
//    @Query("Select * from ${Utils.TABLE_PERMSISSION} WHERE id=1")
//    fun readAllPermission():Permissions
//
////    @Delete
////    suspend fun delete(billModel: BillModel)
////
////    @Update
////    suspend fun update(user:BillModel)
//
//
//
//
//}