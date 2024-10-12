package com.example.warehousemanagment.model.data

import PickingDetailModel
import com.example.warehousemanagment.model.models.LocationModel
import com.example.warehousemanagment.model.models.VersionInfoModel
import com.example.warehousemanagment.model.models.cargo_folder.DriverTaskDoneModel
import com.example.warehousemanagment.model.models.cargo_folder.SetShippingAddressColorModel
import com.example.warehousemanagment.model.models.cargo_folder.cargo.CargoModel
import com.example.warehousemanagment.model.models.cargo_folder.cargo.CargoRow
import com.example.warehousemanagment.model.models.cargo_folder.cargo_detail.CargoDetailModel
import com.example.warehousemanagment.model.models.check_truck.CheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.confirm.ConfirmCheckTruckModel
import com.example.warehousemanagment.model.models.check_truck.deny.DenyCheckTruckModel
import com.example.warehousemanagment.model.models.dock.DockModel
import com.example.warehousemanagment.model.models.dock.SetUseDockModel
import com.example.warehousemanagment.model.models.insert_serial.InsertSerialModel
import com.example.warehousemanagment.model.models.insert_serial.OwnerModel
import com.example.warehousemanagment.model.models.insert_serial.ProductModel
import com.example.warehousemanagment.model.models.insert_serial.WarehouseModel
import com.example.warehousemanagment.model.models.inventory.CompleteInventoryModifyModel
import com.example.warehousemanagment.model.models.inventory.InventoryVerifyWithoutComp
import com.example.warehousemanagment.model.models.inventory.inventory.InventoryModifedModel
import com.example.warehousemanagment.model.models.login.CatalogModel
import com.example.warehousemanagment.model.models.login.DashboardInfoModel
import com.example.warehousemanagment.model.models.login.current_user.CurrentUserModel
import com.example.warehousemanagment.model.models.login.login.LoginModel
import com.example.warehousemanagment.model.models.my_cargo.my_cargo.MyCargoModel
import com.example.warehousemanagment.model.models.my_cargo.my_cargo_detail.MyCargoDetailModel
import com.example.warehousemanagment.model.models.notif.NotificationModel
import com.example.warehousemanagment.model.models.picking.CompletePickingModel
import com.example.warehousemanagment.model.models.picking.GetPickingSerialsModel
import com.example.warehousemanagment.model.models.picking.PickingFinishSerialBaseModel
import com.example.warehousemanagment.model.models.picking.ScanPickingSerialModel
import com.example.warehousemanagment.model.models.picking.picking.PickingTruckModel
import com.example.warehousemanagment.model.models.putaway.complete.CompletePutawayModel
import com.example.warehousemanagment.model.models.putaway.serial_putaway.MySerailReceiptDetailModel
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptDetailLocationModel
import com.example.warehousemanagment.model.models.putaway.serial_putaway.ReceiptSerialModel
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialPutawayAssignModel
import com.example.warehousemanagment.model.models.putaway.serial_putaway.SerialReceiptOnPutawayModel
import com.example.warehousemanagment.model.models.putaway.truck.PutawayTruckModel
import com.example.warehousemanagment.model.models.putaway.truck_detail.PutawayTruckDetailModel
import com.example.warehousemanagment.model.models.receive.add_detail_serial.AddReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.receive.confirm.ConfirmReceiveDetailModel
import com.example.warehousemanagment.model.models.receive.count.ReceiveDetailCountModel
import com.example.warehousemanagment.model.models.receive.receiveDetail.ReceiveDetailModel
import com.example.warehousemanagment.model.models.receive.receiving.ReceivingModel
import com.example.warehousemanagment.model.models.receive.receiving_detail_serials.ReceivingDetailSerialModel
import com.example.warehousemanagment.model.models.receive.remove_serial.RemoveSerialModel
import com.example.warehousemanagment.model.models.report_inventory.LocationInventoryByProduct
import com.example.warehousemanagment.model.models.report_inventory.pickput.PickAndPutModel
import com.example.warehousemanagment.model.models.report_inventory.report_location.ReportLocationInventory
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory.SerialInventoryModel
import com.example.warehousemanagment.model.models.report_inventory.serial_inventory_product.SerialInvProductModel
import com.example.warehousemanagment.model.models.revoke.RevokeModel
import com.example.warehousemanagment.model.models.rework.ReworkModel
import com.example.warehousemanagment.model.models.shipping.AddShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.LoadingFinishModel
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.ShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.TruckLoadingRemoveModel
import com.example.warehousemanagment.model.models.shipping.customer.ColorModel
import com.example.warehousemanagment.model.models.shipping.customer.CustomerInShipping
import com.example.warehousemanagment.model.models.shipping.customer.CustomerModel
import com.example.warehousemanagment.model.models.shipping.detail.ShippingDetailModel
import com.example.warehousemanagment.model.models.shipping.left_dock.LeftDockModel
import com.example.warehousemanagment.model.models.shipping.shipping_truck.ShippingTruckModel
import com.example.warehousemanagment.model.models.stock.StockLocationInsertModel
import com.example.warehousemanagment.model.models.stock.StockLocationModel
import com.example.warehousemanagment.model.models.stock.StockTakingCountModel
import com.example.warehousemanagment.model.models.tracking.GetSerialInfoModel
import com.example.warehousemanagment.model.models.tracking.LabellingModel
import com.example.warehousemanagment.model.models.transfer_task.CompleteLocationTransfer
import com.example.warehousemanagment.model.models.transfer_task.DestinyLocationTransfer
import com.example.warehousemanagment.model.models.transfer_task.LocationTransferSubmit
import com.example.warehousemanagment.model.models.transfer_task.PalletModel
import com.example.warehousemanagment.model.models.transfer_task.location_transfer.LocationTransferTaskModel
import com.example.warehousemanagment.model.models.transfer_task.source_location.SourceLocationTransferModel
import com.example.warehousemanagment.model.models.wait_to_load.TruckLoadingAssignModel
import com.example.warehousemanagment.model.models.wait_to_load.wait_truck.WaitTruckLoadingModel
import com.example.warehousemanagment.model.models.without_master.ProductWithoutMasterModel
import com.example.warehousemanagment.model.models.without_master.UnitOfMeasureSubmitModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.test.StockTakeModel
import com.test.StockTakingLocationModel
import io.reactivex.Observable
import io.reactivex.Single


