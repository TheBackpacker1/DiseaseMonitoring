package com.example.diseasemonitoring.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diseasemonitoring.api.RetrofitInstance
import com.example.diseasemonitoring.models.Disease
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiseaseViewModels : ViewModel() {

    private val _disease = MutableLiveData<Disease>()
    val disease: LiveData<Disease> get() = _disease

    fun addDisease(disease: Disease) {
        RetrofitInstance.api.addDisease(disease).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                if (response.isSuccessful) {
                    // Optionally handle the added disease, e.g., update the LiveData
                    _disease.value = response.body() // If you want to show the added disease
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                // Handle error
            }
        })
    }

    fun fetchDiseaseByName(name: String) {
        RetrofitInstance.api.getDiseaseByName(name).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                if (response.isSuccessful) {
                    _disease.value = response.body()
                    Log.d("DiseaseViewModels", "Fetched disease: ${response.body()}")

                }else {
                    Log.e("DiseaseViewModels", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                // Handle error
                Log.e("DiseaseViewModels", "Error fetching disease: ${t.message}")

            }
        })
    }

}
