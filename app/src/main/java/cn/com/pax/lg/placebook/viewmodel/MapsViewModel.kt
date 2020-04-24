package cn.com.pax.lg.placebook.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cn.com.pax.lg.placebook.model.Bookmark
import cn.com.pax.lg.placebook.repository.BookmarkRepo
import cn.com.pax.lg.placebook.util.ImageUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place

class MapsViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = "MapsViewModel"
    // 2
    private var bookmarkRepo: BookmarkRepo = BookmarkRepo(getApplication())
    private var bookmarks: LiveData<List<BookmarkMarkerView>>? = null

    fun addBookmarkFromPlace(place: Place, image: Bitmap?) {
// 4
            val bookmark = bookmarkRepo.createBookmark()
            bookmark.placeId = place.id
            bookmark.name = place.name.toString()
            bookmark.longitude = place.latLng?.longitude ?: 0.0
            bookmark.latitude = place.latLng?.latitude ?: 0.0
            bookmark.phone = place.phoneNumber.toString()
            bookmark.address = place.address.toString()
// 5
            val newId = bookmarkRepo.addBookmark(bookmark)
            image?.let { bookmark.setImage(it, getApplication()) }
            Log.i(TAG, "New bookmark $newId added to the database.")
    }

    private fun mapBookmarksToMarkerView() {
        bookmarks = Transformations.map(bookmarkRepo.allBookmarks) {
            it.map { bookmark -> bookMarkToMarkerView(bookmark) }
        }
    }

    fun getBookmarkMarkerViews() :
            LiveData<List<BookmarkMarkerView>>? {
        if (bookmarks == null) {
            mapBookmarksToMarkerView()
        }
        return bookmarks
    }

    private fun bookMarkToMarkerView(bookmark: Bookmark) : MapsViewModel.BookmarkMarkerView {
        return MapsViewModel.BookmarkMarkerView(bookmark.id, LatLng(bookmark.latitude, bookmark.longitude), bookmark.name, bookmark.phone)
    }

    data class BookmarkMarkerView(
        var id: Long? = null,
        var location: LatLng = LatLng(0.0, 0.0),
        var name: String = "",
        var phone: String = "") {
        fun getImage(context: Context): Bitmap? {
            id?.let {
                return ImageUtils.loadBitmapFromFile(context, Bookmark.generateImageFilename(it))
            }
            return null
        }
    }
}