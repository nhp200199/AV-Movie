package com.av.movie.data.api.retrofit

import com.av.movie.data.api.model.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkCallAdapter<T: Any, E: Any>(
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, E>
) : CallAdapter<T, Call<NetworkResponse<T, E>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T, E>> {
        return NetworkCall(call, errorConverter)
    }
}