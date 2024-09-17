package com.example.warehousemanagment.model.data

import android.content.Context
import com.example.warehousemanagment.model.constants.Utils


class MySharedPref(context:Context)
{
    private val pref=context.getSharedPreferences(Utils.PREF_NAME,0)
    lateinit var context:Context
    init {
        this.context=context
    }

    fun getCurrentUser():String{
        return pref.getString(Utils.CURRENT_USER,"").toString()
    }
    fun saveCurrentUser(currentUser:String){
        pref.edit().putString(Utils.CURRENT_USER,currentUser).apply()
    }

    fun getWarehouseName():String{
        return pref.getString(Utils.WARE_HOUSE_NAME,"").toString()
    }
    fun saveWareHouseName(warehouseName:String){
        pref.edit().putString(Utils.WARE_HOUSE_NAME,warehouseName).apply()
    }


    fun getPermissionBy(field:String,):Int{
        return pref.getInt(field,0)
    }
    fun setPermissionBy(field: String,value:Int){
        pref.edit().putInt(field,value).apply()
    }

    fun getUserPermissions(): String? {
        return pref.getString(Utils.USER_PERMISSIONS,"")
    }
    fun setUserPermissiosns(value:String){
        pref.edit().putString(Utils.USER_PERMISSIONS,value).apply()
    }


    fun saveTokenGlcTest(token:String){
        val sb=StringBuilder()
        sb.append(Utils.COOKIE_GLCTEST)
        sb.append("=")
        sb.append(token)
        pref.edit().putString(Utils.TOKEN_GLCTEST,sb.toString()).apply()
    }
    fun getTokenGlcTest():String{
        return pref.getString(Utils.TOKEN_GLCTEST,"").toString()
    }

    fun saveDisallowRepetitiveSerial(value:Boolean){
        pref.edit().putBoolean(Utils.ALLOW_REPETITIVE_SERIAL,value).apply()
    }
    fun getDisallowRepetitiveSerial():Boolean{
        return pref.getBoolean(Utils.ALLOW_REPETITIVE_SERIAL,false)
    }


    fun saveDelayForInventorySearch(delay:Long){
        pref.edit().putLong(Utils.DELAY_INVENT_SEARCH,delay).apply()
    }
    fun getDelayForInventorySearch():Long{
        return pref.getLong(Utils.DELAY_INVENT_SEARCH,Utils.DEFUALT_INVENT_SEARCH )
    }

    fun saveDelayForInventoryReportSearch(delay:Long){
        pref.edit().putLong(Utils.DELAY_INVENT_REPORT_SEARCH,delay).apply()
    }

    fun getDelayForInventoryReport(): Long {
        return pref.getLong(Utils.DELAY_INVENT_REPORT_SEARCH,Utils.DEFUALT_INVENT_REPORT_SEARCH )
    }

    fun saveAdapterPosition(position:Int){
        pref.edit().putInt(Utils.ADAPTER_POSITION,0).apply()
    }
    fun getSavedAdapterPosition(): Int {
        return pref.getInt(Utils.ADAPTER_POSITION,0)
    }
    fun saveDomain(domain:String)
    {
        val sb=StringBuilder()
        sb.append(domain)
        sb.append(Utils.CONSTANT_PART_DOMAIN)
        pref.edit().putString(Utils.DOMAIN,sb.toString()).apply()
    }
    fun getDomain():String{
        return pref.getString(Utils.DOMAIN,Utils.FIRST_DOMAIN).toString()
    }
    fun saveUnValidChars(unValids:String){
        pref.edit().putString(Utils.UN_VALID,unValids).apply()
    }
    fun getUnValidChars(): String {
        return pref.getString(Utils.UN_VALID,"").toString()
    }

    fun saveSerialLenMax(len:Int)
    {
        pref.edit().putInt(Utils.SERIAL_LEN_MAX,len).apply()
    }

    fun getSerialLenMax():Int
    {
        return pref.getInt(Utils.SERIAL_LEN_MAX,Utils.MINUS_ONE)
    }

    fun saveSerialLenMin(len:Int)
    {
        pref.edit().putInt(Utils.SERIAL_LEN_MIN,len).apply()
    }

    fun getSerialLenMin():Int
    {
        return pref.getInt(Utils.SERIAL_LEN_MIN,Utils.MINUS_ONE)
    }


    fun saveServicePriod(timeMinute:Int)
    {
        pref.edit().putInt(Utils.SERVICE_PRIOD,timeMinute).apply()
    }

    fun getServicePriod():Int
    {
        return pref.getInt(Utils.SERVICE_PRIOD,Utils.NOTIF_DEFAULT_IME)
    }

    fun getBarcode(): Int {
        return pref.getInt(Utils.BARCODE,1)
    }
    fun saveBarcode(barcodeNumber:Int)
    {
        pref.edit().putInt(Utils.BARCODE, barcodeNumber).apply()
    }


    fun saveTinaPaperSize(len:String)
    {
        pref.edit().putString(Utils.TINA_LABLE_PRINTER,len).apply()
    }

    fun getTinaPaperSize():String
    {
        return pref.getString(Utils.TINA_LABLE_PRINTER,"").toString()
    }

    fun saveOtherPaperSize(len:String)
    {
        pref.edit().putString(Utils.OTHER_LABLE_PRINTER,len).apply()
    }

    fun getOtherPaperSize():String
    {
        return pref.getString(Utils.OTHER_LABLE_PRINTER,"").toString()
    }

    fun savePrintName(name:String)
    {
        pref.edit().putString(Utils.PRINT_NAME,name).apply()
    }

    fun getPrintName():String
    {
        return pref.getString(Utils.PRINT_NAME,"").toString()
    }
    fun saveAtLeastCountForReceivingQuantity(count:Int)
    {
        pref.edit().putInt(Utils.RECEIVE_COUNT_SERIAL,count).apply()
    }
    fun getAtLeastCountForReceivingQuantity():Int{
        return pref.getInt(Utils.RECEIVE_COUNT_SERIAL,5)
    }

    fun saveStockMinusResId(id:String)
    {
        pref.edit().putString(Utils.StockMinusResId,id).apply()
    }
    fun getSaveStockMinusResId():String{
        return pref.getString(Utils.StockMinusResId,"").toString()
    }

}