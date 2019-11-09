package com.example.popularmovies.data.model

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*


class Converters{

    val moshi = Moshi.Builder().build()
    var typeA = Types.newParameterizedType(List::class.java, Genre::class.java)
    val adapter: JsonAdapter<List<Genre>> = moshi.adapter(typeA)

    @TypeConverter
    fun fromGenresList(list :List<Genre>) : String{
        return adapter.toJson(list)
    }

    @TypeConverter
    fun toGenresList(genres: String): List<Genre>?{
        if (genres == null) {
            return Collections.emptyList();
        }
        return adapter.fromJson(genres)
    }
}