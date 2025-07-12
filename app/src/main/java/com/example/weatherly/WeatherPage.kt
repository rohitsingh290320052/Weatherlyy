import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.weatherly.WeatherViewModel
import com.example.weatherly.api.NetworkResponse
import com.example.weatherly.api.WeatherModel


//@Composable
//fun WeatherPage(viewModel: WeatherViewModel) {
//    var city by remember { mutableStateOf("") }
//    val weatherResult = viewModel.weatherResult.observeAsState()
//    val keyboard = LocalSoftwareKeyboardController.current
//
//    // Gradient background
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(Color(0xFF1E3C72), Color(0xFF2A5298)) // Deep blue gradient
//                )
//            )
//            .padding(16.dp)
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White, RoundedCornerShape(12.dp)),
//                value = city,
//                onValueChange = { city = it },
//                label = { Text("Search city") },
//                trailingIcon = {
//                    IconButton(onClick = {
//                        viewModel.getData(city)
//                        keyboard?.hide()
//                    }) {
//                        Icon(Icons.Default.Search, contentDescription = "Search")
//                    }
//                },
//                singleLine = true,
//                shape = RoundedCornerShape(12.dp)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            when (val result = weatherResult.value) {
//                is NetworkResponse.Error -> {
//                    Text(
//                        text = result.message,
//                        color = Color.Red,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//
//                NetworkResponse.Loading -> {
//                    CircularProgressIndicator(color = Color.White)
//                }
//
//                is NetworkResponse.Success -> {
//                    WeatherDetails(result.data)
//                }
//
//                null -> {
//                    Text(
//                        text = "Enter a city to get weather updates",
//                        color = Color.White,
//                        fontSize = 18.sp,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(24.dp)
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun WeatherPage(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboard = LocalSoftwareKeyboardController.current

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E3C72), Color(0xFF2A5298)) // Blue gradient
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = topPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 💡 Stylized Search Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Search city", color = Color.Gray) },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.getData(city)
                            keyboard?.hide()
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🌤️ Weather Result
            when (val result = weatherResult.value) {
                is NetworkResponse.Error -> {
                    Text(
                        text = result.message,
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                is NetworkResponse.Success -> {
                    WeatherDetails(result.data)
                }

                null -> {
                    Text(
                        text = "Enter a city to get weather updates",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "${data.location.name}, ${data.location.country}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature
        Text(
            text = "${data.current.temp_c}°C",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Weather icon",
            modifier = Modifier.size(160.dp)
        )

        Text(
            text = data.current.condition.text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                WeatherInfoRow("💧 Humidity", "${data.current.humidity}%")
                WeatherInfoRow("🌬 Wind", "${data.current.wind_kph} km/h")
                WeatherInfoRow("🔆 UV Index", "${data.current.uv}")
                WeatherInfoRow("☔ Precipitation", "${data.current.precip_mm} mm")
                WeatherInfoRow("🕒 Time", data.location.localtime.split(" ")[1])
                WeatherInfoRow("📅 Date", data.location.localtime.split(" ")[0])
            }
        }
    }
}

@Composable
fun WeatherInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}
