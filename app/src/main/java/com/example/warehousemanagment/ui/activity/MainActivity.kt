package com.example.warehousemanagment.ui.activity

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.currencykotlin.model.di.component.ActivityComponent
import com.example.kotlin_wallet.ui.base.BaseActivity
import com.example.warehousemanagment.BuildConfig
import com.example.warehousemanagment.R
import com.example.warehousemanagment.databinding.ActivityMainBinding
import com.example.warehousemanagment.databinding.DialogBackPressBinding
import com.example.warehousemanagment.databinding.DialogSheetUpdateBinding
import com.example.warehousemanagment.model.classes.createAlertDialog
import com.example.warehousemanagment.model.classes.dismissSheet
import com.example.warehousemanagment.model.classes.expandOrDecrease
import com.example.warehousemanagment.model.classes.getBuiltString
import com.example.warehousemanagment.model.classes.getDimen
import com.example.warehousemanagment.model.classes.getSpanTv
import com.example.warehousemanagment.model.classes.hideKeyboard
import com.example.warehousemanagment.model.classes.startTimerForGettingData
import com.example.warehousemanagment.model.constants.Utils
import com.example.warehousemanagment.model.data.MySharedPref
import com.example.warehousemanagment.model.models.VersionInfoModel
import com.example.warehousemanagment.model.service.MyNotification
import com.example.warehousemanagment.ui.dialog.SheetUpdate
import com.example.warehousemanagment.viewmodel.MainViewModel


class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>()
{
    var subGeneralHeight = 0
    var subReportHeight = 0
    var subRecieveHeight = 0
    var subShippingHeight = 0
    var subTransferHeight = 0
    var subSerialHeight = 0

    var subGeneralExpanding = true
    var subReportExapnding = true
    var subReceiveExpanding = true
    var subShippingExpanding = true
    var subTransferExpanding = true
    var subSerialExpanding = true

    val GENERAL_ITEMS = 7.0f
    val REPORT_ITEMS = 3.0f
    val TRANSFER_ITEMS = 4.0f
    val SHIPPING_ITEMS = 4.0f
    val RECEIVING_ITEMS = 3.0f
    val SERIAL_ITEMS = 5.0f


