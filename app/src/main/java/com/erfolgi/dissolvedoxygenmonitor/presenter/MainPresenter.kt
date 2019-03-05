package com.erfolgi.dissolvedoxygenmonitor.presenter

import android.util.Log
import com.erfolgi.dissolvedoxygenmonitor.api.APICall
import com.erfolgi.dissolvedoxygenmonitor.api.APIClient
import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed
import com.erfolgi.dissolvedoxygenmonitor.model.read.ReadObject
import com.erfolgi.dissolvedoxygenmonitor.model.write.Write
import com.erfolgi.dissolvedoxygenmonitor.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter (var mainView: MainView){
//    lateinit var apiClient:APIClient
//    lateinit var apiCall: Call<Write>
    lateinit var apiClient:APIClient
    lateinit var callFeed: Call<ReadObject>

    fun callview(items:MutableList<Feed>){
        mainView.showFeed(items)
    }

    fun getGraph(){
        Log.d("gedebug","Ready")
        apiClient = APIClient()
        //val apiCall: Call<ReadObject>
        callFeed = apiClient.service.requestRead()
        Log.d("gedebug","Set")
        callFeed.enqueue(object : Callback<ReadObject> {

            override fun onResponse(call : Call<ReadObject>, response : Response<ReadObject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    var objek= response.body()
                    var items= objek?.feeds as MutableList<Feed>
                    mainView.showGraph(items)
                }
            }
            override fun  onFailure(call : Call<ReadObject>, t: Throwable) {
                Log.e("gedebugcoy",t.toString())
            }
        })

    }
    fun getFeed(){
        Log.d("gedebug","Ready")
        val apiClient = APIClient()
        val apiCall: Call<ReadObject>
        apiCall = apiClient.service.requestRead()
        Log.d("gedebug","Set")
        apiCall.enqueue(object : Callback<ReadObject> {

            override fun onResponse(call : Call<ReadObject>, response : Response<ReadObject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    var objek= response.body()
                    var items= objek?.feeds as MutableList<Feed>
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

    fun setFeed(f1:String,f2:String,f3:String,f4:String){
        val apiClient = APIClient()
        val apiCall: Call<Write>
        apiCall = apiClient.service.requestWrite("0RK5LWOWX8F94TNS",f1,f2,f3,f4)
        apiCall.enqueue(object : Callback<Write> {
            override fun onResponse(call : Call<Write>, response : Response<Write>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    Log.d("cek errorer",response.body().toString())
                    var objek= response.body()

                    mainView.showToast("Success")
                }
            }
            override fun  onFailure(call : Call<Write>, t: Throwable) {
                Log.e("gedebugcoy",t.toString())
                setFeed(f1,f2,f3,f4)
                //mainView.showToast("Error :$t")
            }
        })
    }

}