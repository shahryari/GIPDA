package com.example.warehousemanagment.model.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.login.CatalogModel
import javax.inject.Inject


class DatabaseHelper (context: Context) :
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)
{

    companion object {
        private val DATABASE_VERSION = 4
        private val DATABASE_NAME = "mag1.db"

        private val TABLE_NAME = "tableName"
//      private val COL_ID = "id"
        private val COL_CatalogValueCode = "valueCode"
        private val COL_ValueField = "value"
        private val COL_Title = "title"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("create table " + TABLE_NAME + "( "
//                + COL_ID + " INTEGER PRIMARY KEY ," +
                +COL_CatalogValueCode + " TEXT," + COL_ValueField + " int," + COL_Title + " TEXT)")
        db!!.execSQL(createTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //db!!.execSQL("Drop Table if exists "+ TABLE_NAME)
        db!!.execSQL("Drop table if exists $TABLE_NAME")
        onCreate(db)
    }

    fun getDenyReason(): List<CatalogModel>
    {
        val list = ArrayList<CatalogModel>()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_CatalogValueCode " + "='"+Utils.Cardisapprovereason + "'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst())
        {
            do
            {
                val model = CatalogModel(cursor.getString(0),
                    cursor.getInt(1),
                    cursor?.getString(2).toString())
                list.add(model)
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }
    fun getCarType(carNumber:Int): CatalogModel?
    {
        var model:CatalogModel ?=null
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_CatalogValueCode " + "='"+Utils.CarType + "'"+
                " AND "+ COL_ValueField + "='"+carNumber + "'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst())
        {
            do
            {
                model = CatalogModel(cursor.getString(0),
                    cursor.getInt(1),
                    cursor?.getString(2).toString())
                return model
            } while (cursor.moveToNext())
        }
        db.close()
        return model
    }


    fun saveCatalogList(list: List<CatalogModel>)
    {
        val db = this.writableDatabase
        for (i in 0..list.size-1)
        {
            val values = ContentValues()
            values.put(COL_CatalogValueCode, list.get(i).catalogValueCode)
            values.put(COL_ValueField, list.get(i).valueField)
            values.put(COL_Title, list?.get(i)?.title)
            db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE)
        }
        db.close()
    }

//
//    fun updatePerson(model:MyModel){
//        val db=this.writableDatabase
//        val values= ContentValues()
//        values.put(COL_ID,model.id)
//        values.put(COL_NAME,model.name)
//        values.put(COL_EMAIL,model.email)
//
//        db.update(TABLE_NAME, values,"$COL_ID=?", arrayOf(model.id.toString()))
//        db.close()
//    }
//
//    fun deletePerson(model:MyModel){
//        val db=this.writableDatabase
//        val values= ContentValues()
//        values.put(COL_ID,model.id)
//        values.put(COL_NAME,model.name)
//        values.put(COL_EMAIL,model.email)
//
//        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(model.id.toString()))
//        db.close()
//    }



//    fun getListPersons():List<MyModel>{
//        val listPersons=ArrayList<MyModel>()
//        val selectQuery="SELECT * FROM $TABLE_NAME"
//        val db=this.writableDatabase
//        val cursor=db.rawQuery(selectQuery,null)
//        if (cursor.moveToFirst()){
//            do {
//                val model=MyModel()
//                model.id=cursor.getInt(0)
//                model.name=cursor.getString(1)
//                model.email=cursor.getString(2)
//                listPersons.add(model)
//            }while (cursor.moveToNext())
//        }
//        return listPersons
//    }



}