class MyRepository() :DataSource
{
    private val apiDataSource=ApiDataSource()

    override fun login(baseUrl:String,jsonObject: JsonObject): Single<LoginModel>
    {
        return apiDataSource.login(baseUrl,jsonObject)
    }

    override fun dashboardInfo(baseUrl:String,cookie: String): Observable<List<DashboardInfoModel>> {
        return apiDataSource.dashboardInfo(baseUrl,cookie)
    }

    override  fun getReceivingList(baseUrl:String,cookie:String, jsonObject: JsonObject,
                                   page:Int,
                                   rows:Int,
                                   sort:String,
                                   asc:String): Observable<ReceivingModel> {
        return apiDataSource.getReceivingList(baseUrl,cookie,jsonObject,page, rows, sort, asc)
    }

    override fun getCurrentUser(baseUrl:String,cookie: String): Observable<CurrentUserModel> {
        return apiDataSource.getCurrentUser(baseUrl,cookie)
    }

    override fun catalogList(baseUrl:String,cookie: String): Observable<List<CatalogModel>> {
        return apiDataSource.catalogList(baseUrl,cookie)
    }

    override fun getReceiveDetailList(baseUrl:String,jsonObject: JsonObject,
                                      page:Int,
                                      rows:Int,
                                      sort:String,
                                      asc:String, cookie: String):
            Observable<ReceiveDetailModel> {
        return apiDataSource.getReceiveDetailList(baseUrl,jsonObject,page, rows, sort, asc, cookie)
    }

