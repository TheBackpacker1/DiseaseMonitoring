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

    private val _diseaseList = MutableLiveData<List<Disease>>()
    val diseaseList: LiveData<List<Disease>> get() = _diseaseList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun addDisease(disease: Disease) {
        RetrofitInstance.api.addDisease(disease).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                if (response.isSuccessful) {
                    _disease.value = response.body()
                    Log.d("DiseaseViewModels", "Disease added successfully: ${response.body()}")
                    // Optionally fetch updated disease list after adding a new disease
                    fetchAllDiseases()
                } else {
                    _errorMessage.value = "Error: ${response.errorBody()?.string()}"
                    Log.e("DiseaseViewModels", "Failed to add disease: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                _errorMessage.value = "Error adding disease: ${t.message}"
                Log.e("DiseaseViewModels", "Error adding disease: ${t.message}")
            }
        })
    }
    fun fetchDiseaseByName(name: String) {
        RetrofitInstance.api.getDiseaseByName(name).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                if (response.isSuccessful) {
                    _disease.value = response.body()
                    Log.d("DiseaseViewModels", "Fetched disease: ${response.body()}")
                } else {
                    _errorMessage.value = "Error: ${response.errorBody()?.string()}"
                    Log.e("DiseaseViewModels", "Error fetching disease by name: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                _errorMessage.value = "Error fetching disease by name: ${t.message}"
                Log.e("DiseaseViewModels", "Error fetching disease: ${t.message}")
            }
        })
    }

    fun fetchAllDiseases() {
        // Assuming there's an endpoint that returns a list of diseases
        RetrofitInstance.api.getAllDiseases().enqueue(object : Callback<List<Disease>> {
            override fun onResponse(call: Call<List<Disease>>, response: Response<List<Disease>>) {
                if (response.isSuccessful) {
                    _diseaseList.value = response.body()
                    Log.d("DiseaseViewModels", "Fetched all diseases: ${response.body()}")
                } else {
                    _errorMessage.value = "Error: ${response.errorBody()?.string()}"
                    Log.e("DiseaseViewModels", "Error fetching all diseases: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Disease>>, t: Throwable) {
                _errorMessage.value = "Error fetching all diseases: ${t.message}"
                Log.e("DiseaseViewModels", "Error fetching all diseases: ${t.message}")
            }
        })
    }

}
