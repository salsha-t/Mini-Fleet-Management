package com.portfolio.minifleetmanagement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.minifleetmanagement.data.ApiConfig
import com.portfolio.minifleetmanagement.data.response.IntResponse
import com.portfolio.minifleetmanagement.data.response.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel: ViewModel(){

    private val _speedData = MutableLiveData<IntResponse>()
    val speedData: LiveData<IntResponse> = _speedData

    private val _doorStatusData = MutableLiveData<StringResponse>()
    val doorStatusData: LiveData<StringResponse> = _doorStatusData

    private val _engineStatusData = MutableLiveData<StringResponse>()
    val engineStatusData: LiveData<StringResponse> = _engineStatusData

    private val _alertEvent = MutableLiveData<Event<String>>()
    val alertEvent: LiveData<Event<String>> = _alertEvent

    fun getSpeed(){
        val client = ApiConfig.getApiService().getSpeed()
        client.enqueue(object : Callback<IntResponse> {
            override fun onResponse(
                call: Call<IntResponse>,
                response: Response<IntResponse>
            ) {
                if (response.isSuccessful) {
                    _speedData.value = response.body()

                    if((response.body()?.value ?: 0) > 80){
                        _alertEvent.value = Event("High speed exceeds 80 km/h. Please be careful!")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<IntResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDoorStatus(){
        val client = ApiConfig.getApiService().getDoorStatus()
        client.enqueue(object : Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>,
                response: Response<StringResponse>
            ) {
                if (response.isSuccessful) {
                    _doorStatusData.value = response.body()

                    if(response.body()?.value == "open"){
                        _alertEvent.value = Event("The door is open. Please ensure that it is closed!")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getEngineStatus(){
        val client = ApiConfig.getApiService().getEngineStatus()
        client.enqueue(object : Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>,
                response: Response<StringResponse>
            ) {
                if (response.isSuccessful) {
                    _engineStatusData.value = response.body()

                    if(response.body()?.value == "off"){
                        _alertEvent.value = Event("The engine is off. Please make sure the engine is running!")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "DashboardViewModel"
    }
}