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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiDataSource() :DataSource
{

    private fun apiProvider(): ApiService  {
        return ApiClient.getRetrofit()!!.create(ApiService::class.java)
    }
    override fun login(baseUrl:String,jsonObject: JsonObject): Single<LoginModel> {
        return apiProvider().login(
            baseUrl+"login",
            jsonObject)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun dashboardInfo(baseUrl:String,cookie: String): Observable<List<DashboardInfoModel>> {
        return apiProvider().dashboardInfo(baseUrl+"DashboardWorkerTask",cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override  fun getReceivingList(baseUrl:String,cookie: String, jsonObject: JsonObject,
                                   page:Int,
                                   rows:Int,
                                   sort:String,
                                   asc:String): Observable<ReceivingModel> {
        return apiProvider().getReceivingList(baseUrl+"ReceivingList",cookie,jsonObject, page, rows, sort, asc)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCurrentUser(baseUrl:String,cookie: String): Observable<CurrentUserModel> {
        return apiProvider().getCurrentUser(baseUrl+"CurrentUser",cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun catalogList(baseUrl:String,cookie: String): Observable<List<CatalogModel>> {
        return apiProvider().catalogList(baseUrl+"CatalogValues",cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override fun getReceiveDetailList(baseUrl:String,jsonObject: JsonObject,
                                      page:Int,
                                      rows:Int,
                                      sort:String,
                                      asc:String, cookie: String):
            Observable<ReceiveDetailModel> {
        return apiProvider().getReceiveDetailList(baseUrl+"ReceivingDetailList"
            ,jsonObject,page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getReceiveDetailSerialList(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Observable<List<ReceivingDetailSerialModel>> {
        return apiProvider().getReceiveDetailSerialList(baseUrl+"ReceivingDetailSerialList",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun addReceiveDetailSerial(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Single<AddReceivingDetailSerialModel> {
        return apiProvider().addReceiveDetailSerial(baseUrl+"ReceivingDetailSerialInsert",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveSerialModel> {
        return apiProvider().removeSerial(baseUrl+"ReceivingDetailSerialRemove",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun countReceiveDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ReceiveDetailCountModel> {
        return  apiProvider().countReceiveDetail(baseUrl+"ReceivingDetailCount",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun confirmReceiveDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ConfirmReceiveDetailModel> {
        return  apiProvider().confirmReceiveDetail(baseUrl+"ReceivingDetailSerialConfirm",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun putawayTruckList(
        baseUrl:String,
        cookie: String,
        jsonObject: JsonObject,
        page:Int,
        rows:Int,
        sort:String,
        asc:String
    ): Observable<PutawayTruckModel> {
        return apiProvider().putawayTruckList(baseUrl+"PutawayTruck",cookie,jsonObject,page,rows, sort,asc)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

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
        return apiProvider().putawayTruckDetail(baseUrl+"PutawayTruckDetail",jsonObject,page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun completePutawayModel(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePutawayModel> {
        return apiProvider().completePutawayModel(baseUrl+"PutawayComplete",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return  apiProvider().serialReceiptOnPutaway(
            baseUrl+"SerialReceiptOnPutaway",
            jsonObject,
            page,
            rows,
            sort,
            asc,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().mySerialReceiptOnPutaway(
            baseUrl+"MySerialReceiptOnPutaway",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun assignSerialPutaway(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiProvider().assignSerialPutaway(
            baseUrl+"SerialReceiptAssign",
            jsonObject,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun serialReceiptRemoveFromMe(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiProvider().serialReceiptRemoveFromMe(
            baseUrl+"SerialReceiptRemoveFromMe",
            jsonObject,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().mySerialReceiptDetailOnPutaway(
            baseUrl+"MySerialReceiptDetailOnPutaway",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().receiptDetailLocation(
            baseUrl+"ReceiptDetailLocation",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().receiptDetailSerial(
            baseUrl+"ReceiptDetailSerials",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun receiptDetailScanSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiProvider().receiptDetailScanSerial(
            baseUrl+"ReceiptDetailScanSerial",
            jsonObject,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun receiptDetailSerialRemove(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SerialPutawayAssignModel> {
        return apiProvider().receiptDetailSerialRemove(
            baseUrl+"ReceiptDetailSerialRemove",
            jsonObject,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun pickTruckList(baseUrl:String,jsonObject: JsonObject,
                               page:Int,
                               rows:Int,
                               sort:String,
                               asc:String,cookie: String): Observable<PickingTruckModel> {
        return apiProvider().pickTruckList(baseUrl+"PickingListGrouped",jsonObject, page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().pickTruckDetailList(baseUrl+"PickingListGroupedDetail"
            ,jsonObject, page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun completePicking(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePickingModel> {
        return apiProvider().completePicking(baseUrl+"PickingComplete",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().serialPickTruckList(baseUrl+"PickingListGroupedSerialBase",jsonObject, page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().serialPickTruckDetailList(baseUrl+"PickingListGroupedDetailSerialBase"
            ,jsonObject, page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun completeSerialPicking(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompletePickingModel> {

        return apiProvider().completePicking(baseUrl+"PickingComplete",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPickingSerials(
        baseUrl: String,
        jsonObject: JsonObject,
        page: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<GetPickingSerialsModel> {
        return apiProvider().getPickingSerial(
            baseUrl+"GetPickingSerials",
            jsonObject,
            page,
            10,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun scanPickingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<ScanPickingSerialModel> {
        return apiProvider().scanPickingSerial(
            baseUrl+"ScanPickingSerial",
            jsonObject,
            cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun finishPickingSerial(
        baseUrl: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<PickingFinishSerialBaseModel> {
        return apiProvider().finishPickingSerial(baseUrl+"PickingFinishSerialBase",jsonObject,cookie)
    }

    override fun checkTruckList(baseUrl:String,cookie: String): Observable<List<CheckTruckModel>> {
        return apiProvider().checkTruckList(baseUrl+"CheckTruck",cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun confirmCheckTruck(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<ConfirmCheckTruckModel> {
        return apiProvider().confirmCheckTruck(baseUrl+"ConfirmTruck",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun denyCheckTruck(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<DenyCheckTruckModel> {
        return apiProvider().denyCheckTruck(baseUrl+"DenyTruck",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun shippingTruckList(
                        baseUrl:String,
                        jsonObject: JsonObject,
                                   page:Int,
                                    rows:Int,
                                    sort:String,
                                    asc:String, cookie: String): Observable<ShippingTruckModel> {
        return apiProvider().shippingTruckList(baseUrl+"ShippingTruck",jsonObject, page, rows, sort, asc,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getShippingDetail(baseUrl+"ShippingTruckDetail",jsonObject,page, rows, sort, asc, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getShippingSerials(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ShippingSerialModel>> {
        return apiProvider().getShippingSerials(baseUrl+"ShippingDetailSerial",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<AddShippingSerialModel> {
        return apiProvider().insertShippingDetail(baseUrl+"ShippingDetailSerialInsert",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeShippingSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveShippingSerialModel> {
        return  apiProvider().removeShippingSerial(baseUrl+"ShippingDetailSerialRemove",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadingFinish(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LoadingFinishModel> {
        return  apiProvider().loadingFinish(baseUrl+"LoadingFinish",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun revokLocation(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<DestinyLocationTransfer>> {
        return  apiProvider().revokLocation(url+"RevokLocation",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun revoke(url: String, jsonObject: JsonObject, cookie: String): Single<RevokeModel> {
        return apiProvider().revoke(url+"Revoke",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCustomerInShipping(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<CustomerInShipping>> {
        return apiProvider().getCustomerInShipping(
            url+"CustomerInShipping", jsonObject, cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun setShippingColor(url: String, jsonObject: JsonObject, cookie: String) : Single<SetShippingAddressColorModel>{
        return apiProvider().setShippingAddressColor(url+"SetShippingAddressColor", jsonObject, cookie).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getColorList(url: String, cookie: String): Single<List<ColorModel>> {
        return apiProvider().getColors(url+"CustomerColorList",cookie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getShippingDetailCustomers(url: String, jsonObject: JsonObject, cookie: String) : Single<List<CustomerModel>> {
        return apiProvider().getShippingDetailCustomers(url+"ShippingDetailCustomers",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun truckLoadingRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<TruckLoadingRemoveModel> {
        return apiProvider().truckLoadingRemove(url+"TruckLoadingRemove",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCargoItem(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<CargoRow> {
        return apiProvider().getCargoItem(url+"CargoGetItem",jsonObject,cookie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCargoDetailLocation(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<LocationModel>> {
        return apiProvider().getCargoDetailLocation(url+"MyCargoDetailLocation",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getCargoList(url+"Cargo",jsonObject,page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getCargoDetailList(url+"CargoDetail",jsonObject,page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getMyCargoList(url+"MyCargo",jsonObject,page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getMyCargoDetailList(url+"MyCargoDetail"
            ,jsonObject,page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun cargoDetailWorkerSubmit(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiProvider().cargoDetailWorkerSubmit(url+"MyCargoDetailSetDone"
            ,jsonObject, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun cargoDetailWorkerRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiProvider().cargoDetailWorkerRemove(url+"MyCargoDetailSetNotDone"
            ,jsonObject, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun driverTaskSubmit(
        url: String,
        jsonObject: JsonObject,
        cookie:String,
    ): Single<DriverTaskDoneModel> {
        return apiProvider().driverTaskSubmit(
            url+"CargoAssignToMe",jsonObject,cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun driverTaskRemove(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiProvider().driverTaskSubmit(
            url+"CargoRemoveFromMe",jsonObject,cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeDriverTaskDone(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<DriverTaskDoneModel> {
        return apiProvider().ChangeDriverTaskDone(
            url+"MyCargoSetFinish",jsonObject,cookie
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override fun shippingCanselTruckList(
                        baseUrl:String
                        ,jsonObject: JsonObject,
                                         page:Int,
                                         rows:Int,
                                         sort:String,
                                         asc:String,cookie: String): Observable<ShippingTruckModel> {
        return  apiProvider().shippingCanselTruckList(baseUrl+"ShippingCancelTruck",
            jsonObject, page, rows, sort, asc, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return  apiProvider().getCanselShippingDetail(baseUrl+"ShippingCancelDetail",
            jsonObject, page, rows, sort, asc, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCanselShippingSerials(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ShippingSerialModel>> {
        return  apiProvider().getCanselShippingSerials(baseUrl+"ShippingCancelDetailSerial"
            ,jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertCanselShippingDetail(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<AddShippingSerialModel> {
        return  apiProvider().insertCanselShippingDetail(baseUrl+"ShippingCancelDetailSerialInsert",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeCanselShippingSerial(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<RemoveShippingSerialModel> {
        return  apiProvider().removeCanselShippingSerial(baseUrl+"ShippingCancelDetailSerialRemove"
            ,jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadingCanselFinish(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LoadingFinishModel> {
        return  apiProvider().loadingCanselFinish(baseUrl+"ShippingCancel",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().sourceLocationTransfer(baseUrl+"SourceLocationTransfer"
            ,jsonObject,cookie,page, rows, sort, asc)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun destinationLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<DestinyLocationTransfer>> {
        return apiProvider().destinationLocationTransfer(baseUrl+"DestinationLocationTransfer"
            ,jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun submitLocationTransfer(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<LocationTransferSubmit> {
        return apiProvider().submitLocationTransfer(baseUrl+"LocationTransferSubmit",jsonObject,cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun locationTransferTaskList(
        baseUrl:String,jsonObject: JsonObject,
                                          page:Int,
                                          rows:Int,
                                          sort:String,
                                          asc:String,
                                          cookie:String):

            Observable<LocationTransferTaskModel> {
        return apiProvider().locationTransferTaskList(baseUrl+"LocationTransferTask",jsonObject, page, rows, sort, asc, cookie)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun completeLocationTransfer(baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompleteLocationTransfer> {
        return apiProvider().completeLocationTransfer(baseUrl+"LocationTransferComplete",jsonObject, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun inventoryModifiedTask(baseUrl:String, jsonObject: JsonObject,
                                       page:Int,
                                          rows:Int,
                                        sort:String,
                                         asc:String,
                                       cookie: String): Observable<InventoryModifedModel> {
        return apiProvider().inventoryModifiedTask(baseUrl+"InventoryTypeModifiedTask",jsonObject, page, rows, sort, asc, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun completeInventoryModify(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<CompleteInventoryModifyModel> {
        return apiProvider().completeInventoryModify(baseUrl+"InventoryTypeModifyComplete",jsonObject, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun inventoryModifyWitoutComp(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<InventoryVerifyWithoutComp> {
        return apiProvider().inventoryModifyWitoutComp(baseUrl+"InventoryTypeModifyWithoutSerialComplete",jsonObject, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().reportLocationInventory(baseUrl+"LocationInventory"
            ,jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



    override fun reportLocationInventByProduct(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<LocationInventoryByProduct>> {
        return apiProvider().reportLocationInventByProduct(baseUrl+"LocationInventoryByProduct"
            ,jsonObject, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().reportSerialInventory(baseUrl+"SerialInventory"
            ,jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun reportSerialInventoryProduct(
        url:String,
        jsonObject: JsonObject,
        page: Int,
        rows: Int,
        sort: String,
        order: String,
        cookie: String
    ): Observable<SerialInvProductModel> {
        return apiProvider().reportSerialInventoryProduct(url+"SerialInventoryProduct"
            ,jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().pickAndPutReport(baseUrl+"PickAndPutReport"
            ,jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getProductWithoutMaster(baseUrl+"ProductWithoutMaster"
            ,jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun unitOfMeasureSubmtit(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<UnitOfMeasureSubmitModel> {
        return apiProvider().unitOfMeasureSubmtit(baseUrl+"UnitOfMeasureSubmit",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun palletList(baseUrl:String,cookie: String): Observable<List<PalletModel>> {
        return apiProvider().palletList(baseUrl+"Pallet",cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        val api =if (isCompleted) apiProvider()::waitTruckLoading else apiProvider()::waitTruckLoadingNotAssign
        return api(baseUrl+if(isCompleted)"WaitTruckLoadingNotAssign" else "WaitTruckLoading",jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().waitTruckLoadingNotAssignDetail(baseUrl+"WaitTruckLoadingNotAssignDetail",jsonObject, page, rows, sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun truckLoadingAssign(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<TruckLoadingAssignModel> {
        return apiProvider().truckLoadingAssign(baseUrl+"TruckLoadingAssign",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getWarehouse(baseUrl:String,cookie: String): Observable<List<WarehouseModel>> {
        return apiProvider().getWarehouse(baseUrl+"Warehouse",cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getOwner(baseUrl:String,cookie: String): Observable<List<OwnerModel>> {
        return apiProvider().getOwner(baseUrl+"Owner",cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getProducts(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<ProductModel>> {
        return apiProvider().getProducts(baseUrl+"Products",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertSerial(
        baseUrl:String,
        jsonArray: JsonArray,
        cookie: String
    ): Observable<InsertSerialModel> {
        return apiProvider().insertSerial(baseUrl+"InsertSerial",jsonArray,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun notif(baseUrl:String,cookie: String): Single<List<NotificationModel>> {
        return  apiProvider().notif(baseUrl+"Notification",cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTrackingProducts(
        baseUrl:String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<List<ProductModel>> {
        return  apiProvider().getTrackingProducts(baseUrl+"Products",jsonObject,cookie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    override fun getSerialInfo(baseUrl:String,jsonObject: JsonObject, cookie: String):
            Single<GetSerialInfoModel>{
        return  apiProvider().getSerialInfo(baseUrl+"GetSerialInfo",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun labelling(
        baseUrl:String,
        jsonArray: JsonArray, cookie: String): Single<LabellingModel> {
        return  apiProvider().labelling(baseUrl+"Labeling",jsonArray,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun rework(baseUrl:String,jsonObject: JsonObject, cookie: String): Single<ReworkModel> {
        return  apiProvider().rework(baseUrl+"Rework",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCustomers(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Observable<List<CustomerModel>> {
        return  apiProvider().getCustomers(url+"Customers",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun leftDock(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<LeftDockModel> {
        return  apiProvider().leftDock(url+"LeftDock",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return  apiProvider().stockTakingList(
            url+"StockTakings",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
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
        return  apiProvider().stockTakingLocationList(
            url+"StockTakingListLocation",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun stockTakingLocationInsert(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<StockLocationInsertModel> {
        return  apiProvider().stockTakingLocationInsert(
            url+"StockTakingModify", jsonObject, cookie//TODO(set api Address)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun stockTakingCount(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<StockTakingCountModel> {
        return  apiProvider().stockTakingCount(
            url+"StockTakingCount", jsonObject, cookie
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return  apiProvider().stockLocation(
            url+"StockLocation",
            jsonObject,
            page,
            rows,
            sort,
            order,
            cookie
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCurrentVersionInfo(url: String, cookie: String) : Single<VersionInfoModel>{
        return apiProvider().getCurrentVersionInfo(
            url+"GetCurrentVersionInfo",cookie
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        return apiProvider().getDocks(url+"Docks",jsonObject,page,rows,sort, order, cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun setUseDock(
        url: String,
        jsonObject: JsonObject,
        cookie: String
    ): Single<SetUseDockModel> {
        return apiProvider().setUseDock(url+"SetUseDock",jsonObject,cookie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}