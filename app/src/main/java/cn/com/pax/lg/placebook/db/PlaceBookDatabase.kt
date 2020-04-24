package cn.com.pax.lg.placebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.com.pax.lg.placebook.model.Bookmark

@Database(entities = arrayOf(Bookmark::class), version = 2)
abstract class PlaceBookDatabase: RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao

    // 3
    companion object {
        // 4
        private var instance: PlaceBookDatabase? = null
        // 5
        fun getInstance(context: Context): PlaceBookDatabase {
            if (instance == null) {
// 6
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlaceBookDatabase::class.java,
                    "PlaceBook")
                    .fallbackToDestructiveMigration()
                    .build()
            }
// 7
            return instance as PlaceBookDatabase
        }
    }
}