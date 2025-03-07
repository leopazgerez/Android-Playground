package com.lpg.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lpg.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Color.White,
                    topBar = { TopBar() }) { innerPadding ->
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.Yellow)
            .fillMaxWidth()
            .height(85.dp)
    ) {
        Text(
            "Lemonade",
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ButtonImage(
    bottomMessage: String,
    resourceImageId: Int,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onClick, shape = RoundedCornerShape(16.dp)) {
            Image(
                painter = painterResource(resourceImageId),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = bottomMessage, fontSize = 18.sp)
    }
}

@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(3) }
    val message = when (currentStep) {
        1 -> stringResource(R.string.lemon_tree)
        2 -> stringResource(R.string.lemon_squeeze)
        3 -> stringResource(R.string.lemon_drink)
        else -> stringResource(R.string.lemon_restart)
    }
    val resourceImage = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    ButtonImage(
        bottomMessage = message,
        resourceImageId = resourceImage,
        onClick = {
            currentStep =
                when (currentStep) {
                    1 -> 2
                    2 -> {
                        squeezeCount--
                        if (squeezeCount == 0) {
                            squeezeCount = (1..4).random()
                            3
                        } else {
                            2
                        }

                    }
                    3 -> 4
                    else -> 1
                }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        Greeting("Android")
    }
}