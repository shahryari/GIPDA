package com.example.warehousemanagment.model.data

import PickingDetailModel
import com.example.warehousemanagment.model.models.LocationModel
import com.example.warehousemanagment.model.models.LocationProductSerialModel
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
import com.example.warehousemanagment.model.models.dock_assign.DockAssignModel
import com.example.warehousemanagment.model.models.dock_assign.DockListOnShippingModel
import com.example.warehousemanagment.model.models.dock_assign.ShippingListOnDockModel
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
import com.example.warehousemanagment.model.models.picking.SerialBasePickingModel
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
import com.example.warehousemanagment.model.models.serial_transfer.SerialTransferProductModel
import com.example.warehousemanagment.model.models.shipping.AddShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.LoadingFinishModel
import com.example.warehousemanagment.model.models.shipping.RemoveShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.SerialBaseShippingSerialModel
import com.example.warehousemanagment.model.models.shipping.ShippingCancelSerialRow
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
import com.example.warehousemanagment.model.models.stock.StockTurnItemLocationModel
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

interface DataSource
{

    fun logException(baseUrl: String,jsonObject: JsonObject) : Single<Unit>
    fun login(baseUrl:String,jsonObject: JsonObject): Single<LoginModel>

    fun dashboardInfo(baseUrl:String,cookie:String): Observable<List<DashboardInfoModel>>

    fun getCurrentUser(baseUrl:String,cookie:String): Observable<CurrentUserModel>

    fun catalogList(baseUrl:String,cookie:String): Observable<List<CatalogModel>>
    //----------------------Receiving-----------------------------------------------
    fun getReceivingList(baseUrl:String,cookie:String, jsonObject: JsonObject,
                         page:Int,
                         rows:Int,
                         sort:String,
                         asc:String): Observable<ReceivingModel>


    fun getReceiveDetailList(baseUrl:String,jsonObject: JsonObject,
                             page:Int,
                             rows:Int,
                             sort:String,
                             asc:String,
                             cookie:String):
            Observable<ReceiveDetailModel>


