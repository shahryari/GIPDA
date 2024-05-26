
import com.google.gson.annotations.SerializedName

data class PickingDetailRow(
    @SerializedName("GTINCode")
    val gTINCode: String ?,
    @SerializedName("Locations")
    val locations: String,
    @SerializedName("ProductCode")
    val productCode: String,
    @SerializedName("ProductTitle")
    val productTitle: String,
    @SerializedName("SumQuantity")
    val sumQuantity: Int,
    @SerializedName("InvTypeTitle")
    val invTypeTitle:String,
    @SerializedName("OwnerCode")
    val ownerCode:String,

)