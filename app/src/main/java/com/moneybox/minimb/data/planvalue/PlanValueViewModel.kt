package com.moneybox.minimb.data.planvalue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.moneybox.minimb.data.models.products.AllProductsResponse
import com.moneybox.minimb.data.networking.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanValueViewModel : ViewModel() {
    val totalPlanValue = MutableLiveData<Float>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    fun loadPlanValue(token: String) {
        // Set loading state to true and clear any previous error messages
        isLoading.value = true
        errorMessage.value = EMPTY_STRING

        Networking.apiService.getPlanValue("Bearer $token")
            .enqueue(object : Callback<AllProductsResponse> {
                override fun onResponse(
                    call: Call<AllProductsResponse>,
                    response: Response<AllProductsResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        // If successful, update the total plan value
                        totalPlanValue.value = response.body()?.totalPlanValue ?: DEFAULT_PLAN_VALUE
                    } else {
                        errorMessage.value = LOAD_PLAN_VALUE_FAILED
                    }
                }

                override fun onFailure(call: Call<AllProductsResponse>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = LOAD_PLAN_VALUE_ERROR
                }
            })
    }
}

// Constants
private const val EMPTY_STRING = ""
private const val DEFAULT_PLAN_VALUE = 0.0f
private const val LOAD_PLAN_VALUE_FAILED = "Failed to load plan value"
private const val LOAD_PLAN_VALUE_ERROR = "An error occurred"
