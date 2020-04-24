package cn.com.pax.lg.placebook.ui

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.com.pax.lg.placebook.R
import cn.com.pax.lg.placebook.util.ImageUtils
import cn.com.pax.lg.placebook.viewmodel.MapsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class BookmarkInfoWindowAdapter(val context: Activity): GoogleMap.InfoWindowAdapter {
    // 2
    private val contents: View
    // 3
    init {
        contents = context.layoutInflater.inflate(R.layout.content_bookmark_info, null)
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        val titleView = contents.findViewById<TextView>(R.id.title)
        titleView.text = marker.title ?: ""
        val phoneView = contents.findViewById<TextView>(R.id.phone)
        phoneView.text = marker.snippet ?: ""
        val imageView = contents.findViewById<ImageView>(R.id.photo)
        when(marker.tag) {
            is MapsActivity.PlaceInfo -> {
                imageView.setImageBitmap((marker.tag as MapsActivity.PlaceInfo).image)
            }
            is MapsViewModel.BookmarkMarkerView -> {
                var bookMarkview = marker.tag as MapsViewModel.BookmarkMarkerView
                imageView.setImageBitmap(bookMarkview.getImage(context))
            }
        }

        return contents
    }

}