package com.moneybox.minimb.data.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.data.models.login.LoginResponse
import com.moneybox.minimb.data.networking.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    fun login(onLoginSuccess: (String) -> Unit) {
        // Set loading state to true and clear any previous error messages
        isLoading.value = true
        errorMessage.value = EMPTY_STRING

        val emailValue = email.value ?: EMPTY_STRING
        val passwordValue = password.value ?: EMPTY_STRING

        // Create a login request with the email and password
        val request = LoginRequest(
            email = emailValue,
            password = passwordValue
        )

        // Make a network call to login
        Networking.apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                // Set loading state to false
                isLoading.value = false
                if (response.isSuccessful) {
                    // If login is successful, get the token from the response
                    val token = response.body()?.session?.bearerToken
                    if (token != null) {
                        onLoginSuccess(token)
                    } else {
                        errorMessage.value = LOGIN_FAILED_TOKEN_NULL
                    }
                } else {
                    errorMessage.value = LOGIN_FAILED_DETAILS
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Set loading state to false and set error message on failure
                isLoading.value = false
                errorMessage.value = "An error occurred: ${t.message}"
            }
        })
    }
}

// Constants
private const val EMPTY_STRING = ""
private const val LOGIN_FAILED_TOKEN_NULL = "Login failed: Token is null"
private const val LOGIN_FAILED_DETAILS =
    "Login failed. Please ensure you have entered the correct details."