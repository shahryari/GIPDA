package com.example.warehousemanagment.model.classes

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.CountDownTimer
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.DialogSheetSortFilterBinding
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.models.DimenModel
import com.example.warehousemanagment.ui.dialog.SheetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.util.Collections


fun setDescAndCopyRight(copyRightTv: TextView, appDescriptionTv: TextView,context: Context)
{
    val copyRightText = copyRightTv.text.toString()
    val description=appDescriptionTv.text.toString()

    appDescriptionTv.setText(getSpanTv(
        0,
        6,
        ContextCompat.getColor(context, R.color.mainYellow),
        description
    ))
    copyRightTv.setText(getSpanTv(copyRightTv.text.length - 1 - context.getString(R.string.bitfinity).length,
        copyRightText.length,ContextCompat.getColor(context, R.color.mainYellow),copyRightText ))


}

fun getSpanTv(start:Int,end:Int,color:Int,str:String ): SpannableString
{
    val spannable = SpannableString(str)
    spannable.setSpan(StyleSpan(Typeface.BOLD),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun getSpanTv2(start:Int,end:Int,color:Int,str:String,fontSize:Int): SpannableString
{
    val spannable = SpannableString(str)
    spannable.setSpan(StyleSpan(Typeface.BOLD),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan( AbsoluteSizeSpan(fontSize), start, end , SPAN_INCLUSIVE_INCLUSIVE);
    return spannable
}


fun textEdi(edi:EditText): String {
    return edi.text.toString()
}
fun lenEdi(edi:EditText): Int {
    return edi.text.toString().length
}
fun log(tag:String,title:String){
    Log.i("mag2851-"+tag,title)
}
fun logErr(tag:String,title:String){
    Log.i("mag2851-"+tag+"Err",title)
}
var snackbar:Snackbar ?= null
var mySheetAlertDialog: SheetAlertDialog?=null
fun toast(msg:String,context: Context)
{

    mySheetAlertDialog= SheetAlertDialog(context.getString(R.string.message),msg
        ,object : SheetAlertDialog.OnClickListener{
            override fun onCanselClick() {
                mySheetAlertDialog?.dismiss()
            }

            override fun onOkClick(progress: ProgressBar, toInt: String)
            {
                mySheetAlertDialog?.dismiss()
            }


            override fun onCloseClick() {
                mySheetAlertDialog?.dismiss()
            }
            override fun hideCansel(cansel: TextView) {
                cansel.visibility=View.GONE
            }

            override fun onDismiss() {

            }

        })
    mySheetAlertDialog?.show((context as FragmentActivity).supportFragmentManager, "")

}

private fun showSnackBar(
    rootView: View,
    msg: String,
    context: Context
) {
    snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE)
    snackbar?.setTextColor(ContextCompat.getColor(context, R.color.white))
    val snackBarView = snackbar?.view
    snackBarView?.setBackgroundColor(ContextCompat.getColor(context, R.color.black))


    snackbar?.setAction(R.string.ok, object : View.OnClickListener {
        override fun onClick(v: View?) {
            snackbar?.dismiss()
        }
    })

    snackbar?.show()
}

fun dismissSheet(){
    mySheetAlertDialog?.dismiss()
//    snackbar?.dismiss()
}
fun hideKeyboard(activity: Activity)
{
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
fun hideKeyboard2(activity: Activity) {
    // Check if no view has focus:
    val view: View? = activity.getCurrentFocus()
    if (view != null) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
fun createAlertDialog(dialogBinding: ViewDataBinding, background:Int,context: Context): AlertDialog
{
    val builder = AlertDialog.Builder(context)
    builder.setView(dialogBinding.root)
    val dialog:AlertDialog?
    dialog = builder.create()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window
        ?.setBackgroundDrawableResource(background)
    dialog.show()

    return dialog
}


fun showProgress(showLoading:Boolean, view: View, progressBar: ProgressBar){
    if (showLoading==true){
        view.visibility= View.INVISIBLE
        progressBar.visibility= View.VISIBLE
    }else{
        view.visibility= View.VISIBLE
        progressBar.visibility= View.INVISIBLE
    }
}
fun showSimpleProgress(showLoading:Boolean , progressBar: ProgressBar){
    if (showLoading==true){
        progressBar.visibility= View.VISIBLE
    }else{
        progressBar.visibility= View.INVISIBLE
    }
}
fun changeBackgroundTint(view:View, context: Context, color:Int){
    view.backgroundTintList=ContextCompat.getColorStateList(context,color)
}
fun hideView(context:Context,id:Int,visiblity:Int){
    (context as Activity).findViewById<View>(id)?.visibility=visiblity
}
fun setToolbarTitle(context:Context,title:String){
    (context as Activity).findViewById<TextView>(R.id.title)?.setText(title)
}
fun initShortCut(context:Context,title:String,onClick:()->Unit){
    (context as Activity).findViewById<TextView>(R.id.shortcut)?.setText(title)
    (context as Activity).findViewById<TextView>(R.id.shortcut).visibility=View.VISIBLE
    (context as Activity).findViewById<TextView>(R.id.shortcut).setOnClickListener {
        onClick()
        (context as Activity).findViewById<TextView>(R.id.shortcut).visibility=View.INVISIBLE
    }
}
fun hideShortCut(context:Context){
    (context as Activity).findViewById<TextView>(R.id.shortcut).visibility=View.INVISIBLE
}


fun setBelowCount(context: Context, str1: String, count: Int, str2: String)
{
    val sb= StringBuilder()
    sb.append(str1)
    sb.append(" ")
    sb.append(count)
    sb.append(" ")
    sb.append(str2)

    val coloredCount=getSpanTv2(sb.indexOf(count.toString()),sb.indexOf(count.toString())+
            count.toString().length,
        ContextCompat.getColor(context, R.color.black),
        sb.toString(),context.getResources().getDimensionPixelSize(R.dimen.belowTitleFontSize)+10)
    (context as Activity).findViewById<TextView>(R.id.summaryTv)?.setText(coloredCount)
}







fun hideActivityView(context:Context, id:Int, visiblity:Int){
    (context as Activity).findViewById<View>(id)?.visibility=visiblity
}

fun showErrorMsg(it:Throwable,logTitle:String,context: Context)
{
    if (it is HttpException)
    {
        val errorBody = (it).response()?.errorBody()
        val errorMsg= Html.fromHtml(errorBody!!.string()).toString()
        val jsonObj = JSONObject(errorMsg)


        logErr(logTitle,errorMsg)
//        log("test",jsonObj.optString("Message").toString())
        if (jsonObj.optString("Message").toString().length!=0)
        {
            toast(jsonObj.optString("Message").toString(),context)
            log("test",jsonObj.optString("Message").toString())
        }else{
            toast(jsonObj.optString("Messages").toString().replace("[","")
                .replace("]",""),context)
        }
    }else {
        logErr(logTitle,it.toString())
        it.localizedMessage?.let { it1 -> toast(it1,context) }
    }
}

fun isStockMessageEqualMinusOne(it:Throwable,logTitle:String,): Boolean {
    if (it is HttpException)
    {
        val errorBody = (it).response()?.errorBody()
        val errorMsg= Html.fromHtml(errorBody!!.string()).toString()
        val jsonObj = JSONObject(errorMsg)


        logErr(logTitle,errorMsg)
        return if(jsonObj.optInt("MessageCode")==Utils.MINUS_ONE)
            true
        else false

    }else {
        logErr(logTitle,it.toString())
        return false;
    }

}



fun clearEdi(img: ImageView, edi:EditText)
{
    edi.requestFocus()

    if (lenEdi(edi)==0)
        img.visibility=View.INVISIBLE

    edi.doAfterTextChanged()
    {
        if (lenEdi(edi)==0)
            img.visibility=View.INVISIBLE
        else img.visibility=View.VISIBLE
    }

    img.setOnClickListener {
        edi.setText("")
    }
}

fun getAppPath(context:Context): String
{
    val sb= java.lang.StringBuilder()
    sb.append(android.os.Environment.getExternalStorageDirectory().toString())
    sb.append(File.separator.toString())
    sb.append(context.resources.getString(R.string.app_name))
    sb.append(File.separator.toString())


    val dir= File(sb.toString())
    if (!dir.exists())
    {
        dir.mkdir()
    }
    return dir.path+ File.separator

}

fun getBuiltString(
    str1: String = "", str2: String = "", str3: String = "", str4: String = "",
    str5: String = "", str6: String = "", str7: String = "", str8: String = "",
    str9: String = "", str10: String = "", str11: String = "", str12: String = "",
):String{
    val sb=StringBuilder()
    sb.append(str1)
    sb.append(str2)
    sb.append(str3)
    sb.append(str4)
    sb.append(str5)
    sb.append(str6)
    sb.append(str7)
    sb.append(str8)
    sb.append(str9)
    sb.append(str10)
    sb.append(str11)
    sb.append(str12)
    return sb.toString()

}
fun getDimen(context: Context):DimenModel{
    val displayMetrics = DisplayMetrics()
    (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels

    return DimenModel(height,width)

}
fun expandOrDecrease(target:Int,expanding:Boolean,view:View,duration:Long,viewStarter:View)
{
    val showAnim=ShowAnim(view,target, expanding)
    showAnim.duration=duration
    viewStarter.startAnimation(showAnim)
}





fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
    if (context != null && permissions != null) {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission!!)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
    }
    return true
}

fun initRequestPermission(activity: Activity, permissions: Array<String>, request: Int) {
    if (!hasPermissions(activity, *permissions)) {
        ActivityCompat.requestPermissions(activity, permissions, request)
    }
}


fun checkTick(img:ImageView,binding: DialogSheetSortFilterBinding)
{
    binding.locationCodeImg.visibility=View.GONE
    binding.ownerCOdeImg.visibility=View.GONE
    binding.productCodeImg.visibility=View.GONE
    binding.productTitleImg.visibility=View.GONE
    binding.img5.visibility = View.GONE
    binding.img6.visibility = View.GONE
    img.visibility=View.VISIBLE
}
fun <T> search(mainSearchTitle:String,mainList:List<T>,field1:String,field2:String=""
               ,field3: String="",field4:String="",field5: String=""
               ,field6: String="",field7:String="",field8: String=""): MutableList<T>
{
    val newList: MutableList<T> = ArrayList<T>()
    val gson = Gson()
    val arr: String = gson.toJson(mainList, object : TypeToken<ArrayList<T>>() {}.getType())
    val jsonArray = JSONArray(arr)

    for (i  in 0..mainList.size-1)
    {
        val model=mainList[i]
        val jObj: JSONObject = jsonArray.getJSONObject(i)
        val value1 = jObj.optString(field1).lowercase()
        val value2 = jObj.optString(field2).lowercase()
        val value3 = jObj.optString(field3).lowercase()
        val value4 = jObj.optString(field4).lowercase()
        val value5 = jObj.optString(field5).lowercase()
        val value6 = jObj.optString(field6).lowercase()
        val value7 = jObj.optString(field7).lowercase()
        val value8 = jObj.optString(field8).lowercase()
        val searchTitle=mainSearchTitle.lowercase()

        if (searchTitle.endsWith("enter")){
            searchTitle.replace("enter","")
        }

        if (value1.contains(searchTitle)||value2.contains(searchTitle)||
            value3.contains(searchTitle)||value4.contains(searchTitle)||
            value5.contains(searchTitle)||value6.contains(searchTitle)||
            value7.contains(searchTitle)||value8.contains(searchTitle) )
        {
            newList.add(model)
        }
    }
    return newList
}

fun <T> search(searchText:String,list:List<T>,searchField:String): MutableList<T>
{
    val newList: MutableList<T> = ArrayList<T>()
    val gson = Gson()
    val arr: String = gson.toJson(list, object : TypeToken<ArrayList<T>>() {}.getType())
    val jsonArray = JSONArray(arr)

    for (i  in list.indices)
    {
        val model=list[i]
        val jObj: JSONObject = jsonArray.getJSONObject(i)
        val value = jObj.optString(searchField).lowercase()

        val searchTitle=searchText.lowercase()

        if (value.contains(searchTitle) )
        {
            newList.add(model)
        }
    }
    return newList
}

fun <T> sortArray(list:List<T>,sortField:String)
{

    Collections.sort(list,object :Comparator<T>
    {
        override fun compare(o1: T, o2: T): Int
        {
            val json1=JSONObject()
            val json2=JSONObject()
            json1.put(sortField,o1)
            json2.put(sortField,o2)

            return json1.optString(sortField)
                .compareTo(json2.optString(sortField),ignoreCase = true)
        }
    })
}
fun setToolbarBackground(rel:RelativeLayout,context: Context)
{
     rel.background=ContextCompat.getDrawable(context
        ,R.drawable.shape_background_rect_white_6_half)
}

fun checkIfIsValidChars(
                        serial:String
                        ,unValidStr:String
                        ,serialLenMax:Int
                        ,serialLenMin:Int
                        ,context: Context): Boolean
{
    if (serial.trim().length>serialLenMax && serialLenMax!=Utils.MINUS_ONE){
        toast(context.getString(R.string.serialLenMoreThan)+" "+serialLenMax.toString(),context)
        return false
    }
    else if (serial.trim().length<serialLenMin &&serialLenMin!=Utils.MINUS_ONE)
    {
        toast(context.getString(R.string.serialLenLessThan)+" "+serialLenMin.toString(),context)
        return false
    }
    else if (unValidStr.isEmpty())
        return true
    else
    {
        val unValidArr=Array(unValidStr.length) { unValidStr[it].toString() }

        log("unValidArr",unValidArr.toString())


        for (i in unValidArr)
        {
            if (serial.contains(i))
            {
                log("illegalChar",i)
                toast(context.getString(R.string.illegalCharSerial),context)
                return false
            }

        }
        return true
    }

}


fun checkIfIsValidChars(
    serial:String
    ,unValidStr:String
    ,serialLenMax:Int
    ,serialLenMin:Int ): Boolean
{
    if (serial.trim().length>serialLenMax && serialLenMax!=Utils.MINUS_ONE){
        return false
    }
    else if (serial.trim().length<serialLenMin && serialLenMin!=Utils.MINUS_ONE)
    {
        return false
    }
    else if (unValidStr.length==0)
        return true
    else
    {
        val unValidArr=Array(unValidStr.length) { unValidStr[it].toString() }
        for (i in unValidArr)
        {
            if (serial.contains(i))
            {
                return false
            }
        }
        return true
    }

}


fun myKeyEnter(edi: EditText,func:()->Unit)
{
    edi.setOnKeyListener(View.OnKeyListener { v,keyCode,event->
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
            func()
            return@OnKeyListener true
        }
        false
    })
}

fun checkEnterKey(edi: EditText,func:()->Unit)
{
    edi.doAfterTextChanged { myKeyEnter(edi,func) }
}



var  chronometer:CountDownTimer ?=null

fun startTimerForGettingData(delay:Long=Utils.DELAY_SERIAL,func: () -> Unit)
{
    chronometer?.cancel()
    chronometer = object : CountDownTimer(delay, 100)
    {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            func()
        }
    }.start()

}


fun check401Error( e: Throwable?,): Int {
    if (e is HttpException) {
        val code = e.code()
        if (code == Utils.ERROR_401) {
          return code
        }

    }
    return 0
}






