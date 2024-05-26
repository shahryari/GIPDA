package com.example.warehousemanagment

import com.example.warehousemanagment.model.classes.checkIfIsValidChars
import com.example.warehousemanagment.model.classes.search
import com.example.warehousemanagment.model.models.offline_serial.OfflineSerialModel
import org.junit.Test

import org.junit.Assert.*
import java.util.ArrayList

class ExampleUnitTest
{
    @Test
    fun test_searchClass()
    {
        val FIELD="serial"
        val list: MutableList<OfflineSerialModel> = ArrayList<OfflineSerialModel>()
        list.add(OfflineSerialModel("Serial1"))
        list.add(OfflineSerialModel("majid"))
        list.add(OfflineSerialModel("this is mine"))
        list.add(OfflineSerialModel("what is the best way"))
        list.add(OfflineSerialModel("this is me"))
        list.add(OfflineSerialModel("She"))


        val searchResult= search("wh",list,FIELD)
        assertEquals(1,searchResult.size)

        assertEquals("She", search("she",list,FIELD).get(0).serial)
        assertEquals(list.size,search("",list,"FIELD").size)

    }

    @Test
    fun check_if_serial_valid()
    {
       assertEquals(true,checkIfIsValidChars("majid2851","",9,0))
       assertEquals(false,checkIfIsValidChars("!e234>-ariw","!e",100,0))
       assertEquals(false,checkIfIsValidChars("asfasgerqerwt/  a","",16,0))
       assertEquals(false,checkIfIsValidChars("rwoirq__","",5,0))
       assertEquals(true, checkIfIsValidChars("","",0,0))



    }






}