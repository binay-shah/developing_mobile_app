package com.example.popularmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.popularmovies.data.model.Trailer

@Dao
interface TrailersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTrailers( trailers: List<Trailer>)
}
