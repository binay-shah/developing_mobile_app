package com.example.popularmovies.data.remote.network

import com.example.popularmovies.BuildConfig
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.model.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient


import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://api.themoviedb.org/3/"

enum class MovieApiFilter(val value : String){
    POPULAR("popular"), TOP_RATED("top_rated")}


interface MovieService{
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MovieResponse

    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    suspend fun getMovieDetails(@Path("id") id: Long): Response<MovieProperty>
}

object MovieApi {
    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)


    private val interceptor = object : Interceptor{
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var originalRequest = chain.request()

            var url = originalRequest.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            var request = originalRequest.newBuilder()
                .url(url)
                .build()

            return chain.proceed(request)
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(interceptor)
        .build()

    private val moshi = Moshi.Builder()
        //.add(GenreAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    val getMovieService: MovieService by lazy {
        retrofit.create(MovieService::class.java)
    }
}


