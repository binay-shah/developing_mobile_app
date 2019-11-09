package com.example.popularmovies.utils

import androidx.lifecycle.LiveData
import com.example.popularmovies.data.remote.network.ApiErrorResponse
import com.example.popularmovies.data.remote.network.ApiResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

//class ApiResponseCallAdapter<R>(
//    private val responseType: Type
//) : CallAdapter<R, ApiResponse<R>> {
//
//    override fun responseType() = responseType

//    override fun adapt(call: Call<R>): ApiResponse<R> {
//
//
//        call.enqueue(object : Callback<R> {
//            override fun onFailure(call: Call<R>, t: Throwable) {
//                ApiResponse.create<R>(t)
//            }
//
//            override fun onResponse(call: Call<R>, response: Response<R>) {
//
//                ApiResponse.create<R>(response)
//            }
//            })
//
//        //return ApiResponse<R>()
//        return
//    }
//}