    lateinit var pref:MySharedPref



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)



        startTimerForGettingData(delay = pref.getServicePriod()*60*1000L)
        {
            viewModel.notif(pref.getDomain(),pref.getTokenGlcTest())
            {model->
                val sb= getBuiltString("you have:",model.pickingCount.toString()
                        +" "+"Picking"+",",
                    model.putawayCount.toString()
                            +" "+"Putaway"+",",
                    model.receivingCount.toString()
                            +" "+"Receiving"+",",
                    model.shippingCount.toString()
                            +" "+"Shipping"+",",
                    model.transferCount.toString()
                            +" "+"TransferCount"+"",)
                startNotif("",sb)
            }
        }

        viewModel.getUpdateInfo().observe(this){
            if (it.version > BuildConfig.VERSION_CODE) {
                showUpdateSheetDialog(it)
            }
        }




        startNavigation()
        b.rel1.visibility = View.GONE



        b.backImg.setOnClickListener {
            onBackClick()
        }

        b.drawerLayout.profileName.text = pref.getCurrentUser()
        b.drawerLayout.wareHouseName.text = pref.getWarehouseName()
        b.drawerLayout.relSetting.setOnClickListener {
            goToFragment(R.id.settingFragment)
        }

        b.drawerLayout.relReceive.setOnClickListener {
            goToFragment(R.id.receivingFragment)
        }
        b.drawerLayout.relPutaway.setOnClickListener {
            goToFragment(R.id.putawayFragment)
        }
        b.drawerLayout.relSerialPutawayAssign.setOnClickListener {
            goToFragment(R.id.serialPutawayAssignFragment)
        }
        b.drawerLayout.relSerialPutaway.setOnClickListener {
            goToFragment(R.id.serialPutawayFragment)
        }
        b.drawerLayout.relPicking.setOnClickListener {
            goToFragment(R.id.pickingFragment)
        }
        b.drawerLayout.relSerialPicking.setOnClickListener {
            goToFragment(R.id.serialPickingListFragment)
        }
        b.drawerLayout.relShipping.setOnClickListener {
            goToFragment(R.id.shippingFragment)
        }

        b.drawerLayout.relShippingSerial.setOnClickListener {
            goToFragment(R.id.serialShippingFragment)
        }

        b.drawerLayout.relCheckTruck.setOnClickListener {
            goToFragment(R.id.checkTruckFragment)
        }


        b.drawerLayout.relCanselShipping.setOnClickListener {
            goToFragment(R.id.canselShippingFragment)
        }

        b.drawerLayout.relDockAssign.setOnClickListener {
            goToFragment(R.id.dockAssignFragment)
        }

        b.drawerLayout.linLocationInventory.setOnClickListener {
            goToFragment(R.id.locationInventoryReportFragment)
        }
        b.drawerLayout.linSerialInventory.setOnClickListener {
            goToFragment(R.id.inventoryBySerialProductFragment)
        }
        b.drawerLayout.linPutPick.setOnClickListener {
            goToFragment(R.id.pickputDailyReportFragment)
        }
        b.drawerLayout.linOfflineScanSerial.setOnClickListener {
            goToFragment(R.id.offlineSerialFragment)
        }
        b.drawerLayout.linProductWithoutMaster.setOnClickListener {
            goToFragment(R.id.productWithoutMasterFragment)
        }
        b.drawerLayout.linWaitForLoading.setOnClickListener {
            goToFragment(R.id.waitForLoadingFragment)
        }

        b.drawerLayout.linStockTake.setOnClickListener {
            goToFragment(R.id.stockTakeFragment)
        }
        b.drawerLayout.serials.setOnClickListener {
            goToFragment(R.id.insertSerialFragment)
        }

        b.drawerLayout.linCargoDetail.setOnClickListener {
            goToFragment(R.id.cargoFragment)
        }

        b.drawerLayout.linMyCargo.setOnClickListener {
            goToFragment(R.id.myCargoFragment)
        }


        b.drawerLayout.relLogout.setOnClickListener {
            pref.saveTokenGlcTest("")
            goToFragment(R.id.loginFragment)
        }
        b.drawerLayout.linTrackingSerial.setOnClickListener {
            goToFragment(R.id.trackingFragment)
        }
        b.drawerLayout.linLocationTransfer.setOnClickListener {
            goToFragment(R.id.manualLocationTransferFragment)
        }
        b.drawerLayout.linSerialLocationTransfer.setOnClickListener {
            goToFragment(R.id.serialLocationTransferFragment)
        }
        b.drawerLayout.linTransferTask.setOnClickListener {
            goToFragment(R.id.locationTransferTaskFragment)
        }
        b.drawerLayout.linInventoryModify.setOnClickListener {
            goToFragment(R.id.inventoryModifiedTaskFragment)
        }
        b.drawerLayout.linRework.setOnClickListener {
            goToFragment(R.id.reworkFragment)
        }
        b.drawerLayout.lineDocks.setOnClickListener {
            goToFragment(R.id.dockFragment)
        }

        b.drawerLayout.serialExpand.setOnClickListener {
            setHeightOfSerial()

            vanishOtherExpanded(
                b.drawerLayout.linGeneral,
                b.drawerLayout.generalExpand, b.drawerLayout.linReport,
                b.drawerLayout.reportExpand, b.drawerLayout.linShipping,
                b.drawerLayout.shippingExpand,
                b.drawerLayout.linTransfer,
                b.drawerLayout.transferExpand,
                b.drawerLayout.linReceiving,
                b.drawerLayout.receiveExapnd
            )

            expandOrDecrease(
                subSerialHeight,
                subSerialExpanding,
                b.drawerLayout.linSerial,
                Utils.EXPAND_DURATION,
                b.drawerLayout.serialExpand
            )
            subSerialExpanding = !subSerialExpanding
        }


        b.drawerLayout.transferExpand.setOnClickListener()
        {
            setHeightOfTransfer()

            vanishOtherExpanded(
                b.drawerLayout.linGeneral,
                b.drawerLayout.generalExpand, b.drawerLayout.linReport,
                b.drawerLayout.reportExpand, b.drawerLayout.linShipping,
                b.drawerLayout.shippingExpand,
                b.drawerLayout.linReceiving,
                b.drawerLayout.receiveExapnd,
                b.drawerLayout.linSerial,
                b.drawerLayout.serialExpand
            )

            expandOrDecrease(
                subTransferHeight,
                subTransferExpanding,
                b.drawerLayout.linTransfer,
                Utils.EXPAND_DURATION,
                b.drawerLayout.transferExpand
            )
            subTransferExpanding = !subTransferExpanding
//
        }

        b.drawerLayout.receiveExapnd.setOnClickListener()
        {
            setHeightOfReceive()
            vanishOtherExpanded(
                b.drawerLayout.linGeneral,
                b.drawerLayout.generalExpand, b.drawerLayout.linReport,
                b.drawerLayout.reportExpand, b.drawerLayout.linShipping,
                b.drawerLayout.shippingExpand,
                b.drawerLayout.linTransfer,
                b.drawerLayout.transferExpand,
                b.drawerLayout.linSerial,
                b.drawerLayout.serialExpand
            )

            expandOrDecrease(
                subRecieveHeight,
                subReceiveExpanding,
                b.drawerLayout.linReceiving,
                Utils.EXPAND_DURATION,
                b.drawerLayout.receiveExapnd
            )
            subReceiveExpanding = !subReceiveExpanding


        }
        b.drawerLayout.shippingExpand.setOnClickListener()
        {
            setHeightOfShipping()
            vanishOtherExpanded(
                b.drawerLayout.linGeneral,
                b.drawerLayout.generalExpand, b.drawerLayout.linReport,
                b.drawerLayout.reportExpand, b.drawerLayout.linReceiving,
                b.drawerLayout.receiveExapnd,
                b.drawerLayout.linTransfer,
                b.drawerLayout.transferExpand,

                b.drawerLayout.linSerial,
                b.drawerLayout.serialExpand
            )

            expandOrDecrease(
                subShippingHeight,
                subShippingExpanding,
                b.drawerLayout.linShipping,
                Utils.EXPAND_DURATION,
                b.drawerLayout.shippingExpand
            )
            subShippingExpanding = !subShippingExpanding

        }



        b.drawerLayout.reportExpand.setOnClickListener()
        {
            setHeightOfReport()
            vanishOtherExpanded(
                b.drawerLayout.linGeneral,
                b.drawerLayout.generalExpand, b.drawerLayout.linReceiving,
                b.drawerLayout.receiveExapnd, b.drawerLayout.linShipping,
                b.drawerLayout.shippingExpand,
                b.drawerLayout.linTransfer,
                b.drawerLayout.transferExpand,
                b.drawerLayout.linSerial,
                b.drawerLayout.serialExpand
            )

            expandOrDecrease(
                subReportHeight,
                subReportExapnding,
                b.drawerLayout.linReport,
                Utils.EXPAND_DURATION,
                b.drawerLayout.reportExpand
            )
            subReportExapnding = !subReportExapnding

        }
        b.drawerLayout.generalExpand.setOnClickListener()
        {
            setHeightOfGeneral()
            vanishOtherExpanded(
                b.drawerLayout.linReceiving,
                b.drawerLayout.receiveExapnd, b.drawerLayout.linReport,
                b.drawerLayout.reportExpand, b.drawerLayout.linShipping,
                b.drawerLayout.shippingExpand,
                b.drawerLayout.linTransfer,
                b.drawerLayout.transferExpand,

                b.drawerLayout.linSerial,
                b.drawerLayout.serialExpand
            )

            expandOrDecrease(
                subGeneralHeight, subGeneralExpanding,
                b.drawerLayout.linGeneral, Utils.EXPAND_DURATION, b.drawerLayout.generalExpand
            )
            subGeneralExpanding = !subGeneralExpanding

        }




    }

    private fun showUpdateSheetDialog(versionInfoModel: VersionInfoModel) {
        val sheet = SheetUpdate(object :SheetUpdate.OnClickListener {
            override fun init(binding: DialogSheetUpdateBinding) {
                binding.update.visibility = View.VISIBLE
                binding.close.setOnClickListener {
                    finish()
                }
            }

            override fun onUpdate() {
                val intent = Intent(Intent.ACTION_VIEW,versionInfoModel.downloadUrl.toUri())
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(intent)
            }
        })
        sheet.show(supportFragmentManager,"")
    }
    private fun startNotif(title:String,desc:String)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                "id", "id", NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)


        }

        val contentIntent = PendingIntent.getActivity(this, 0,
            Intent(this, MainActivity::class.java), PendingIntent.FLAG_MUTABLE
        )
        val builder = NotificationCompat.Builder(this, "id")

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(alarmSound)
        builder.setContentTitle(title)
        builder.setContentText(desc)
        builder.setContentIntent(contentIntent)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setAutoCancel(true)

        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(1, builder.build())



    }


    private fun startService()
    {

        val myNotification = MyNotification()
        val intent = Intent(this, myNotification::class.java)
//        stopService(intent)

        if (!isMyServiceRunning(myNotification::class.java)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//             Context.startForegroundService(intent)
              startForegroundService( intent)
            } else startService(intent)
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }



    private fun vanishOtherExpanded(
        lin1: LinearLayout, lin1Expander: LinearLayout,
        lin2: LinearLayout, lin2Expander: LinearLayout,
        lin3: LinearLayout, lin3Expander: LinearLayout,
        lin4: LinearLayout, lin4Expander: LinearLayout,
        lin5: LinearLayout, lin5Expander: LinearLayout
    ) {
        subReceiveExpanding = true
        subShippingExpanding = true
        subGeneralExpanding = true
        subReportExapnding = true
        subTransferExpanding = true
        subSerialExpanding = true
        expandOrDecrease(0, false, lin1, Utils.EXPAND_DURATION, lin1Expander)
        expandOrDecrease(0, false, lin2, Utils.EXPAND_DURATION, lin2Expander)
        expandOrDecrease(0, false, lin3, Utils.EXPAND_DURATION, lin3Expander)
        expandOrDecrease(0, false, lin4, Utils.EXPAND_DURATION, lin4Expander)
        expandOrDecrease(0, false, lin5, Utils.EXPAND_DURATION, lin5Expander)

    }

    private fun goToFragment(id: Int) {
        findNavController(R.id.navHostFragment).navigate(id)
        closeDrawer()
    }

    private fun startNavigation() {
        b.nav.setNavigationItemSelectedListener {
            val id = it.itemId

            b.drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener false
        }
        b.menuImg.setOnClickListener {
            hideKeyboard(this)
            b.drawer.openDrawer(GravityCompat.START)
        }

        setVersionName()

//        setProfileName()

    }

    private fun setVersionName() {
        val sb = StringBuilder()
        sb.append(getString(R.string.version))
        sb.append("${BuildConfig.VERSION_CODE}.${BuildConfig.VERSION_NAME}")
        b.drawerLayout.version.text = sb.toString()
    }

    private fun setProfileName() {
        val profileName = b.drawerLayout.profileName.text.toString()
        val end = profileName.indexOf(" ")
        b.drawerLayout.profileName.text = getSpanTv(
            0, end, ContextCompat.getColor(
                this,
                R.color.mainYellow
            ), profileName
        )
    }


    //------------------------------------------------------
    override fun onBackPressed() {
        onBackClick()
    }

    private fun onBackClick() {
        dismissSheet()
        if (b.drawer.isDrawerOpen(GravityCompat.START)) {
            b.drawer.closeDrawer(GravityCompat.START)
        } else {
            val navigationController = findNavController(R.id.navHostFragment)
            if (navigationController.currentDestination?.id == R.id.desktopFragment) {
                showFinishDialog()
            } else if (navigationController.currentDestination?.id == R.id.loginFragment) {
                showFinishDialog()
            } else {
                super.onBackPressed()
            }
        }

    }

    private fun showFinishDialog() {
        val dialogBinding = DialogBackPressBinding.inflate(LayoutInflater.from(this))
        val finishDialog = createAlertDialog(
            dialogBinding,
            R.drawable.shape_background_rect_border_gray_solid_white, this
        )
        dialogBinding.no.setOnClickListener {
            finishDialog.dismiss()
        }
        dialogBinding.yes.setOnClickListener {
            finish()
        }
    }


    private fun closeDrawer() {
        if (b.drawer.isDrawerOpen(GravityCompat.START)) {
            b.drawer.closeDrawer(GravityCompat.START)
        }
    }

    override fun init()
    {
        pref=MySharedPref(this)

        setHeightOfReport()


        setHeightOfGeneral()


        setHeightOfReceive()

        setHeightOfShipping()


        setHeightOfTransfer()

        setHeightOfSerial()



        b.drawerLayout.linReport.layoutParams.height=0
        b.drawerLayout.linGeneral.layoutParams.height=0
        b.drawerLayout.linReceiving.layoutParams.height=0
        b.drawerLayout.linShipping.layoutParams.height=0
        b.drawerLayout.linTransfer.layoutParams.height=0


        b.drawerLayout.linSerial.layoutParams.height=0



    }

    private fun setHeightOfSerial() {
        subSerialHeight = (getDimen(this).height * Utils.DRAWER_SUB_SERIAL_RATIO).toInt()
        subSerialHeight = (subSerialHeight -
                (pref.getPermissionBy(Utils.RECEIVING_CAT) / SERIAL_ITEMS) * subSerialHeight).toInt()
    }

    private fun setHeightOfTransfer() {
        subTransferHeight = (getDimen(this).height * Utils.DRAWER_SUB_TRANSFER_RATIO).toInt()
        subTransferHeight = (subTransferHeight -
                (pref.getPermissionBy(Utils.TRANSFER_CAT) / TRANSFER_ITEMS) * subTransferHeight).toInt()
    }

    private fun setHeightOfShipping() {
        subShippingHeight = (getDimen(this).height * Utils.DRAWER_SUB_SHIPPING_RATIO).toInt()
        subShippingHeight = (subShippingHeight -
                (pref.getPermissionBy(Utils.SHIPPING_CAT) / SHIPPING_ITEMS) * subShippingHeight).toInt()
    }

    private fun setHeightOfReceive() {
        subRecieveHeight = (getDimen(this).height * Utils.DRAWER_SUB_RECEIVE_RATIO).toInt()
        subRecieveHeight = (subRecieveHeight -
                (pref.getPermissionBy(Utils.RECEIVING_CAT) / RECEIVING_ITEMS) * subRecieveHeight).toInt()
    }

    private fun setHeightOfGeneral() {
        subGeneralHeight = (getDimen(this).height * Utils.DRAWER_SUB_GENERAL_RATIO).toInt()
        subGeneralHeight = (subGeneralHeight -
                (pref.getPermissionBy(Utils.GENERAL_CAT) / GENERAL_ITEMS) * subGeneralHeight).toInt()
    }

    private fun setHeightOfReport() {
        subReportHeight = (getDimen(this).height * Utils.DRAWER_SUB_REPORT_RATIO).toInt()
        subReportHeight = (subReportHeight -
                (pref.getPermissionBy(Utils.REPORT_CAT) / REPORT_ITEMS) * subReportHeight).toInt()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun setupComponent(component: ActivityComponent) {
        component.inject(this)
    }


    override fun onResume() {
        super.onResume()
        viewModel.getUpdateInfo(pref.getDomain(),pref.getTokenGlcTest())
    }

    override fun attachBaseContext(newBase: Context) {
        val overrideConfiguration = Configuration(
            newBase.resources.configuration
        ).apply { fontScale = 1.0f }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            super.attachBaseContext(
                newBase.createConfigurationContext(overrideConfiguration)
            )
        }
    }

}