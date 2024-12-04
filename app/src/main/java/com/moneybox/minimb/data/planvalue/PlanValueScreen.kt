package com.moneybox.minimb.data.planvalue

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moneybox.minimb.data.theme.Teal1000

@Composable
fun PlanValueScreen(token: String) {
    val viewModel: PlanValueViewModel = viewModel()
    val totalPlanValue by viewModel.totalPlanValue.observeAsState(DEFAULT_PLAN_VALUE)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(EMPTY_STRING)

    LaunchedEffect(token) {
        viewModel.loadPlanValue(token)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DEFAULT_PADDING),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage.isNotEmpty()) {
            Text(
                errorMessage,
                color = MaterialTheme.colors.error
            )
        } else {
            Text(
                text = PLAN_VALUE_TITLE,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = Teal1000
            )
            Spacer(modifier = Modifier.height(SPACER_HEIGHT))
            Text(
                text = "£$totalPlanValue",
                style = MaterialTheme.typography.h6,
                color = Teal1000
            )
        }
    }
}

// Constants
private const val EMPTY_STRING = ""
private const val PLAN_VALUE_TITLE = "Total Plan Value"
private const val DEFAULT_PLAN_VALUE = 0.0f
private val DEFAULT_PADDING = 16.dp
private val SPACER_HEIGHT = 8.dp