    override fun getReceiveDetailSerialList(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Observable<List<ReceivingDetailSerialModel>> {
        return apiDataSource.getReceiveDetailSerialList(baseUrl,jsonObject, cookie)
    }

    override fun addReceiveDetailSerial(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Single<AddReceivingDetailSerialModel> {
        return apiDataSource.addReceiveDetailSerial(baseUrl,jsonObject,cookie)
    }

    override fun removeSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveSerialModel> {
        return apiDataSource.removeSerial(baseUrl,jsonObject,cookie)
    }

    override fun countReceiveDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ReceiveDetailCountModel> {
        return apiDataSource.countReceiveDetail(baseUrl,jsonObject,cookie)
    }

    override fun confirmReceiveDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ConfirmReceiveDetailModel> {
        return apiDataSource.confirmReceiveDetail(baseUrl,jsonObject, cookie)
    }

    override fun putawayTruckList(
        baseUrl:String,
        cookie: String,jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String
    ): Observable<PutawayTruckModel> {
        return apiDataSource.putawayTruckList(baseUrl,cookie,jsonObject, page, rows, sort, asc)
    }

    override fun putawayTruckDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
        cookie: String
    ): Observable<PutawayTruckDetailModel> {
        return apiDataSource.putawayTruckDetail(baseUrl,jsonObject,page, rows, sort, asc,cookie)
    }

    override fun completePutawayModel(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePutawayModel> {
        return apiDataSource.completePutawayModel(baseUrl,jsonObject,cookie)
    }

    override fun serialReceiptOnPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String
    ): Observable<SerialReceiptOnPutawayModel> {
        return apiDataSource.serialReceiptOnPutaway(baseUrl,jsonObject, page, rows, sort, asc,cookie)
    }

    override fun mySerialReceiptOnPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<SerialReceiptOnPutawayModel> {
        return apiDataSource.mySerialReceiptOnPutaway(
            baseUrl,jsonObject, page, rows, sort, order, cookie
        )
    }

    override fun assignSerialPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiDataSource.assignSerialPutaway(baseUrl, jsonObject, cookie)
    }

    override fun mySerialReceiptDetailOnPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<MySerailReceiptDetailModel> {
        return apiDataSource.mySerialReceiptDetailOnPutaway(
            baseUrl,jsonObject, page, rows, sort, order, cookie
        )
    }

