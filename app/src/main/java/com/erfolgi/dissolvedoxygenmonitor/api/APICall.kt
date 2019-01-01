package com.erfolgi.dissolvedoxygenmonitor.api

import com.erfolgi.dissolvedoxygenmonitor.model.read.ReadObject
import com.erfolgi.dissolvedoxygenmonitor.model.write.Write
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APICall {
//https://api.thingspeak.com/channels/592381/feeds.json?results=1
    @GET("channels/592381/feeds.json?results=5")
    fun requestRead(): Call<ReadObject>
    //channels/592381/fields/1.json?results=1

    @GET("channels/592381/fields/1.json?results=1")
    fun requestReadField1(): Call<ReadObject>

    @GET("channels/592381/fields/2.json?results=1")
    fun requestReadField2(): Call<ReadObject>



    @POST("update.json")
    @FormUrlEncoded
    fun requestWrite(@Field("api_key") key:String?="0RK5LWOWX8F94TNS",
                     @Field("field1") f1:String?,
                     @Field("field2") f2:String?,
                     @Field("field3") f3:String? ): Call<Write>

}