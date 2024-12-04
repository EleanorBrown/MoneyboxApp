package com.moneybox.minimb.data.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.moneybox.minimb.R
import com.moneybox.minimb.data.theme.Teal200


@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = viewModel()
    val email by viewModel.email.observeAsState(EMPTY_STRING)
    val password by viewModel.password.observeAsState(EMPTY_STRING)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(EMPTY_STRING)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DEFAULT_PADDING)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.moneybox_logo),
                contentDescription = LOGO_DESCRIPTION,
                modifier = Modifier
                    .height(LOGO_HEIGHT)
            )
            Spacer(modifier = Modifier.height(SPACER_HEIGHT))
            TextField(
                value = email,
                onValueChange = { viewModel.email.value = it },
                label = { Text(EMAIL_LABEL) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Teal200,
                    unfocusedIndicatorColor = Teal200
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { viewModel.password.value = it },
                label = { Text(PASSWORD_LABEL) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Teal200,
                    unfocusedIndicatorColor = Teal200
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                viewModel.login { token ->
                    navController.navigate("planValue/$token")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(LOGIN_BUTTON)
        }
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        if (isLoading) {
            CircularProgressIndicator()
        }
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage)
        }
    }
}

// Constants
private val LOGO_HEIGHT = 100.dp
private val SPACER_HEIGHT = 16.dp
private val DEFAULT_PADDING = 16.dp
private const val LOGO_DESCRIPTION = "Moneybox Logo"
private const val EMAIL_LABEL = "Email"
private const val PASSWORD_LABEL = "Password"
private const val LOGIN_BUTTON = "LOG IN"
private const val EMPTY_STRING = ""