    fun getReceiveDetailSerialList(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<List<ReceivingDetailSerialModel>>


    fun addReceiveDetailSerial(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Single<AddReceivingDetailSerialModel>


    fun removeSerial(baseUrl:String,jsonObject: JsonObject,cookie:String): Observable<RemoveSerialModel>


    fun countReceiveDetail(baseUrl:String,jsonObject: JsonObject
                           ,cookie:String): Observable<ReceiveDetailCountModel>

    fun confirmReceiveDetail(baseUrl:String,jsonObject: JsonObject
                             , cookie:String): Observable<ConfirmReceiveDetailModel>

    //-----------------------Putaway--------------------------------------------------

    fun putawayTruckList(baseUrl:String,cookie:String,jsonObject: JsonObject,
                         page:Int,
                         rows:Int,
                         sort:String,
                         asc:String): Observable<PutawayTruckModel>

    fun putawayTruckDetail(baseUrl:String,jsonObject: JsonObject,
                           page:Int,
                           rows:Int,
                           sort:String,
                           asc:String,cookie:String):
            Observable<PutawayTruckDetailModel>

    fun completePutawayModel(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<CompletePutawayModel>

    fun serialReceiptOnPutaway(
        baseUrl:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String,
        cookie:String
    ) : Observable<SerialReceiptOnPutawayModel>

    fun mySerialReceiptOnPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<SerialReceiptOnPutawayModel>

    fun assignSerialPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>

    fun serialReceiptRemoveFromMe(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>


    fun mySerialReceiptDetailOnPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<MySerailReceiptDetailModel>

    fun receiptDetailLocation(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<ReceiptDetailLocationModel>

    fun receiptDetailSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<ReceiptSerialModel>

    fun receiptDetailScanSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>


    fun receiptDetailAutoScanSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>

    fun receiptDetailScanSerialAuto(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>



    fun receiptDetailSerialRemove(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<SerialPutawayAssignModel>

    //-----------------------Picking-----------------------------------------------


    fun pickTruckList(baseUrl:String,jsonObject: JsonObject,
                      page:Int,
                      rows:Int,
                      sort:String,
                      asc:String, cookie:String): Observable<PickingTruckModel>



    fun pickTruckDetailList(baseUrl:String,jsonObject: JsonObject,
                      page:Int,
                      rows:Int,
                      sort:String,
                      asc:String, cookie:String): Observable<PickingDetailModel>



    fun completePicking(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<CompletePickingModel>

    fun serialPickTruckList(baseUrl:String,jsonObject: JsonObject,
                      page:Int,
                      rows:Int,
                      sort:String,
                      asc:String, cookie:String): Observable<PickingTruckModel>



    fun serialPickTruckDetailList(baseUrl:String,jsonObject: JsonObject,
                            page:Int,
                            rows:Int,
                            sort:String,
                            asc:String, cookie:String): Observable<PickingDetailModel>



    fun completeSerialPicking(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<CompletePickingModel>


    fun getPickingSerials(baseUrl:String,jsonObject: JsonObject,page: Int,sort: String, order: String,cookie:String):
            Observable<GetPickingSerialsModel>

    fun scanPickingSerial(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Single<ScanPickingSerialModel>

    fun finishPickingSerial(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Single<PickingFinishSerialBaseModel>

    fun getSerialBasePicking(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<SerialBasePickingModel>

    fun getSerialBasePickingDetailSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<GetPickingSerialsModel>

    fun scanSerialBasePickingDetailSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<ScanPickingSerialModel>

    //-----------------------CheckTruck-------------------------------------------

    fun checkTruckList(baseUrl:String,cookie:String):
            Observable<List<CheckTruckModel>>

    fun confirmCheckTruck(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<ConfirmCheckTruckModel>

    fun denyCheckTruck(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<DenyCheckTruckModel>
    //-------------------------Shipping-------------------------------------------------

    fun shippingTruckList(baseUrl:String,jsonObject: JsonObject,
                          page:Int,
                          rows:Int,
                          sort:String,
                          asc:String, cookie: String): Observable<ShippingTruckModel>


    fun serialShippingTruckList(baseUrl:String,jsonObject: JsonObject,
                          page:Int,
                          rows:Int,
                          sort:String,
                          asc:String, cookie: String): Observable<ShippingTruckModel>

    fun getShippingDetail(baseUrl:String,jsonObject: JsonObject,
                          page:Int,
                          rows:Int,
                          sort:String,
                          asc:String,
                          cookie:String):Observable<ShippingDetailModel>

    fun serialShippingTruckDetail(baseUrl:String, jsonObject: JsonObject,
                                  page:Int,
                                  rows:Int,
                                  sort:String,
                                  asc:String,
                                  cookie:String):Observable<ShippingDetailModel>

    fun getShippingSerials(baseUrl:String,jsonObject: JsonObject,
                          cookie:String):Observable<List<ShippingSerialModel>>

    fun getSerialBaseShippingSerials(baseUrl: String,jsonObject: JsonObject,page: Int,rows: Int,cookie: String)
        : Observable<SerialBaseShippingSerialModel>

    fun verifySerialBaseShippingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<AddShippingSerialModel>

    fun insertShippingDetail(baseUrl:String,jsonObject: JsonObject,cookie:String):
            Observable<AddShippingSerialModel>

    fun removeShippingSerial(baseUrl:String,jsonObject: JsonObject,
                           cookie:String):Observable<RemoveShippingSerialModel>


    fun loadingFinish(baseUrl:String,jsonObject: JsonObject,
                      cookie:String):Observable<LoadingFinishModel>

    fun serialBaseLoadingFinish(baseUrl: String,jsonObject: JsonObject,cookie: String) : Observable<LoadingFinishModel>

    fun revokLocation(url:String ,jsonObject: JsonObject,
                      cookie:String):Single<List<DestinyLocationTransfer>>

    fun revoke(url:String, jsonObject: JsonObject,
                cookie:String):Single<RevokeModel>

    fun getCustomerInShipping(url: String,jsonObject: JsonObject, cookie: String) : Observable<List<CustomerInShipping>>

    fun setShippingColor(url: String,jsonObject: JsonObject,cookie: String) : Single<SetShippingAddressColorModel>

    fun getColorList(url: String,cookie: String) : Single<List<ColorModel>>

    fun getShippingDetailCustomers(url: String,jsonObject: JsonObject,cookie: String) : Single<List<CustomerModel>>

    fun truckLoadingRemove(url: String,jsonObject: JsonObject,cookie: String) : Single<TruckLoadingRemoveModel>
    //----------------------------Cargo----------------------------

    fun getCargoItem(url: String,jsonObject: JsonObject,cookie: String) : Single<CargoRow>

    fun getCargoDetailLocation(url: String, jsonObject: JsonObject,cookie: String) : Single<List<LocationModel>>

    fun getCargoList(url:String ,jsonObject: JsonObject,
                     page:Int,
                     rows:Int,
                     sort:String,
                     order:String,
                     cookie:String):Single<CargoModel>

    fun getCargoDetailList(url:String ,jsonObject: JsonObject,
                     page:Int,
                     rows:Int,
                     sort:String,
                     order:String,
                     cookie:String):Single<CargoDetailModel>

    fun driverTaskSubmit(url: String,jsonObject: JsonObject,cookie:String)
            :Single<DriverTaskDoneModel>

    fun driverTaskRemove(url: String,jsonObject: JsonObject,cookie:String)
            :Single<DriverTaskDoneModel>


    fun changeDriverTaskDone(url: String, jsonObject: JsonObject, cookie:String)
            :Single<DriverTaskDoneModel>

    fun getMyCargoList(url:String ,jsonObject: JsonObject,
                     page:Int,
                     rows:Int,
                     sort:String,
                     order:String,
                     cookie:String):Single<MyCargoModel>

    fun getMyCargoDetailList(url:String ,jsonObject: JsonObject,
                           page:Int,
                           rows:Int,
                           sort:String,
                           order:String,
                           cookie:String):Single<MyCargoDetailModel>


    fun cargoDetailWorkerSubmit( url: String, jsonObject: JsonObject,
                                cookie:String)
            :Single<DriverTaskDoneModel>


    fun cargoDetailWorkerRemove( url: String, jsonObject: JsonObject,cookie:String)
            :Single<DriverTaskDoneModel>

    //----------------------------ShippingCansel---------------------------------------------------

    fun shippingCanselTruckList(baseUrl:String,jsonObject: JsonObject,
                                page:Int,
                                rows:Int,
                                sort:String,
                                asc:String, cookie:String):Observable<ShippingTruckModel>

    fun getCanselShippingDetail(baseUrl:String,jsonObject: JsonObject,
                                page:Int,
                                rows:Int,
                                sort:String,
                                asc:String,
                               cookie:String):Observable<ShippingDetailModel>

    fun getCanselShippingSerials(baseUrl:String,jsonObject: JsonObject,
                                 cookie:String):Observable<List<ShippingSerialModel>>

    fun insertCanselShippingDetail(baseUrl:String,jsonObject: JsonObject,
                                    cookie:String):Single<AddShippingSerialModel>

    fun removeCanselShippingSerial(baseUrl:String,jsonObject: JsonObject,
                                    cookie:String):Observable<RemoveShippingSerialModel>

    fun loadingCanselFinish(baseUrl:String,jsonObject: JsonObject,
                            cookie:String):Observable<LoadingFinishModel>

    fun getSerialBaseShippingCancelSerials(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Observable<List<ShippingCancelSerialRow>>

    fun scanSerialBaseShippingCancelSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<AddShippingSerialModel>

    fun removeSerialBaseShippingCancelSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<RemoveShippingSerialModel>

    fun cancelSerialShippingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<LoadingFinishModel>

    //----------Transfer------------------------------------------

    fun sourceLocationTransfer(baseUrl:String,jsonObject: JsonObject,
                               cookie:String,
                               page:Int,
                               rows:Int,
                               sort:String,
                               asc:String,):Observable<SourceLocationTransferModel>

    fun destinationLocationTransfer(baseUrl:String,jsonObject: JsonObject,
                                    cookie:String):Observable<List<DestinyLocationTransfer>>

    fun submitLocationTransfer(baseUrl:String,jsonObject: JsonObject,
                              cookie:String):Observable<LocationTransferSubmit>

    fun locationTransferTaskList(baseUrl:String,jsonObject: JsonObject,
                                 page:Int,
                                 rows:Int,
                                 sort:String,
                                 asc:String,
                                 cookie:String):
            Observable<LocationTransferTaskModel>

    fun completeLocationTransfer(baseUrl:String,jsonObject: JsonObject,
                                 cookie:String):Observable<CompleteLocationTransfer>

    fun inventoryModifiedTask(baseUrl:String,jsonObject: JsonObject,
                              page:Int,
                              rows:Int,
                              sort:String,
                              asc:String,
                              cookie:String):Observable<InventoryModifedModel>

    fun completeInventoryModify(baseUrl:String,jsonObject: JsonObject,
                                cookie:String):Observable<CompleteInventoryModifyModel>

    fun inventoryModifyWitoutComp(baseUrl:String,jsonObject: JsonObject,
                                cookie:String):Observable<InventoryVerifyWithoutComp>

    fun getSerialBaseLocationProduct(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        rows:Int,
        sort:String,
        asc:String,
        cookie:String
    ) : Observable<SerialTransferProductModel>


    fun checkLocationTransferSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<LocationTransferSubmit>

    fun serialBaseLocationTransfer(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<LocationTransferSubmit>

    //------------------------ReportLocation----------------------------------------------

    fun reportLocationInventory(baseUrl:String,jsonObject: JsonObject,
                                page:Int,
                                rows:Int,
                                sort:String,
                                order:String,
                                cookie:String):Observable<ReportLocationInventory>

    fun getLocationProductSerials(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        cookie: String
    ) : Observable<LocationProductSerialModel>

    fun reportLocationInventByProduct(baseUrl:String,jsonObject: JsonObject,
                                     cookie:String):Observable<List<LocationInventoryByProduct>>

    fun reportSerialInventory(baseUrl:String,jsonObject: JsonObject,
                               page:Int,
                                rows:Int,
                               sort:String,
                               order:String,
                               cookie:String)
            :Observable<SerialInventoryModel>

    fun reportSerialInventoryProduct(url:String,jsonObject: JsonObject,
                                     page:Int,
                                     rows:Int,
                                     sort:String,
                                     order:String,
                                     cookie:String)
            :Observable<SerialInvProductModel>

    fun pickAndPutReport(baseUrl:String,jsonObject: JsonObject, page:Int,
                         rows:Int,
                         sort:String,
                         order:String,
                         cookie:String):Observable<PickAndPutModel>

    //---------------------Product Without Master------------------------------------------

    fun getProductWithoutMaster(baseUrl:String,jsonObject: JsonObject,
                                page:Int,
                                rows:Int,
                                sort:String,
                                order:String,
                                cookie:String):
                 Observable<List<ProductWithoutMasterModel>>

    fun unitOfMeasureSubmtit(baseUrl:String,jsonObject: JsonObject, cookie:String):
            Observable<UnitOfMeasureSubmitModel>

    fun palletList(baseUrl: String,cookie:String):Observable<List<PalletModel>>

    //-------------------------------------------------------------------------------------

    fun waitTruckLoading(baseUrl:String,
                         isCompleted: Boolean,
                         jsonObject: JsonObject,
                         page:Int,
                         rows:Int,
                         sort:String,
                         order:String,
                         cookie: String):Observable<WaitTruckLoadingModel>

    fun waitTruckLoadingNotAssignDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String
    ) : Observable<ShippingDetailModel>

    fun truckLoadingAssign(baseUrl:String,jsonObject: JsonObject,cookie: String)
            :Observable<TruckLoadingAssignModel>
    //------------------------------------------------------------------------------------
    fun getWarehouse(baseUrl:String,cookie: String):Observable<List<WarehouseModel>>
    fun getOwner( baseUrl:String,cookie: String):Observable<List<OwnerModel>>
    fun getProducts(baseUrl:String,jsonObject: JsonObject,
                   cookie: String):Observable<List<ProductModel>>
    fun insertSerial(
        baseUrl:String,
        jsonArray: JsonArray,
        cookie: String):Observable<InsertSerialModel>
    //Notification---------------------------------------------

    fun notif(baseUrl:String,cookie: String):Single<List<NotificationModel>>

    //-----------------Tracking------------------------------
    fun getTrackingProducts(
        baseUrl:String,
        jsonObject: JsonObject,cookie: String): Single<List<ProductModel>>

    fun getSerialInfo(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Single<GetSerialInfoModel>

    fun labelling(
        baseUrl:String,
        jsonArray: JsonArray,
       cookie: String):
            Single<LabellingModel>

    fun rework(
        baseUrl:String,
         jsonObject: JsonObject, cookie: String):
            Single<ReworkModel>

    fun getCustomers(
        url:String,
        jsonObject: JsonObject,
         cookie: String):
            Observable<List<CustomerModel>>

    fun leftDock(
        url:String ,
        jsonObject: JsonObject,
        cookie: String
    ): Single<LeftDockModel>

    //---Stock----------------------------
    fun stockTakingList(url:String,jsonObject: JsonObject,
                        page:Int,
                        rows:Int,
                        sort:String,
                        order:String,
                        cookie: String,)
            :Observable<StockTakeModel>


    fun stockTakingLocationList(
        url:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String,
    ):Observable<StockTakingLocationModel>

    fun stockTakingLocationListCounted(
        url:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String,
    ):Observable<StockTakingLocationModel>



    fun stockTakingLocationInsert(
        url:String ,
        jsonObject: JsonObject,
        cookie: String,
    ):Single<StockLocationInsertModel>

    fun stockTakingCount(
        url:String ,
        jsonObject: JsonObject,
        cookie: String,
    ):Single<StockTakingCountModel>

    fun stockLocation(
        url:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String,
    ):Observable<StockLocationModel>


    fun stockTurnItemLocation(
        url:String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        order:String,
        cookie: String,
    ) : Observable<StockTurnItemLocationModel>

    fun saveTempCountQuantity(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<StockTakingCountModel>


    //get current version info


    fun getCurrentVersionInfo(
        url: String,
        cookie: String
    ) : Single<VersionInfoModel>


    fun getDocks(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<DockModel>

    fun setUseDock(url: String,jsonObject: JsonObject,cookie: String) : Single<SetUseDockModel>


    //dock assign

    fun getShippingListOnDock(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<ShippingListOnDockModel>


    fun getDockListOnShippingAddress(
        url: String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ) : Observable<DockListOnShippingModel>

    fun dockAssignShippingAddress(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<DockAssignModel>

    fun getProductsWithOwner(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ) : Single<List<ProductModel>>
}



