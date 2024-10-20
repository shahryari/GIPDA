package com.example.warehousemanagment.model.constants

class Utils
{
    companion object {
        val HAS_PRIORITY: String="HasLowPriorityTitle"
        val locationCode: String="locationCode"
        val sumQuantity: String="sumQuantity"
        val PICKING_ID: String="pickingId"
        val Done: String="Done"
        val IsDone: String = "is Done"
        val StockMinusResId="stockMinusResId"
        val customerCount="CustomerCount"
        val PRODUCT_TYPE_SORT: String="produtType"
        val StockTurnTitle: String="StockTurnTitle"
        val StockTurnCode: String="StockTurnCode"
        val StockTakingID: String="StockTakingID"
        val NOT_ASSIGN: String="NotAssign"
        val ShippingAddressId: String="ShippingAddressId"
        val RECEIVE_COUNT_SERIAL="RECEIVE_COUNT_SERIAL"
        val CargoBundle: String="CargoBundle"
        val WhLoadingFinish: String="WhLoadingFinish"
        val PRODUCT_ID: String="ProductID"
        val TaskTime: String="TaskTime"
        val total: String = "Total"
        val doneQuantity: String = "Qty"
        val sumDoneQuantity: String = "Done Qty"
        val showAssignToMe: String = "Show Assign To Me"
//      val FIRST_DOMAIN: String="http://81.12.116.91:8080/mobile/v1/"
        val FIRST_DOMAIN: String="https://gi.bitfinity.ir/mobile/v1/"
//        val FIRST_DOMAIN: String="https://tms.goldiran.ir/mobile/v1/"
//        val FIRST_DOMAIN:String="http://gi.namipaya.com/mobile/v1/"

        val ERROR_401: Int=401
        const val PDA="App: PDA"
        val PUT_TASK_TYPE_ID: Int=2
        val PICK_TASK_TYPE_ID: Int=3
        val PRINT_NAME: String="printName"
        val OTHER_LABLE_PRINTER: String="otherLabelPrinter"
        val TINA_LABLE_PRINTER: String="tinaLabelPrinter"
        val PALLET_NAME: String="PalletName"
        val PALLET_CODE: String="PalletCode"


        val ALLOW_REPETITIVE_SERIAL = "allowRepetitiveSerial"
        val CONSTANT_PART_DOMAIN="/mobile/v1/"
        val PREVIEW_PRINT: String="previewPrint"
        val BARCODE: String="barcode"
        val SERVICE_PRIOD: String="servicePriod"
        val NOTIF_DEFAULT_IME: Int=2
        val WARE_HOUSE_NAME: String="warehouseName"
        val USER_PERMISSIONS: String="userPermission"
        val DB_NAME: String="room_db"
        const val TABLE_PERMSISSION="table_permission"
        val GENERAL_CAT: String="generalCat"
        val REPORT_CAT: String="reportCat"
        val TRANSFER_CAT: String="transferCat"
        val SHIPPING_CAT: String="shippingCat"
        val RECEIVING_CAT: String="receivingCat"
        val shipping: String="shipping"
        val insertSerial: String="insertSerial"
        val locationInventory: String="locationInventory"
        val productWithoutMaster: String="productWithoutMaster"
        val locationTransfer: String="locationTransfer"
        val trackingSerial: String="trackingSerial"
        val shippingCancel: String="shippingCancel"
        val serialReport: String="serialReport"
        val inventoryTypeModifyTask: String="inventoryTypeModifyTask"
        val transferTask: String="transferTask"
        val waitForLoading: String="waitForLoading"
        val putaway: String="putaway"
        val offlineSerial: String="offlineSerial"
        val receiving: String="receiving"
        val picking: String="picking"
        val pickPutReport: String="pickPutReport"
        val LOGIN_MODEL="loginModel"
        val SERIAL_LEN_MIN="serialMinLen"
        val MINUS_ONE: Int=-1
        val DEF_SERIAL_LEN: Int=50
        val SERIAL_LEN_MAX: String="serialLen"
        val UN_VALID: String="unValidChars"
        val BASE_URL: String="baseURl"
        val CUSTOMER_FULL_NAME: String="CustomerFullName"
        val DockAssignTime="DockAssignTime"
        val DriverFullName="DriverFullName"
        val DockCode="DockCode"
        val CreatedOn="CreatedOn"
        val UseDock="UseDock"
        val REQUEST_CAMERA: Int=1000
        val RESULT_SCANNER:String="resultScanner"
        val PLAQUE="plaque"
        val ADAPTER_POSITION: String ="adapterPosition2"
        val QSCANNER_PERMISSIONS= arrayOf(android.Manifest.permission.CAMERA)
        val SUB_SETTING_SORT: Double=0.05
        val DEFUALT_INVENT_REPORT_SEARCH: Long=1000L
        val DELAY_INVENT_REPORT_SEARCH: String="delayInventReport"
        val ASC_ORDER: String="asc"
        val DESC_ORDER: String="desc"
        val PRODUCT_TITLE_SORT="ProductTitle"
        val LOCATION_CODE_SORT="LocationCode"
        val PRODUCT_CODE_SORT="ProductCode"
        val OWNER_SORT="Owner"
        val ROWS=10
        val PAGE_START: Int=1
        val SUB_SETTING_RATIO: Double=.17
        val EXPAND_DURATION: Long=600
        val DRAWER_SUB_REPORT_RATIO: Double=.15
        val DRAWER_SUB_TRANSFER_RATIO: Double=.2
        val DRAWER_SUB_GENERAL_RATIO: Double=0.3
        val DRAWER_SUB_RECEIVE_RATIO: Double=.15
        val DRAWER_SUB_SHIPPING_RATIO: Double=.35
        val Quantity: String="Quantity"
        val OwnerCode="OwnerCode"
        val ProductTitle: String="ProductTitle"
        val ProductCode: String="ProductCode"
        val SourceLocationCode: String="SourceLocationCode"
        val DestinationLcationCode: String="DestinationLcationCode"
        val COUNT_DOWN_INTERVAL: Long=100L
        val DEFUALT_INVENT_SEARCH: Long=1000L
        val DELAY_INVENT_SEARCH: String="delayInventSearch"

        val DOMAIN: String="Domain"
        val CURRENT_USER: String="currentUser"
        val CarType: String="CarType"
        val ShippingId: String="ShippingId"
        val BOLNumber: String="BOLNumber"
        val ShippingNumber: String="ShippingNumber"
        val Date: String="Date"
        val Cardisapprovereason: String="CarDisapproveReason"
        val DELAY_SERIAL=1000L
        val WRONG_NUM: Int=-1
        val CONTAINER_NUMBER: String="ContainerNumber"
        val CAR_TYPE_TITLE: String="carTypeTitle"
        val CREATED_ON: String="CreatedOn"
        val TOKEN_TEST_LENGTH=20
        val RECEIVING_ID="receivingId"
        val RECEIVE_NUMBER="ReceivingNumber"
        val DRIVE_FULLNAME="DriverFullName"
        val DOCK_CODE="dockCode"

        val COOKIE_GLCTEST=".glctest"
        val TOKEN_GLCTEST="token"
        val PREF_NAME="pref5"
        val CheckTruckPERCENT:Float= 0.95F
        const val COOKIE="Cookie"
        const val CONTENT_TYPE="Content-Type: application/json"

        //----------------------Printer----------------------------
        private const val PRINT_META_JOB_LABEL = "@PJL"
        private const val PRINT_META_BREAK = "\r\n"
        private const val ESCAPE_KEY = 0x1b.toChar()
        const val UEL = "$ESCAPE_KEY%-12345X"
        const val ESC_SEQ = "$ESCAPE_KEY%-12345 $PRINT_META_BREAK"
        const val PRINT_META_JOB_START = "$PRINT_META_JOB_LABEL $PRINT_META_BREAK"
        const val PRINT_META_JOB_NAME = "$PRINT_META_JOB_LABEL JOB NAME = 'INBOUND_FINISH' $PRINT_META_BREAK"
        const val PRINT_META_JOB_PAPER_TYPE = "$PRINT_META_JOB_LABEL SET PAPER = A4"
        const val PRINT_META_JOB_COPIES = "$PRINT_META_JOB_LABEL SET COPIES = 1"
        const val PRINT_META_JOB_LANGUAGE = "$PRINT_META_JOB_LABEL ENTER LANGUAGE = PDF $PRINT_META_BREAK"
        const val PRINTER_DEFAULT_PORT: Int = 9100
        const val PRINTER_BUFFER_SIZE: Int = 3000
        //----------------------------------------------------------------------------------

        val PLAQUE_1="plaque1"
        val PLAQUE_2="plaque2"
        val PLAQUE_3="plaque3"
        val PLAQUE_4="plaque4"



    }

}