package com.theapplication.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.theapplication.weatherapp.Mvvm.Api.NetworkResponse
import com.theapplication.weatherapp.Mvvm.WeatherViewModel

//@Preview(showSystemUi = true)
@Composable
fun WheatherPage(weatherViewModel: WeatherViewModel) {
    var city by rememberSaveable {
        mutableStateOf("")
    }
    
    val weatherresult = weatherViewModel.weatherResult.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = city, onValueChange ={city=it} , label =
            { Text(text = "search for any location")}, modifier = Modifier.weight(1f))

            IconButton(onClick = { weatherViewModel.getData(city) }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription =null )
            }





        }
        
        when(val result = weatherresult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            is NetworkResponse.Success -> {

                Text(text = result.data.toString())
            }
            NetworkResponse.loading -> {
                CircularProgressIndicator()
            }
            null -> {}
        }
    }
}