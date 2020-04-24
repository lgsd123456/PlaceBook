package cn.com.pax.lg.placebook.model

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import cn.com.pax.lg.placebook.util.ImageUtils

// 1
@Entity
// 2
data class Bookmark(
// 3
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
// 4
    var placeId: String? = null,
    var name: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var phone: String = "",
    var notes: String = ""
) {
    fun setImage(image: Bitmap, context: Context) {
        id?.let {
            ImageUtils.saveBitmapToFile(context, image, generateImageFilename(it))
        }
    }

    companion object {
        fun generateImageFilename(id: Long): String {
// 4
            return "bookmark$id.png"
        }
    }
}