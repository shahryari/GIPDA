package com.example.currencykotlin.model.di.component

import com.example.currencykotlin.model.di.module.FragmentModule
import com.example.warehousemanagment.ui.fragment.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FragmentModule::class])
@Singleton
interface FragmentComponent
{
    fun inject(fragment:LoginFragment)
    fun inject(fragment:DesktopFragment)
    fun inject(fragment:ReceivingFragment)
    fun inject(fragment: ReceivingDetailFragment)
    fun inject(fragment: PutawayFragment)
    fun inject(fragment: PutawayDetailFragment)
    fun inject(fragment: PickingFragment)
    fun inject(fragment: CheckTruckFragment)
    fun inject(fragment: ShippingFragment)
    fun inject(fragment: ShippingDetailFragment)
    fun inject(fragment: CanselShippingFragment)
    fun inject(fragment: CanselShippingDetailFragment)
    fun inject(fragment: ManualLocationTransferFragment)
    fun inject(fragment: TransferTaskFragment)
    fun inject(fragment: InventoryModifiedTaskFragment)
    fun inject(serialFragment: InventoryBySerialFragment)
    fun inject(fragment: PickputDailyReportFragment)
    fun inject(fragment: InsertSerialFragment)
    fun inject(fragment: OfflineSerialFragment)
    fun inject(fragment: TrackingFragment)
    fun inject(fragment:ProductWithoutMasterFragment)
    fun inject(fragment:WaitForLoadingFragment)
    fun inject(fragment:LocationInventoryReportFragment)
    fun inject(fragment:SettingFragment)
    fun inject(fragment:ReworkFragment)
    fun inject(fragment:InventoryBySerialProductFragment)
    fun inject(fragment:CargoFragment)
    fun inject(fragment: CargoDetailFragment)
    fun inject(fragment: MyCargoFragment)
    fun inject(fragment:MyCargoDetailFragment)
    fun inject(fragment:StockTakeFragment)
    fun inject(fragment:StockTakeLocationFragment)
    fun inject(fragment:PickingDetailFragment)




}