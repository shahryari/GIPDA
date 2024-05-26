
import com.google.gson.annotations.SerializedName

data class PickingDetailModel(
    @SerializedName("rows")
    val rows: List<PickingDetailRow>,
    @SerializedName("total")
    val total: Int
)