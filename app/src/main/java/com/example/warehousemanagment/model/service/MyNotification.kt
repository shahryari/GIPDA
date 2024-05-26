package com.example.warehousemanagment.model.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.warehousemanagment.R
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.log
import com.example.warehousemanagment.model.classes.logErr
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MyRepository
import com.example.warehousemanagment.model.data.MySharedPref
import com.example.warehousemanagment.ui.activity.MainActivity


class  MyNotification() : Service()
{
    private lateinit var token:String
    private val repository=MyRepository()
    lateinit var pref: MySharedPref

    val ACTION_STOP_SERVICE = "STOP"
    var FIRST_TIME=true

    val channelId = "ChannelId1"
    var countDownTimer:CountDownTimer?=null


    private var mInstance: MyNotification? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }





    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()
        setIntent(intent,"","")

        startTimer(repository,token,intent)


        return START_STICKY
    }

    private fun setIntent(intent: Intent,title:String,desc:String)
    {
        if (ACTION_STOP_SERVICE == intent.action) {
            Log.d("mag2851", "called to cancel service")
            stopSelf()
        }
        val stopSelf = Intent(this, MyNotification::class.java)

        stopSelf.action = ACTION_STOP_SERVICE

        val pStopSelf = PendingIntent.getService(
            this, 0, stopSelf,
            PendingIntent.FLAG_MUTABLE or
                    PendingIntent.FLAG_CANCEL_CURRENT
        )
        val intent1 = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent1, PendingIntent.FLAG_MUTABLE
                    or 0
        )
        var notification: Notification? = null


        notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic__787567_wrong_delete_remove_trash_minus_icon)
//                .addAction(R.drawable.ic__787567_wrong_delete_remove_trash_minus_icon,"Close", pStopSelf)
            .setSound(null)
            .setContentIntent(pendingIntent).build()
        startForeground(1, notification)
    }

    private fun startTimer(repository: MyRepository, token: String,intent: Intent) {
        if (FIRST_TIME==true)
        {
            repository.notif(pref.getDomain(),token).subscribe(
                {
                    log("notif",it.toString())
                    val model=it.get(0)
                    val sb= getBuiltString("you have:",model.pickingCount.toString()
                            +" "+"Picking"+",",
                        model.putawayCount.toString()
                                +" "+"Putaway"+",",
                        model.receivingCount.toString()
                                +" "+"Receiving"+",",
                        model.shippingCount.toString()
                                +" "+"Shipping"+",",
                        model.transferCount.toString()
                                +" "+"TransferCount"+"",
                    )
                    setIntent(intent =intent , title =sb,desc="")
                },{

                    logErr("notif",it.toString())
                }).let {  }
            FIRST_TIME=false
        }
        countDownTimer?.cancel()
        countDownTimer = object :
            CountDownTimer(pref.getServicePriod()*60*1000L, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish()
            {
                repository.notif(pref.getDomain(),token).subscribe(
                    {
                        log("notif",it.toString())
                        val model=it.get(0)
                        val sb= getBuiltString("you have:",model.pickingCount.toString()
                        +" "+"Picking"+",",
                            model.putawayCount.toString()
                                    +" "+"Putaway"+",",
                            model.receivingCount.toString()
                                    +" "+"Receiving"+",",
                            model.shippingCount.toString()
                                    +" "+"Shipping"+",",
                            model.transferCount.toString()
                                    +" "+"TransferCount"+"",
                            )
                        setIntent(intent =intent , title =sb,desc="")
                    },{

                        logErr("notif",it.toString())
                    }).let {  }


                startTimer(repository,token,intent)

            }

        }.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notif = NotificationChannel(channelId, "foregroundNotification", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notif)
        }
    }


    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        mInstance = null
        super.onDestroy()
    }


    override fun onCreate() {
        mInstance = this
        pref=MySharedPref(this)
        token=pref.getTokenGlcTest()
    }


}


