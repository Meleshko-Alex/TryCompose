package com.meleha.trycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meleha.trycompose.ui.btnTextfieldCheckBoxes.ScrollableScreen
import com.meleha.trycompose.ui.lazyColumn.FastLazyColumn
import com.meleha.trycompose.ui.lazyColumn.SimpleLazyColumn
import com.meleha.trycompose.ui.navigationBar.NavigationScreen
import com.meleha.trycompose.ui.perfectLazyColumn.ui.PerfectLazyColumn
import com.meleha.trycompose.ui.simpleNavigation.SimpleNavigationScreen

data class CounterState(
    val number: Int = 0
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //StatefulCounter()
            //ScrollableScreen()
            //SimpleLazyColumn()
            //FastLazyColumn()
            //PerfectLazyColumn()
            //NavigationScreen()
            SimpleNavigationScreen()
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun StatefulCounter() {
        val counterMutableState = remember {
            mutableStateOf(CounterState())
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = counterMutableState.value.number.toString(),
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    val state = counterMutableState.value
                    val newState = state.copy(number = state.number + 1)
                    counterMutableState.value = newState
                })
            {
                Text(text = "Increment", fontSize = 18.sp)
            }
        }
    }
}