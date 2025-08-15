package com.example.warehousemanagment.dagger.module

import android.app.Application
import android.content.Context
import com.example.warehousemanagment.model.data.DatabaseHelper
import com.example.warehousemanagment.model.data.MySharedPref
import com.example.warehousemanagment.viewmodel.CanselShippingDetailViewModel
import com.example.warehousemanagment.viewmodel.CanselShippingViewModel
import com.example.warehousemanagment.viewmodel.CargoDetailViewModel
import com.example.warehousemanagment.viewmodel.CargoViewModel
import com.example.warehousemanagment.viewmodel.CheckTruckViewModel
import com.example.warehousemanagment.viewmodel.DesktopViewModel
import com.example.warehousemanagment.viewmodel.DockAssignViewModel
import com.example.warehousemanagment.viewmodel.DockViewModel
import com.example.warehousemanagment.viewmodel.InsertSerialViewModel
import com.example.warehousemanagment.viewmodel.InventoryByViewModel
import com.example.warehousemanagment.viewmodel.InventoryModifiedTaskViewModel
import com.example.warehousemanagment.viewmodel.InventorySerialProductViewModel
import com.example.warehousemanagment.viewmodel.InventorySerialViewModel
import com.example.warehousemanagment.viewmodel.LocatoinInventoryReportViewModel
import com.example.warehousemanagment.viewmodel.LoginViewModel
import com.example.warehousemanagment.viewmodel.ManualLocationTransViewModel
import com.example.warehousemanagment.viewmodel.MyCargoDetailViewModel
import com.example.warehousemanagment.viewmodel.MyCargoViewModel
import com.example.warehousemanagment.viewmodel.OfflineSerialViewModel
import com.example.warehousemanagment.viewmodel.PickingDetailListViewModel
import com.example.warehousemanagment.viewmodel.PickingListViewModel
import com.example.warehousemanagment.viewmodel.PickputDailyReportViewModel
import com.example.warehousemanagment.viewmodel.ProductWithoutMasterViewModel
import com.example.warehousemanagment.viewmodel.PutAwayDetailViewModel
import com.example.warehousemanagment.viewmodel.PutAwayViewModel
import com.example.warehousemanagment.viewmodel.ReceivingDetailViewModel
import com.example.warehousemanagment.viewmodel.ReceivingViewModel
import com.example.warehousemanagment.viewmodel.ReworkViewModel
import com.example.warehousemanagment.viewmodel.SerialPickingDetailViewModel
import com.example.warehousemanagment.viewmodel.SerialPickingListViewModel
import com.example.warehousemanagment.viewmodel.SerialPickingScanViewModel
import com.example.warehousemanagment.viewmodel.SerialPutawayAssignViewModel
import com.example.warehousemanagment.viewmodel.SerialPutawayDetailLocationViewModel
import com.example.warehousemanagment.viewmodel.SerialPutawayDetailViewModel
import com.example.warehousemanagment.viewmodel.SerialPutawayViewModel
import com.example.warehousemanagment.viewmodel.SerialShippingDetailViewModel
import com.example.warehousemanagment.viewmodel.SerialShippingViewModel
import com.example.warehousemanagment.viewmodel.SerialTransferViewModel
import com.example.warehousemanagment.viewmodel.SettingViewModel
import com.example.warehousemanagment.viewmodel.ShippingDetailViewModel
import com.example.warehousemanagment.viewmodel.ShippingViewModel
import com.example.warehousemanagment.viewmodel.StockTakeLocationViewModel
import com.example.warehousemanagment.viewmodel.StockTakeViewModel
import com.example.warehousemanagment.viewmodel.StockTurnReportViewModel
import com.example.warehousemanagment.viewmodel.TrackingViewModel
import com.example.warehousemanagment.viewmodel.TransferTaskViewModel
import com.example.warehousemanagment.viewmodel.WaitForLoadingDetailViewModel
import com.example.warehousemanagment.viewmodel.WaitForLoadingViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule()
{
    lateinit var application:Application
    lateinit var context: Context
    constructor(application: Application,context: Context) : this() {
        this.application=application
        this.context=context
    }

    @Singleton
    @Provides
    fun myPref(): MySharedPref {
        return MySharedPref(application)
    }

    @Singleton
    @Provides
    fun sqliteDatabase(): DatabaseHelper {
        return DatabaseHelper(application)
    }

    @Singleton
    @Provides
    fun loginViewModel(): LoginViewModel {
        return LoginViewModel(application,context)
    }
    @Singleton
    @Provides
    fun desktopViewModel(): DesktopViewModel {
        return DesktopViewModel(application,context)
    }
    @Singleton
    @Provides
    fun receivingViewModel(): ReceivingViewModel {
        return ReceivingViewModel(application,context)
    }

    @Singleton
    @Provides
    fun PutAwayViewModel(): PutAwayViewModel {
        return PutAwayViewModel(application,context)
    }

    @Singleton
    @Provides
    fun PutAwayDetailViewModel(): PutAwayDetailViewModel {
        return PutAwayDetailViewModel(application,context)
    }


    @Singleton
    @Provides
    fun PickingListViewModel(): PickingListViewModel {
        return PickingListViewModel(application,context)
    }

    @Singleton
    @Provides
    fun receivingDetailViewModel(): ReceivingDetailViewModel {
        return ReceivingDetailViewModel(application,context)
    }


    @Singleton
    @Provides
    fun shippingViewModel(): ShippingViewModel {
        return ShippingViewModel(application,context)
    }


    @Singleton
    @Provides
    fun CheckTruckViewModel(): CheckTruckViewModel {
        return CheckTruckViewModel(application,context)
    }

    @Singleton
    @Provides
    fun ShippingDetailViewModel(): ShippingDetailViewModel {
        return ShippingDetailViewModel(application,context)
    }

    @Singleton
    @Provides
    fun CanselShippingViewModel(): CanselShippingViewModel {
        return CanselShippingViewModel(application,context)
    }

    @Singleton
    @Provides
    fun CanselShippingDetailViewModel(): CanselShippingDetailViewModel {
        return CanselShippingDetailViewModel(application,context)
    }
    @Singleton
    @Provides
    fun ManualLocationTransViewModel(): ManualLocationTransViewModel {
        return ManualLocationTransViewModel(application,context)
    }

    @Singleton
    @Provides
    fun LocationTransferTaskViewModel(): TransferTaskViewModel {
        return TransferTaskViewModel(application,context)
    }

    @Singleton
    @Provides
    fun InventoryModifiedTaskViewModel(): InventoryModifiedTaskViewModel {
        return InventoryModifiedTaskViewModel(application,context)
    }


    @Singleton
    @Provides
    fun InventoryByViewModel(): InventoryByViewModel {
        return InventoryByViewModel(application,context)
    }

    @Singleton
    @Provides
    fun PickputDailyReportViewModel(): PickputDailyReportViewModel {
        return PickputDailyReportViewModel(application,context)
    }
    @Singleton
    @Provides
    fun InsertSerialViewModel(): InsertSerialViewModel {
        return InsertSerialViewModel(application,context)
    }
    @Singleton
    @Provides
    fun OfflineSerialViewModel(): OfflineSerialViewModel {
        return OfflineSerialViewModel(application,context)
    }
    @Singleton
    @Provides
    fun TrackingViewModel(): TrackingViewModel {
        return TrackingViewModel(application,context)
    }

    @Singleton
    @Provides
    fun ProductWithoutMasterViewModel(): ProductWithoutMasterViewModel {
        return ProductWithoutMasterViewModel(application,context)
    }

    @Singleton
    @Provides
    fun WaitForLoadingViewModel(): WaitForLoadingViewModel {
        return WaitForLoadingViewModel(application,context)
    }


    @Singleton
    @Provides
    fun LocatoinInventoryReportViewModel(): LocatoinInventoryReportViewModel {
        return LocatoinInventoryReportViewModel(application,context)
    }

    @Singleton
    @Provides
    fun SettingViewModel(): SettingViewModel {
        return SettingViewModel(application,context)
    }
    @Singleton
    @Provides
    fun InventorySerialViewModel(): InventorySerialViewModel {
        return InventorySerialViewModel(application,context)
    }

    @Singleton
    @Provides
    fun InventorySerialProductViewModel(): InventorySerialProductViewModel {
        return InventorySerialProductViewModel(application,context)
    }



    @Singleton
    @Provides
    fun ReworkViewModel(): ReworkViewModel {
        return ReworkViewModel(application,context)
    }



    @Singleton
    @Provides
    fun cargoViewModel(): CargoViewModel {
        return CargoViewModel(application,context)
    }


    @Singleton
    @Provides
    fun cargoDetailViewModel(): CargoDetailViewModel {
        return CargoDetailViewModel(application,context)
    }

    @Singleton
    @Provides
    fun myCargoViewModel(): MyCargoViewModel {
        return MyCargoViewModel(application,context)
    }


    @Singleton
    @Provides
    fun myCargoDetailViewModel(): MyCargoDetailViewModel {
        return MyCargoDetailViewModel(application,context)
    }


    @Singleton
    @Provides
    fun stockTakeViewModel(): StockTakeViewModel {
        return StockTakeViewModel(application,context)
    }

    @Singleton
    @Provides
    fun StockTakeLocationViewModel(): StockTakeLocationViewModel {
        return StockTakeLocationViewModel(application,context)
    }

    @Singleton
    @Provides
    fun PickingDetailViewModel(): PickingDetailListViewModel {
        return PickingDetailListViewModel(application,context)
    }

    @Singleton
    @Provides
    fun dockViewModel() : DockViewModel {
        return DockViewModel(application)
    }

    @Singleton
    @Provides
    fun dockDetailViewModel() : WaitForLoadingDetailViewModel {
        return WaitForLoadingDetailViewModel(application,context)
    }

    @Singleton
    @Provides
    fun serialPutawayAssignViewModel() : SerialPutawayAssignViewModel {
        return SerialPutawayAssignViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPutawayViewModel() : SerialPutawayViewModel {
        return SerialPutawayViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPutawayDetailViewModel() : SerialPutawayDetailViewModel {
        return SerialPutawayDetailViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPutawayDetailLocationViewModel() : SerialPutawayDetailLocationViewModel {
        return SerialPutawayDetailLocationViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPickingListViewModel() : SerialPickingListViewModel {
        return SerialPickingListViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPickingDetailViewModel() : SerialPickingDetailViewModel {
        return SerialPickingDetailViewModel(application)
    }

    @Singleton
    @Provides
    fun serialPickingScanViewModel() : SerialPickingScanViewModel {
        return SerialPickingScanViewModel(application)
    }

    @Singleton
    @Provides
    fun serialTransferViewModel() : SerialTransferViewModel {
        return SerialTransferViewModel(application)
    }

    @Singleton
    @Provides
    fun serialShippingViewModel() : SerialShippingViewModel {
        return SerialShippingViewModel(application,context)
    }

    @Singleton
    @Provides
    fun serialShippingDetailViewModel() : SerialShippingDetailViewModel {
        return SerialShippingDetailViewModel(application,context)
    }

    @Singleton
    @Provides
    fun dockAssignViewModel() : DockAssignViewModel {
        return DockAssignViewModel(application)
    }

    @Singleton
    @Provides
    fun stockTurnReportViewModel() : StockTurnReportViewModel {
        return StockTurnReportViewModel(application)
    }
}