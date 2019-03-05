package com.erfolgi.dissolvedoxygenmonitor.presenter

import android.util.Log
import com.erfolgi.dissolvedoxygenmonitor.api.APIClient
import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed
import com.erfolgi.dissolvedoxygenmonitor.model.read.ReadObject
import com.erfolgi.dissolvedoxygenmonitor.view.HistoryView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HistoryPresenter (var historyView: HistoryView){
    lateinit var apiClient: APIClient
    lateinit var callFeed: Call<ReadObject>

    fun callview(items:MutableList<Feed>){
        historyView.showFeed(items)
    }

    fun getFeed(){
        val apiClient = APIClient()
        val apiCall: Call<ReadObject>
        apiCall = apiClient.service.requestRead2()
        apiCall.enqueue(object : Callback<ReadObject> {

            override fun onResponse(call : Call<ReadObject>, response : Response<ReadObject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    var objek= response.body()
                    var items= objek?.feeds as MutableList<Feed>
                    items.reverse()
                    callview(items)
                }
            }
            override fun  onFailure(call : Call<ReadObject>, t: Throwable) {
                Log.e("gedebugcoy",t.toString())
                getFeed()
            }
        })
    }
    fun cancelRequest(){
        callFeed.cancel()
    }
}