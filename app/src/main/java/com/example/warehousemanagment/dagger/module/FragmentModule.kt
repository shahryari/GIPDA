package com.example.currencykotlin.model.di.module

import android.app.Application
import android.content.Context
import com.example.warehousemanagment.model.data.DatabaseHelper
import com.example.warehousemanagment.model.data.MySharedPref
import com.example.warehousemanagment.viewmodel.InventoryModifiedTaskViewModel
import com.example.warehousemanagment.viewmodel.*
import com.test.StockTakingLocationModel
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
}