    override fun receiptDetailLocation(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<ReceiptDetailLocationModel> {
        return apiDataSource.receiptDetailLocation(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }

    override fun receiptDetailSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<ReceiptSerialModel> {
        return apiDataSource.receiptDetailSerial(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }

    override fun receiptDetailScanSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiDataSource.receiptDetailScanSerial(baseUrl,jsonObject, cookie)
    }

    override fun receiptDetailSerialRemove(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiDataSource.receiptDetailSerialRemove(baseUrl,jsonObject, cookie)
    }

    override fun pickTruckList(baseUrl:String,jsonObject: JsonObject,
                               page:Int,
                               rows:Int,
                               sort:String,
                               asc:String,cookie: String): Observable<PickingTruckModel> {
        return apiDataSource.pickTruckList(baseUrl,jsonObject, page, rows, sort, asc,cookie)
    }

    override fun pickTruckDetailList(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String
    ): Observable<PickingDetailModel> {
        return apiDataSource.pickTruckDetailList(
            baseUrl, jsonObject, page, rows, sort, asc, cookie
        )
    }

    override fun completePicking(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePickingModel> {
        return apiDataSource.completePicking(baseUrl,jsonObject,cookie)
    }

    override fun serialPickTruckList(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String
    ): Observable<PickingTruckModel> {
        return apiDataSource.serialPickTruckList(
            baseUrl, jsonObject, page, rows, sort, asc, cookie
        )
    }

    override fun serialPickTruckDetailList(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        asc: String,
        cookie: String
    ): Observable<PickingDetailModel> {
        return apiDataSource.serialPickTruckDetailList(
            baseUrl, jsonObject, page, rows, sort, asc, cookie
        )
    }

    override fun completeSerialPicking(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePickingModel> {
        return apiDataSource.completeSerialPicking(baseUrl,jsonObject,cookie)
    }

    override fun getPickingSerials(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<GetPickingSerialsModel> {
        return apiDataSource.getPickingSerials(baseUrl,jsonObject,page,sort,order,cookie)
    }

    override fun scanPickingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<ScanPickingSerialModel> {
        return apiDataSource.scanPickingSerial(baseUrl,jsonObject,cookie)
    }

    override fun finishPickingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<PickingFinishSerialBaseModel> {
        return apiDataSource.finishPickingSerial(baseUrl,jsonObject,cookie)
    }

    override fun checkTruckList(baseUrl:String,cookie: String): Observable<List<CheckTruckModel>> {
        return apiDataSource.checkTruckList(baseUrl,cookie)
    }

    override fun confirmCheckTruck(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ConfirmCheckTruckModel> {
        return apiDataSource.confirmCheckTruck(baseUrl,jsonObject,cookie)
    }

    override fun denyCheckTruck(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<DenyCheckTruckModel> {
        return apiDataSource.denyCheckTruck(baseUrl,jsonObject,cookie)
    }

    override fun shippingTruckList(baseUrl:String,jsonObject: JsonObject,
                                   page:Int,
                                   rows:Int,
                                   sort:String,
                                   asc:String, cookie: String): Observable<ShippingTruckModel> {
        return apiDataSource.shippingTruckList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
    }

    override fun getShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
        cookie: String
    ): Observable<ShippingDetailModel> {
        return apiDataSource.getShippingDetail(baseUrl,jsonObject,page, rows, sort, asc, cookie)
    }

    override fun getShippingSerials(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ShippingSerialModel>> {
        return apiDataSource.getShippingSerials(baseUrl,jsonObject,cookie)
    }

    override fun insertShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<AddShippingSerialModel> {
        return apiDataSource.insertShippingDetail(baseUrl,jsonObject,cookie)
    }

    override fun removeShippingSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveShippingSerialModel> {
        return apiDataSource.removeShippingSerial(baseUrl,jsonObject,cookie)
    }

    override fun loadingFinish(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LoadingFinishModel> {
        return apiDataSource.loadingFinish(baseUrl,jsonObject,cookie)
    }

    override fun revokLocation(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<DestinyLocationTransfer>> {
        return apiDataSource.revokLocation(url, jsonObject, cookie)
    }

    override fun revoke(url: String, jsonObject: JsonObject, cookie: String): Single<RevokeModel> {
        return apiDataSource.revoke(url, jsonObject, cookie)
    }

    override fun getCustomerInShipping(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<CustomerInShipping>> {
        return apiDataSource.getCustomerInShipping(url, jsonObject, cookie)
    }

    override fun truckLoadingRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<TruckLoadingRemoveModel> {
        return apiDataSource.truckLoadingRemove(url, jsonObject, cookie)
    }

    override fun setShippingColor(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SetShippingAddressColorModel> {
        return apiDataSource.setShippingColor(url, jsonObject, cookie)
    }

    override fun getColorList(url: String, cookie: String): Single<List<ColorModel>> {
        return apiDataSource.getColorList(url, cookie)
    }

    override fun getShippingDetailCustomers(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<CustomerModel>> {
        return apiDataSource.getShippingDetailCustomers(url, jsonObject, cookie)
    }

    override fun getCargoItem(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<CargoRow> {
        return apiDataSource.getCargoItem(url, jsonObject, cookie)
    }

    override fun getCargoDetailLocation(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<LocationModel>> {
        return apiDataSource.getCargoDetailLocation(url, jsonObject, cookie)
    }

    override fun getCargoList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Single<CargoModel> {
        return apiDataSource.getCargoList(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun getCargoDetailList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Single<CargoDetailModel> {
        return apiDataSource.getCargoDetailList(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun getMyCargoList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Single<MyCargoModel> {
        return apiDataSource.getMyCargoList(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun getMyCargoDetailList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Single<MyCargoDetailModel> {
        return apiDataSource.getMyCargoDetailList(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun cargoDetailWorkerSubmit(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiDataSource.cargoDetailWorkerSubmit(url, jsonObject, cookie)
    }

    override fun cargoDetailWorkerRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiDataSource.cargoDetailWorkerRemove(url, jsonObject, cookie)
    }

    override fun driverTaskSubmit(
        url: String,
        jsonObject: JsonObject,
        cookie: String,
    ): Single<DriverTaskDoneModel> {
        return apiDataSource.driverTaskSubmit(url,jsonObject,cookie)
    }

    override fun changeDriverTaskDone(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiDataSource.changeDriverTaskDone(url, jsonObject, cookie)
    }

    override fun driverTaskRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiDataSource.driverTaskRemove(url,jsonObject,cookie)
    }

    override fun shippingCanselTruckList(
        baseUrl:String,
        jsonObject: JsonObject,
         page:Int,
         rows:Int,
         sort:String,
         asc:String,cookie: String):
            Observable<ShippingTruckModel> {
        return apiDataSource.shippingCanselTruckList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
    }

    override fun getCanselShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
        cookie:String
    ): Observable<ShippingDetailModel> {
        return apiDataSource.getCanselShippingDetail(baseUrl,jsonObject, page, rows, sort, asc, cookie)
    }

    override fun getCanselShippingSerials(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ShippingSerialModel>> {
        return apiDataSource.getCanselShippingSerials(baseUrl,jsonObject, cookie)
    }

    override fun insertCanselShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<AddShippingSerialModel> {
        return apiDataSource.insertCanselShippingDetail(baseUrl,jsonObject, cookie)
    }

    override fun removeCanselShippingSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveShippingSerialModel> {
        return apiDataSource.removeCanselShippingSerial(baseUrl,jsonObject,cookie)
    }

    override fun loadingCanselFinish(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LoadingFinishModel> {
        return apiDataSource.loadingCanselFinish(baseUrl,jsonObject, cookie)
    }

    override fun sourceLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
    ): Observable<SourceLocationTransferModel> {
        return apiDataSource
            .sourceLocationTransfer(baseUrl,jsonObject, cookie,page, rows, sort, asc)
    }

    override fun destinationLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<DestinyLocationTransfer>> {
        return apiDataSource.destinationLocationTransfer(baseUrl,jsonObject,cookie)
    }

    override fun submitLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LocationTransferSubmit> {
        return apiDataSource.submitLocationTransfer(baseUrl,jsonObject, cookie)
    }

    override fun locationTransferTaskList(
                                        baseUrl:String,
                                        jsonObject: JsonObject,
                                          page:Int,
                                          rows:Int,
                                          sort:String,
                                          asc:String,
                                          cookie:String):
            Observable<LocationTransferTaskModel> {
        return apiDataSource.locationTransferTaskList(baseUrl,jsonObject, page, rows, sort, asc, cookie)
    }

    override fun completeLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompleteLocationTransfer> {
        return apiDataSource.completeLocationTransfer(baseUrl,jsonObject, cookie)
    }

    override fun inventoryModifiedTask(
                                    baseUrl:String,
                                    jsonObject: JsonObject,
                                       page:Int,
                                       rows:Int,
                                       sort:String,
                                       asc:String,
                                       cookie:String): Observable<InventoryModifedModel> {
        return apiDataSource.
            inventoryModifiedTask(baseUrl,jsonObject, page, rows, sort, asc, cookie)
    }

    override fun completeInventoryModify(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompleteInventoryModifyModel> {
        return apiDataSource.completeInventoryModify(baseUrl,jsonObject, cookie)
    }

    override fun inventoryModifyWitoutComp(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<InventoryVerifyWithoutComp> {
        return apiDataSource.inventoryModifyWitoutComp(baseUrl,jsonObject, cookie)
    }

    override fun reportLocationInventory(
        baseUrl:String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<ReportLocationInventory> {
        return apiDataSource.reportLocationInventory(baseUrl,jsonObject, page, rows, sort, order, cookie)

    }

    override fun reportSerialInventory(
        baseUrl:String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<SerialInventoryModel> {
        return apiDataSource.reportSerialInventory(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }

    override fun reportSerialInventoryProduct(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<SerialInvProductModel> {
        return apiDataSource.reportSerialInventoryProduct(url,jsonObject, page, rows, sort, order, cookie)
    }


    override fun reportLocationInventByProduct(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<LocationInventoryByProduct>> {
        return apiDataSource.reportLocationInventByProduct(baseUrl,jsonObject, cookie)
    }



    override fun pickAndPutReport(
        baseUrl:String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<PickAndPutModel> {
        return apiDataSource.pickAndPutReport(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }

    override fun getProductWithoutMaster(
        baseUrl:String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<List<ProductWithoutMasterModel>> {
        return apiDataSource.getProductWithoutMaster(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }


    override fun unitOfMeasureSubmtit(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<UnitOfMeasureSubmitModel> {
        return apiDataSource.unitOfMeasureSubmtit(baseUrl,jsonObject, cookie)
    }

    override fun palletList(
        baseUrl:String,cookie: String): Observable<List<PalletModel>> {
        return apiDataSource.palletList(baseUrl,cookie)
    }

    override fun waitTruckLoading(
        baseUrl:String,
        isCompleted: Boolean,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String
    ): Observable<WaitTruckLoadingModel> {
        return apiDataSource.waitTruckLoading(baseUrl,isCompleted,jsonObject, page, rows, sort, order, cookie)
    }

    override fun waitTruckLoadingNotAssignDetail(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<ShippingDetailModel> {
        return apiDataSource.waitTruckLoadingNotAssignDetail(baseUrl,jsonObject, page, rows, sort, order, cookie)
    }

    override fun truckLoadingAssign(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<TruckLoadingAssignModel> {
        return apiDataSource.truckLoadingAssign(baseUrl,jsonObject, cookie)
    }

    override fun getWarehouse(
        baseUrl:String,cookie: String): Observable<List<WarehouseModel>> {
        return apiDataSource.getWarehouse(baseUrl,cookie)
    }

    override fun getOwner(
        baseUrl:String,cookie: String): Observable<List<OwnerModel>> {
        return apiDataSource.getOwner(baseUrl,cookie)
    }

    override fun getProducts(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ProductModel>> {
        return apiDataSource.getProducts(baseUrl,jsonObject, cookie)
    }

    override fun insertSerial(
        baseUrl:String,
        jsonArray: JsonArray,
        cookie: String
    ): Observable<InsertSerialModel> {
        return apiDataSource.insertSerial(baseUrl,jsonArray, cookie)
    }

    override fun notif(
        baseUrl:String,cookie: String): Single<List<NotificationModel>> {
        return apiDataSource.notif(baseUrl,cookie)
    }

    override fun getTrackingProducts(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<ProductModel>> {
        return apiDataSource.getTrackingProducts(baseUrl,jsonObject, cookie)
    }

    override fun getSerialInfo(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<GetSerialInfoModel> {
        return apiDataSource.getSerialInfo(baseUrl,jsonObject, cookie)
    }

    override fun labelling(
        baseUrl:String,
        jsonArray: JsonArray, cookie: String): Single<LabellingModel> {
        return apiDataSource.labelling(baseUrl,jsonArray, cookie)
    }

    override fun rework(
        baseUrl:String,jsonObject: JsonObject, cookie: String): Single<ReworkModel> {
        return apiDataSource.rework(baseUrl,jsonObject, cookie)
    }

    override fun getCustomers(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<CustomerModel>> {
        return apiDataSource.getCustomers(
            url,jsonObject, cookie
        )
    }

    override fun leftDock(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<LeftDockModel> {
        return apiDataSource.leftDock(
            url,jsonObject,cookie
        )
    }

    override fun stockTakingList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<StockTakeModel> {
        return apiDataSource.stockTakingList(
            url,jsonObject,page, rows, sort, order, cookie
        )
    }


    override fun stockTakingLocationList(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<StockTakingLocationModel> {
        return apiDataSource.stockTakingLocationList(
            url,jsonObject,page, rows, sort, order, cookie
        )
    }

    override fun stockTakingLocationInsert(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<StockLocationInsertModel> {
        return apiDataSource.stockTakingLocationInsert(url, jsonObject, cookie)
    }

    override fun stockTakingCount(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<StockTakingCountModel> {
        return apiDataSource.stockTakingCount(url, jsonObject, cookie)
    }

    override fun stockLocation(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<StockLocationModel> {
        return apiDataSource.stockLocation(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun getCurrentVersionInfo(url: String, cookie: String): Single<VersionInfoModel> {
        return apiDataSource.getCurrentVersionInfo(url,cookie)
    }

    override fun getDocks(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<DockModel> {
        return apiDataSource.getDocks(url, jsonObject, page, rows, sort, order, cookie)
    }

    override fun setUseDock(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SetUseDockModel> {
        return apiDataSource.setUseDock(url, jsonObject, cookie)
    }
}