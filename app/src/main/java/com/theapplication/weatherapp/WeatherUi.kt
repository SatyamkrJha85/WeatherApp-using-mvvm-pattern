package com.theapplication.weatherapp
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.jetpackcomposethe.taskmanager.Data.WheatherData
import com.jetpackcomposethe.taskmanager.Data.wheatherlist
import com.theapplication.weatherapp.Mvvm.Api.NetworkResponse
import com.theapplication.weatherapp.Mvvm.Model.WeatherModel
import com.theapplication.weatherapp.Mvvm.WeatherViewModel
import com.theapplication.weatherapp.ui.theme.LightBlue
import com.theapplication.weatherapp.ui.theme.LightGreen
import com.theapplication.weatherapp.ui.theme.LightPurple
import com.theapplication.weatherapp.ui.theme.Nurito
import com.theapplication.weatherapp.ui.theme.Orange
import com.theapplication.weatherapp.ui.theme.Purple700

import java.lang.reflect.Modifier



@Composable
fun Weather_Screen(weatherViewModel: WeatherViewModel) {

    var imgcondition = remember {
        mutableStateOf(true)
    }
    LazyColumn(

    ) {
        item {
            search(weatherViewModel)

            if (imgcondition.value){
                DefaultImage()
            }

            Resultdata(weatherViewModel,imgcondition)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun search(weatherViewModel: WeatherViewModel) {
    var city by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 6.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        // horizontalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = androidx.compose.ui.Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    Color.DarkGray, CircleShape
                ), painter = painterResource(id = R.drawable.logo), contentDescription = null
        )
        Spacer(modifier = androidx.compose.ui.Modifier.width(14.dp))

        OutlinedTextField(
            modifier = androidx.compose.ui.Modifier

                .clip(RoundedCornerShape(40.dp))
                .border(
                    1.dp,
                    Purple700,
                    RoundedCornerShape(40.dp)
                )
                .height(52.dp)
                .width(220.dp),
            placeholder = { Text(text = "Search Your City...", textAlign = TextAlign.Center) },
            value = city,
            maxLines = 1,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
            },
            onValueChange = { city = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                errorCursorColor = Color.Black


            ))

        Spacer(modifier = androidx.compose.ui.Modifier.width(14.dp))

        IconButton(onClick = {
            weatherViewModel.getData(city)
            keyboardController?.hide()
        }) {
            
            Icon(imageVector = Icons.Filled.Search, contentDescription = null,modifier = androidx.compose.ui.Modifier
                .size(40.dp)
                .padding(5.dp),)
           
        }



    }


}
@Composable
fun Image(data: WeatherModel) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tempC = data.current.temp_c.toDoubleOrNull()

        if (tempC != null) {
            when {
                tempC in 0.0..14.9 -> {
                    Image(
                        modifier = androidx.compose.ui.Modifier.size(300.dp),
                        painter = painterResource(id = R.drawable.coupleinwinter),
                        contentDescription = null
                    )
                }
                tempC in 15.0..24.9 -> {
                    Image(
                        modifier = androidx.compose.ui.Modifier.size(300.dp),
                        painter = painterResource(id = R.drawable.weather),
                        contentDescription = null
                    )
                }
                tempC in 25.0..29.9 -> {
                    // Placeholder image or text when the temperature is between 25 and 29.9
                    Image(
                        modifier = androidx.compose.ui.Modifier.size(300.dp),
                        painter = painterResource(id = R.drawable.coupleinchill),
                        contentDescription = null
                    )
                }
                tempC >= 30.0 -> {
                    Image(
                        modifier = androidx.compose.ui.Modifier.size(300.dp),
                        painter = painterResource(id = R.drawable.coupleinsummer),
                        contentDescription = null
                    )
                }
                else -> {
                    Text(text = "Temperature out of range")
                }
            }
        } else {
            Text(text = "Invalid temperature data")
        }
    }
}


@Composable
fun DefaultImage() {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(),
           // .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = androidx.compose.ui.Modifier.size(330.dp),
            painter = painterResource(id = R.drawable.coupleinproblem),
            contentDescription = null
        )

        Text(text = "Add a Correct Input")
    }
}

@Composable
fun Resultdata(weatherViewModel: WeatherViewModel,imgcondition:MutableState<Boolean>) {

    val weatherresult = weatherViewModel.weatherResult.observeAsState()

    when(val result = weatherresult.value){
        is NetworkResponse.Error -> {
           // Text(text = result.message)

            Column (
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "${result.message}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = Nurito,
                )
            }

        }
        is NetworkResponse.Success -> {

           // Text(text = result.data.toString())
            imgcondition.value = false
            Image(result.data)
            maininfo(data = result.data)

           Row(
               modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Center
           ) {
               cloudbox(R.drawable.date, LightBlue,"Local Date",result.data.location.localtime.split(" ")[0] )
               cloudbox(R.drawable.time, LightGreen,"Local Time",result.data.location.localtime.split(" ")[1] )
               cloudbox(R.drawable.sun, LightPurple,"Humidity",result.data.current.humidity+"°c" )

           }
        }
        NetworkResponse.loading -> {
            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Orange)
            }


        }
        null -> {}
    }
}

@Composable
fun maininfo(data: WeatherModel) {


    Column(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(text = "${data.current.temp_c} °c", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
        Spacer(modifier = androidx.compose.ui.Modifier.height(6.dp))

        Text(
            text = "${data.location.name}, ${data.location.region}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            fontFamily = Nurito,
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(6.dp))
        Text(
            text = "${data.location.country}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            fontFamily = Nurito,
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(6.dp))
        Text(
            text = "The Current Condition is ${data.current.condition.text}\n" +
                    "Winds the speed at ${data.current.wind_kph} kp/h",
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Some", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            Row(
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "More Helpfull Details", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Icon(
                    modifier = androidx.compose.ui.Modifier
                        .size(22.dp)
                        .align(Alignment.CenterVertically)
                        .padding(top = 3.dp),
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null
                )

            }

        }

    }
}

@Composable
fun cloudbox(img:Int,color: Color,title:String,des:String) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // First Box

        Column() {

            Box(
                modifier = androidx.compose.ui.Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(100.dp)
                    .height(150.dp)
                    .background(color)
                    .padding(5.dp)
            ) {
                Column(
                    modifier = androidx.compose.ui.Modifier
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = androidx.compose.ui.Modifier.padding(top = 7.dp),
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.height(5.dp))
                    Image(
                        modifier = androidx.compose.ui.Modifier

                            .size(60.dp),
                        painter = painterResource(img),
                        contentDescription = null
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.height(5.dp))


                    Text(text = des, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, textAlign = TextAlign.Center, color = Color.Black)



                }

            }
        }


    }
}
