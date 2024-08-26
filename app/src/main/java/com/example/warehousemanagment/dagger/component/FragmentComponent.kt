package com.example.warehousemanagment.dagger.component

import com.example.warehousemanagment.dagger.module.FragmentModule
import com.example.warehousemanagment.ui.fragment.CanselShippingDetailFragment
import com.example.warehousemanagment.ui.fragment.CanselShippingFragment
import com.example.warehousemanagment.ui.fragment.CargoDetailFragment
import com.example.warehousemanagment.ui.fragment.CargoFragment
import com.example.warehousemanagment.ui.fragment.CheckTruckFragment
import com.example.warehousemanagment.ui.fragment.DesktopFragment
import com.example.warehousemanagment.ui.fragment.DockFragment
import com.example.warehousemanagment.ui.fragment.InsertSerialFragment
import com.example.warehousemanagment.ui.fragment.InventoryBySerialFragment
import com.example.warehousemanagment.ui.fragment.InventoryBySerialProductFragment
import com.example.warehousemanagment.ui.fragment.InventoryModifiedTaskFragment
import com.example.warehousemanagment.ui.fragment.LocationInventoryReportFragment
import com.example.warehousemanagment.ui.fragment.LoginFragment
import com.example.warehousemanagment.ui.fragment.ManualLocationTransferFragment
import com.example.warehousemanagment.ui.fragment.MyCargoDetailFragment
import com.example.warehousemanagment.ui.fragment.MyCargoFragment
import com.example.warehousemanagment.ui.fragment.OfflineSerialFragment
import com.example.warehousemanagment.ui.fragment.PickingDetailFragment
import com.example.warehousemanagment.ui.fragment.PickingFragment
import com.example.warehousemanagment.ui.fragment.PickputDailyReportFragment
import com.example.warehousemanagment.ui.fragment.ProductWithoutMasterFragment
import com.example.warehousemanagment.ui.fragment.PutawayDetailFragment
import com.example.warehousemanagment.ui.fragment.PutawayFragment
import com.example.warehousemanagment.ui.fragment.ReceivingDetailFragment
import com.example.warehousemanagment.ui.fragment.ReceivingFragment
import com.example.warehousemanagment.ui.fragment.ReworkFragment
import com.example.warehousemanagment.ui.fragment.SettingFragment
import com.example.warehousemanagment.ui.fragment.ShippingDetailFragment
import com.example.warehousemanagment.ui.fragment.ShippingFragment
import com.example.warehousemanagment.ui.fragment.StockTakeFragment
import com.example.warehousemanagment.ui.fragment.StockTakeLocationFragment
import com.example.warehousemanagment.ui.fragment.TrackingFragment
import com.example.warehousemanagment.ui.fragment.TransferTaskFragment
import com.example.warehousemanagment.ui.fragment.WaitForLoadingDetailFragment
import com.example.warehousemanagment.ui.fragment.WaitForLoadingFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FragmentModule::class])
@Singleton
interface FragmentComponent
{
    fun inject(fragment:LoginFragment)
    fun inject(fragment:DesktopFragment)

    fun inject(fragment: DockFragment)

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
    fun inject(fragment:WaitForLoadingDetailFragment)



}