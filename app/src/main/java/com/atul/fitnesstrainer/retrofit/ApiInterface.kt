package com.atul.fitnesstrainer.retrofit

import com.atul.fitnesstrainer.modelClasses.Program
import com.atul.fitnesstrainer.modelClasses.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("all?category_id=14")
    fun getPrograms(): Call<ResponseData>
}
