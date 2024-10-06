package com.example.warehousemanagment.model.data

import PickingDetailModel
import com.example.warehousemanagment.model.constants.ApiUtils
import com.example.warehousemanagment.model.constants.Utils
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
import com.example.warehousemanagment.model.models.picking.picking.PickingTruckModel
import com.example.warehousemanagment.model.models.putaway.complete.CompletePutawayModel
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
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService
{
    @Headers(Utils.CONTENT_TYPE)
    @POST//("login")
    fun login (
        @Url url:String,
        @Body jsonObject: JsonObject): Single<LoginModel>


    @Headers(Utils.CONTENT_TYPE,Utils.PDA)
    @POST//"DashboardWorkerTask")
    fun dashboardInfo(@Url url:String ,@Header(Utils.COOKIE) cookie:String): Observable<List<DashboardInfoModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"CurrentUser")
    fun getCurrentUser(@Url url:String ,@Header(Utils.COOKIE) cookie:String): Observable<CurrentUserModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"CatalogValues")
    fun catalogList(@Url url:String ,@Header(Utils.COOKIE) cookie:String): Observable<List<CatalogModel>>

    //-------------------------------------------Receiving---------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingList")
    fun getReceivingList(@Url url:String ,@Header(Utils.COOKIE) cookie:String,
                         @Body jsonObject: JsonObject,
                         @Header("page") page:Int,
                         @Header("rows") rows:Int,
                         @Header("sort") sort:String,
                         @Header("order") asc:String): Observable<ReceivingModel>



    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailList")
    fun getReceiveDetailList(@Url url:String ,@Body jsonObject: JsonObject,
                             @Header("page") page:Int,
                             @Header("rows") rows:Int,
                             @Header("sort") sort:String,
                             @Header("order") asc:String
                             ,@Header(Utils.COOKIE) cookie:String): Observable<ReceiveDetailModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailSerialList")
    fun getReceiveDetailSerialList(@Url url:String ,@Body jsonObject: JsonObject
                                   ,@Header(Utils.COOKIE) cookie:String): Observable<List<ReceivingDetailSerialModel>>



    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailSerialInsert")
    fun addReceiveDetailSerial(@Url url:String ,@Body jsonObject: JsonObject
                                   ,@Header(Utils.COOKIE) cookie:String): Single<AddReceivingDetailSerialModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailSerialRemove")
    fun removeSerial(@Url url:String ,@Body jsonObject: JsonObject
                               ,@Header(Utils.COOKIE) cookie:String): Observable<RemoveSerialModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailCount")
    fun countReceiveDetail(@Url url:String ,@Body jsonObject: JsonObject
                     ,@Header(Utils.COOKIE) cookie:String): Observable<ReceiveDetailCountModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ReceivingDetailSerialConfirm")
    fun confirmReceiveDetail(@Url url:String ,@Body jsonObject: JsonObject
                           ,@Header(Utils.COOKIE) cookie:String): Observable<ConfirmReceiveDetailModel>

    //----------PutAaway-----------------------------------------------------------------------------------
    @Headers(Utils.CONTENT_TYPE)
    @POST//"PutawayTruck")
    fun putawayTruckList(@Url url:String ,@Header(Utils.COOKIE) cookie:String,
        @Body jsonObject: JsonObject,
        @Header("page") page:Int,
        @Header("rows") rows:Int,
        @Header("sort") sort:String,
        @Header("order") asc:String): Observable<PutawayTruckModel>



    @Headers(Utils.CONTENT_TYPE)
    @POST//"PutawayTruckDetail")
    fun putawayTruckDetail(@Url url:String ,@Body jsonObject: JsonObject,
                           @Header("page") page:Int,
                           @Header("rows") rows:Int,
                           @Header("sort") sort:String,
                           @Header("order") asc:String,
                           @Header(Utils.COOKIE) cookie:String):
            Observable<PutawayTruckDetailModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"PutawayComplete")
    fun completePutawayModel(@Url url:String ,@Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie:String):
            Observable<CompletePutawayModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"SerialReceiptOnPutaway)
    fun serialReceiptOnPutaway(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header("page") page:Int,
        @Header("rows") rows:Int,
        @Header("sort") sort:String,
        @Header("order") asc:String,
        @Header(Utils.COOKIE) cookie:String
    ): Observable<SerialReceiptOnPutawayModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"MySerialReceiptOnPutaway)
    fun mySerialReceiptOnPutaway(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header("page") page:Int,
        @Header("rows") rows:Int,
        @Header("sort") sort:String,
        @Header("order") asc:String,
        @Header(Utils.COOKIE) cookie:String
    ): Observable<SerialReceiptOnPutawayModel>

    //------------Picking-------------------------------------------------------------------------
    @Headers(Utils.CONTENT_TYPE)
    @POST//"PickingList")
    fun pickTruckList(@Url url:String ,@Body jsonObject: JsonObject,
                       @Header("page") page:Int,
                       @Header("rows") rows:Int,
                       @Header("sort") sort:String,
                       @Header("order") asc:String,@Header(Utils.COOKIE) cookie:String):
            Observable<PickingTruckModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"PickingListGroupedDetail")
    fun pickTruckDetailList(@Url url:String ,@Body jsonObject: JsonObject,
                      @Header("page") page:Int,
                      @Header("rows") rows:Int,
                      @Header("sort") sort:String,
                      @Header("order") asc:String,@Header(Utils.COOKIE) cookie:String):
            Observable<PickingDetailModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"PickingComplete")
    fun completePicking(@Url url:String ,@Body jsonObject: JsonObject,@Header(Utils.COOKIE) cookie:String):
            Observable<CompletePickingModel>

    //----------CheckTruck------------------------------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"CheckTruck")
    fun checkTruckList(@Url url:String ,@Header(Utils.COOKIE) cookie:String):
            Observable<List<CheckTruckModel>>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ConfirmTruck")
    fun confirmCheckTruck(@Url url:String ,@Body jsonObject: JsonObject,@Header(Utils.COOKIE) cookie:String):
            Observable<ConfirmCheckTruckModel>



    @Headers(Utils.CONTENT_TYPE)
    @POST//"DenyTruck")
    fun denyCheckTruck(@Url url:String ,@Body jsonObject: JsonObject,@Header(Utils.COOKIE) cookie:String):
            Observable<DenyCheckTruckModel>

    //-------------------Shipping------------------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingTruck")
    fun shippingTruckList(@Url url:String ,@Body jsonObject: JsonObject,
                          @Header("page") page:Int,
                          @Header("rows") rows:Int,
                          @Header("sort") sort:String,
                          @Header("order") asc:String,@Header(Utils.COOKIE) cookie:String):Observable<ShippingTruckModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingTruckDetail")
    fun getShippingDetail(@Url url:String ,@Body jsonObject: JsonObject,
                          @Header("page") page:Int,
                          @Header("rows") rows:Int,
                          @Header("sort") sort:String,
                          @Header("order") asc:String,
                          @Header(Utils.COOKIE) cookie:String):Observable<ShippingDetailModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingDetailSerial")
    fun getShippingSerials(@Url url:String ,@Body jsonObject: JsonObject,
                          @Header(Utils.COOKIE) cookie:String):Observable<List<ShippingSerialModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingDetailSerialInsert")
    fun insertShippingDetail(@Url url:String ,@Body jsonObject: JsonObject,
                           @Header(Utils.COOKIE) cookie:String):Observable<AddShippingSerialModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingDetailSerialRemove")
    fun removeShippingSerial(@Url url:String ,@Body jsonObject: JsonObject,
                           @Header(Utils.COOKIE) cookie:String):Observable<RemoveShippingSerialModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"LoadingFinish")
    fun loadingFinish(@Url url:String ,@Body jsonObject: JsonObject,
                             @Header(Utils.COOKIE) cookie:String):Observable<LoadingFinishModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"RevokLocation"
    fun revokLocation(@Url url:String ,@Body jsonObject: JsonObject,
                      @Header(Utils.COOKIE) cookie:String):Single<List<DestinyLocationTransfer>>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun revoke(@Url url:String,@Body jsonObject: JsonObject,
               @Header(Utils.COOKIE) cookie:String):Single<RevokeModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//CustomerInShipping
    fun getCustomerInShipping(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Observable<List<CustomerInShipping>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//CustomerColorList
    fun getColors(@Url url: String, @Header(Utils.COOKIE) cookie: String) : Single<List<ColorModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//ShippingDetailCustomers
    fun getShippingDetailCustomers(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Single<List<CustomerModel>>


    @Headers(Utils.CONTENT_TYPE)
    @POST//SetShippingAddressColor
    fun setShippingAddressColor(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Single<SetShippingAddressColorModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//TruckLoadingRemove
    fun truckLoadingRemove(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Single<TruckLoadingRemoveModel>
    //----------------------------ShippingCansel---------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancelTruck")
    fun shippingCanselTruckList(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header("page") page:Int,
                                @Header("rows") rows:Int,
                                @Header("sort") sort:String,
                                @Header("order") asc:String,
                                @Header(Utils.COOKIE) cookie:String):Observable<ShippingTruckModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancelDetail")
    fun getCanselShippingDetail(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header("page") page:Int,
                                @Header("rows") rows:Int,
                                @Header("sort") sort:String,
                                @Header("order") asc:String,
                          @Header(Utils.COOKIE) cookie:String):Observable<ShippingDetailModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancelDetailSerial")
    fun getCanselShippingSerials(@Url url:String ,@Body jsonObject: JsonObject,
                           @Header(Utils.COOKIE) cookie:String):Observable<List<ShippingSerialModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancelDetailSerialInsert")
    fun insertCanselShippingDetail(@Url url:String ,@Body jsonObject: JsonObject,
                             @Header(Utils.COOKIE) cookie:String):Single<AddShippingSerialModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancelDetailSerialRemove")
    fun removeCanselShippingSerial(@Url url:String ,@Body jsonObject: JsonObject,
                             @Header(Utils.COOKIE) cookie:String):Observable<RemoveShippingSerialModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"ShippingCancel")
    fun loadingCanselFinish(@Url url:String ,@Body jsonObject: JsonObject,
                      @Header(Utils.COOKIE) cookie:String):Observable<LoadingFinishModel>


    //---------------------------------Transfer-Task-------------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"SourceLocationTransfer")
    fun sourceLocationTransfer(@Url url:String ,@Body jsonObject: JsonObject,
                            @Header(Utils.COOKIE) cookie:String,
                               @Header("page") page:Int,
                               @Header("rows") rows:Int,
                               @Header("sort") sort:String,
                               @Header("order") asc:String,):Observable<SourceLocationTransferModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"DestinationLocationTransfer")
    fun destinationLocationTransfer(@Url url:String ,@Body jsonObject: JsonObject,
                               @Header(Utils.COOKIE) cookie:String):Observable<List<DestinyLocationTransfer>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"LocationTransferSubmit")
    fun submitLocationTransfer(@Url url:String ,@Body jsonObject: JsonObject,
                                    @Header(Utils.COOKIE) cookie:String):Observable<LocationTransferSubmit>
    @Headers(Utils.CONTENT_TYPE)
    @POST//"LocationTransferTask")
    fun locationTransferTaskList(@Url url:String ,@Body jsonObject: JsonObject,
                                 @Header("page") page:Int,
                                 @Header("rows") rows:Int,
                                 @Header("sort") sort:String,
                                 @Header("order") asc:String,
                                 @Header(Utils.COOKIE) cookie:String)
        :Observable<LocationTransferTaskModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"LocationTransferComplete")
    fun completeLocationTransfer(@Url url:String ,@Body jsonObject: JsonObject,
                                 @Header(Utils.COOKIE) cookie:String):Observable<CompleteLocationTransfer>

    //-------------------------Inventory--------------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"InventoryTypeModifiedTask")
    fun inventoryModifiedTask(@Url url:String ,@Body jsonObject: JsonObject,
                              @Header("page") page:Int,
                              @Header("rows") rows:Int,
                              @Header("sort") sort:String,
                              @Header("order") asc:String,
                              @Header(Utils.COOKIE) cookie:String):Observable<InventoryModifedModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"InventoryTypeModifyComplete")
    fun completeInventoryModify(@Url url:String ,@Body jsonObject: JsonObject,
                                 @Header(Utils.COOKIE) cookie:String):Observable<CompleteInventoryModifyModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"InventoryTypeModifyWithoutSerialComplete")
    fun inventoryModifyWitoutComp(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(Utils.COOKIE) cookie:String):Observable<InventoryVerifyWithoutComp>

    //----------------------------Inventory Report------------------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"LocationInventory")
    fun reportLocationInventory(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(ApiUtils.Page) page:Int,
                                @Header(ApiUtils.Rows) rows:Int,
                                @Header(ApiUtils.Sort) sort:String,
                                @Header(ApiUtils.Order) order:String,
                                @Header(Utils.COOKIE) cookie:String)
                        :Observable<ReportLocationInventory>
    @Headers(Utils.CONTENT_TYPE)
    @POST//"LocationInventoryByProduct")
    fun reportLocationInventByProduct(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(Utils.COOKIE) cookie:String):Observable<List<LocationInventoryByProduct>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"SerialInventory")
    fun reportSerialInventory(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(ApiUtils.Page) page:Int,
                                @Header(ApiUtils.Rows) rows:Int,
                                @Header(ApiUtils.Sort) sort:String,
                                @Header(ApiUtils.Order) order:String,
                                @Header(Utils.COOKIE) cookie:String)
            :Observable<SerialInventoryModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"SerialInventoryProduct")
    fun reportSerialInventoryProduct(@Url url:String ,@Body jsonObject: JsonObject,
                              @Header(ApiUtils.Page) page:Int,
                              @Header(ApiUtils.Rows) rows:Int,
                              @Header(ApiUtils.Sort) sort:String,
                              @Header(ApiUtils.Order) order:String,
                              @Header(Utils.COOKIE) cookie:String)
            :Observable<SerialInvProductModel>



    @Headers(Utils.CONTENT_TYPE)
    @POST//"PickAndPutReport")
    fun pickAndPutReport(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(ApiUtils.Page) page:Int,
                                @Header(ApiUtils.Rows) rows:Int,
                                @Header(ApiUtils.Sort) sort:String,
                                @Header(ApiUtils.Order) order:String,
                                @Header(Utils.COOKIE) cookie:String):Observable<PickAndPutModel>

    //-----------------------------Cargo---------------------------
    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getCargoList(@Url url:String ,@Body jsonObject: JsonObject,
                     @Header(ApiUtils.Page) page:Int,
                     @Header(ApiUtils.Rows) rows:Int,
                     @Header(ApiUtils.Sort) sort:String,
                     @Header(ApiUtils.Order) order:String,
                     @Header(Utils.COOKIE) cookie:String):Single<CargoModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getCargoDetailLocation(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Single<List<LocationModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//CargoGetItem
    fun getCargoItem(@Url url: String, @Body jsonObject: JsonObject, @Header(Utils.COOKIE) cookie: String) : Single<CargoRow>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getCargoDetailList(@Url url:String ,@Body jsonObject: JsonObject,
                     @Header(ApiUtils.Page) page:Int,
                     @Header(ApiUtils.Rows) rows:Int,
                     @Header(ApiUtils.Sort) sort:String,
                     @Header(ApiUtils.Order) order:String,
                     @Header(Utils.COOKIE) cookie:String):Single<CargoDetailModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun driverTaskSubmit(@Url url: String,@Body jsonObject: JsonObject,
                         @Header(Utils.COOKIE) cookie:String)
        :Single<DriverTaskDoneModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun ChangeDriverTaskDone(@Url url: String,@Body jsonObject: JsonObject,
                         @Header(Utils.COOKIE) cookie:String)
            :Single<DriverTaskDoneModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun driverTaskRemove(@Url url: String,@Body jsonObject: JsonObject,
                             @Header(Utils.COOKIE) cookie:String)
            :Single<DriverTaskDoneModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getMyCargoDetailList(@Url url:String ,@Body jsonObject: JsonObject,
                           @Header(ApiUtils.Page) page:Int,
                           @Header(ApiUtils.Rows) rows:Int,
                           @Header(ApiUtils.Sort) sort:String,
                           @Header(ApiUtils.Order) order:String,
                           @Header(Utils.COOKIE) cookie:String):Single<MyCargoDetailModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getMyCargoList(@Url url:String ,@Body jsonObject: JsonObject,
                     @Header(ApiUtils.Page) page:Int,
                     @Header(ApiUtils.Rows) rows:Int,
                     @Header(ApiUtils.Sort) sort:String,
                     @Header(ApiUtils.Order) order:String,
                     @Header(Utils.COOKIE) cookie:String):Single<MyCargoModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"CargoDetailWorkerSubmit"
    fun cargoDetailWorkerSubmit(@Url url: String,@Body jsonObject: JsonObject,
                         @Header(Utils.COOKIE) cookie:String)
            :Single<DriverTaskDoneModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"CargoDetailWorkerRemove"
    fun cargoDetailWorkerRemove(@Url url: String,@Body jsonObject: JsonObject,
                         @Header(Utils.COOKIE) cookie:String)
            :Single<DriverTaskDoneModel>



    //----------------------Product Without Master-------------------------------------------
    @Headers(Utils.CONTENT_TYPE)
    @POST//"ProductWithoutMaster")
    fun getProductWithoutMaster(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(ApiUtils.Page) page:Int,
                                @Header(ApiUtils.Rows) rows:Int,
                                @Header(ApiUtils.Sort) sort:String,
                                @Header(ApiUtils.Order) order:String,
                                @Header(Utils.COOKIE) cookie:String):
                        Observable<List<ProductWithoutMasterModel>>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"UnitOfMeasureSubmit")
    fun unitOfMeasureSubmtit(@Url url:String ,@Body jsonObject: JsonObject,
                                @Header(Utils.COOKIE) cookie:String):
            Observable<UnitOfMeasureSubmitModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"Pallet")
    fun palletList(@Url url:String ,@Header(Utils.COOKIE) cookie:String):
            Observable<List<PalletModel>>

    //-------------------------------------WaitTruck----------------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"WaitTruckLoading")
    fun waitTruckLoading(@Url url:String ,@Body jsonObject: JsonObject,
                         @Header(ApiUtils.Page) page:Int,
                         @Header(ApiUtils.Rows) rows:Int,
                         @Header(ApiUtils.Sort) sort:String,
                         @Header(ApiUtils.Order) order:String,
                         @Header(Utils.COOKIE) cookie: String,)
        :Observable<WaitTruckLoadingModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"WaitTruckLoadingNotAssign")
    fun waitTruckLoadingNotAssign(
        @Url url: String,
        @Body jsonObject: JsonObject,
        @Header(ApiUtils.Page) page:Int,
        @Header(ApiUtils.Rows) rows:Int,
        @Header(ApiUtils.Sort) sort:String,
        @Header(ApiUtils.Order) order:String,
        @Header(Utils.COOKIE) cookie: String,
    ) : Observable<WaitTruckLoadingModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"WaitTruckLoadingNotAssignDetail")
    fun waitTruckLoadingNotAssignDetail(@Url url:String ,@Body jsonObject: JsonObject,
                          @Header("page") page:Int,
                          @Header("rows") rows:Int,
                          @Header("sort") sort:String,
                          @Header("order") asc:String,
                          @Header(Utils.COOKIE) cookie:String):Observable<ShippingDetailModel>




    @Headers(Utils.CONTENT_TYPE)
    @POST//"TruckLoadingAssign")
    fun truckLoadingAssign(@Url url:String ,@Body jsonObject: JsonObject,@Header(Utils.COOKIE) cookie: String)
            :Observable<TruckLoadingAssignModel>

    //-----------------------------------------Insert Serial-------------------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Warehouse")
    fun getWarehouse(@Url url:String ,@Header(Utils.COOKIE) cookie: String):Observable<List<WarehouseModel>>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"Owner")
    fun getOwner(@Url url:String ,@Header(Utils.COOKIE) cookie: String):Observable<List<OwnerModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Products")
    fun getProducts(@Url url:String ,@Body jsonObject: JsonObject,
                 @Header(Utils.COOKIE) cookie: String):Observable<List<ProductModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"InsertSerial")
    fun insertSerial(@Url url:String ,@Body jsonArray: JsonArray,
                     @Header(Utils.COOKIE) cookie: String):Observable<InsertSerialModel>

    //------------------------------Notification---------------------------- - -

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Notification")
    fun notif(@Url url:String ,@Header(Utils.COOKIE) cookie: String):Single<List<NotificationModel>>

    //----------------------------------Tracking-------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Products")
    fun getTrackingProducts(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String):
            Single<List<ProductModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"GetSerialInfo")
    fun getSerialInfo(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String):
            Single<GetSerialInfoModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"Labeling")
    fun labelling(
        @Url url:String ,
        @Body jsonArray: JsonArray,
        @Header(Utils.COOKIE) cookie: String):
            Single<LabellingModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Rework")
    fun rework(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String):
            Single<ReworkModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"Customers")
    fun getCustomers(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String):
            Observable<List<CustomerModel>>

    @Headers(Utils.CONTENT_TYPE)
    @POST//"LeftDock")
    fun leftDock(
        @Url url: String,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String
    ): Single<LeftDockModel>

    //---------STOCK---------------------------------

    @Headers(Utils.CONTENT_TYPE)
    @POST//"StockTakings")
    fun stockTakingList(@Url url:String ,@Body jsonObject: JsonObject,
                        @Header(ApiUtils.Page) page:Int,
                        @Header(ApiUtils.Rows) rows:Int,
                        @Header(ApiUtils.Sort) sort:String,
                        @Header(ApiUtils.Order) order:String,
                        @Header(Utils.COOKIE) cookie: String,)
            :Observable<StockTakeModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//"StockTakingListLocation")
    fun stockTakingLocationList(
                @Url url:String ,
                @Body jsonObject: JsonObject,
                @Header(ApiUtils.Page) page:Int,
                @Header(ApiUtils.Rows) rows:Int,
                @Header(ApiUtils.Sort) sort:String,
                @Header(ApiUtils.Order) order:String,
                @Header(Utils.COOKIE) cookie: String,)
            :Observable<StockTakingLocationModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//StockTakingLocationInsert
    fun stockTakingLocationInsert(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String,
    ):Single<StockLocationInsertModel>


    @Headers(Utils.CONTENT_TYPE)
    @POST//StockTakingCount
    fun stockTakingCount(
        @Url url:String ,
        @Body jsonObject: JsonObject,
        @Header(Utils.COOKIE) cookie: String,
    ):Single<StockTakingCountModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST//StockLocation
    fun stockLocation(
        @Url url: String,
        @Body jsonObject: JsonObject,
        @Header(ApiUtils.Page) page:Int,
        @Header(ApiUtils.Rows) rows:Int,
        @Header(ApiUtils.Sort) sort:String,
        @Header(ApiUtils.Order) order:String,
        @Header(Utils.COOKIE) cookie: String
    ) : Observable<StockLocationModel>


    //get current version
    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getCurrentVersionInfo(
        @Url url: String,
        @Header(Utils.COOKIE) cookie: String
    ) : Single<VersionInfoModel>



    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun getDocks(
        @Url url: String,
        @Body jsonObject: JsonObject,
        @Header(ApiUtils.Page) page:Int,
        @Header(ApiUtils.Rows) rows:Int,
        @Header(ApiUtils.Sort) sort:String,
        @Header(ApiUtils.Order) order:String,
        @Header(Utils.COOKIE) cookie: String,
    ) : Observable<DockModel>

    @Headers(Utils.CONTENT_TYPE)
    @POST
    fun setUseDock(@Url url: String,@Body jsonObject: JsonObject,@Header(Utils.COOKIE) cookie: String) : Single<SetUseDockModel>


}