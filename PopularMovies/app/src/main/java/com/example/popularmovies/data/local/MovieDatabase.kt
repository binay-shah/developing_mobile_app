package com.example.popularmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.popularmovies.data.local.dao.MovieDao
import com.example.popularmovies.data.local.dao.TrailersDao
import com.example.popularmovies.data.model.Converters
import com.example.popularmovies.data.model.Movie
import com.example.popularmovies.data.model.Trailer


@Database(entities = [Movie::class, Trailer::class], version = 3)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val trailersDao: TrailersDao

    companion object{
        private lateinit var INSTANCE: MovieDatabase

        fun getDatabase(context: Context): MovieDatabase {

            synchronized(MovieDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movies.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}



