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
    private val _diseaseList = MutableLiveData<List<Disease>>(emptyList())
    val diseaseList: LiveData<List<Disease>> get() = _diseaseList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchAllDiseases()
    }

    fun addDisease(disease: Disease) {
        _isLoading.value = true // Set loading state before API call
        RetrofitInstance.api.addDisease(disease).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                _isLoading.value = false // Reset loading state
                if (response.isSuccessful) {
                    response.body()?.let {
                        _disease.value = it
                        // Update the diseaseList by adding the new disease
                        val currentList = _diseaseList.value?.toMutableList() ?: mutableListOf()
                        currentList.add(it) // Add the new disease
                        _diseaseList.value = currentList // Update the live data
                        Log.d("DiseaseViewModels", "Disease added successfully: $it")
                    }
                } else {
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                _isLoading.value = false // Reset loading state
                _errorMessage.value = "Error adding disease: ${t.message}"
                Log.e("DiseaseViewModels", "Error adding disease: ${t.message}")
            }
        })
    }

    private fun handleError(response: Response<*>) {
        val errorBody = response.errorBody()?.string()
        val statusCode = response.code()
        _errorMessage.value = "Error: $errorBody"
        Log.e("DiseaseViewModels", "API error (Code: $statusCode): $errorBody")
    }

    fun fetchDiseaseByName(name: String) {
        _isLoading.value = true
        RetrofitInstance.api.getDiseaseByName(name).enqueue(object : Callback<Disease> {
            override fun onResponse(call: Call<Disease>, response: Response<Disease>) {
                _isLoading.value = false // Reset loading state
                if (response.isSuccessful) {
                    _disease.value = response.body()
                    Log.d("DiseaseViewModels", "Fetched disease: ${response.body()}")
                } else {
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<Disease>, t: Throwable) {
                _isLoading.value = false // Reset loading state
                _errorMessage.value = "Error fetching disease by name: ${t.message}"
                Log.e("DiseaseViewModels", "Error fetching disease: ${t.message}")
            }
        })
    }

    fun fetchAllDiseases() {
        _isLoading.value = true
        RetrofitInstance.api.getAllDiseases().enqueue(object : Callback<List<Disease>> {
            override fun onResponse(call: Call<List<Disease>>, response: Response<List<Disease>>) {
                _isLoading.value = false // Reset loading state
                if (response.isSuccessful) {
                    _diseaseList.value = response.body() ?: emptyList()
                    Log.d("DiseaseViewModels", "Fetched all diseases: ${response.body()}")
                } else {
                    handleError(response)
                }
            }

            override fun onFailure(call: Call<List<Disease>>, t: Throwable) {
                _isLoading.value = false // Reset loading state
                _errorMessage.value = "Error fetching all diseases: ${t.message}"
                Log.e("DiseaseViewModels", "Error fetching all diseases: ${t.message}")
            }
        })
    }




}
