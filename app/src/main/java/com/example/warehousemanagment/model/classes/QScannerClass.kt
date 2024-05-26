package com.example.warehousemanagment.model.classes

import android.content.DialogInterface
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.constants.Utils.Companion.QSCANNER_PERMISSIONS
import com.example.warehousemanagment.ui.activity.MainActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QScannerClass : AppCompatActivity(),ZXingScannerView.ResultHandler
{
    private  var scannerView:ZXingScannerView ?=null
    private val camera= Camera.CameraInfo.CAMERA_FACING_BACK

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?)
    {
        super.onCreate(savedInstanceState, persistentState)
        scannerView= ZXingScannerView(this)
        setContentView(scannerView)
        initRequestPermission(this, QSCANNER_PERMISSIONS,Utils.REQUEST_CAMERA)


    }

    override fun handleResult(result: Result)
    {
        val rawResult=result.text
        val builder=AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.scanResults))
        builder.setPositiveButton(getString(R.string.ok),object :DialogInterface.OnClickListener
        {
            override fun onClick(dialog: DialogInterface, which: Int)
            {
                scannerView?.resumeCameraPreview(this@QScannerClass)

                val intent=Intent(this@QScannerClass,MainActivity::class.java)
                intent.putExtra(Utils.RESULT_SCANNER,rawResult)
                setResult(Utils.REQUEST_CAMERA,intent)
                finish()
            }
        })
        builder.setNegativeButton(getString(R.string.cancel),
            object :DialogInterface.OnClickListener
            {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                     onDestroy()
                }
            })

        builder.setMessage(result.text)
        val dialog=builder.create()
        dialog.show()

    }

    override fun onResume()
    {
        super.onResume()
        if (hasPermissions(this,*QSCANNER_PERMISSIONS)==true)
        {
            if (scannerView== null)
            {
                scannerView= ZXingScannerView(this)
                setContentView(scannerView)
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()


        }else initRequestPermission(this,QSCANNER_PERMISSIONS,Utils.REQUEST_CAMERA)

    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
//        scannerView=null
    }

}