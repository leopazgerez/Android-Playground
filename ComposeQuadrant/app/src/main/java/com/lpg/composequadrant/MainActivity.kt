package com.lpg.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpg.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeQuadrantTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContentQuadrant(
                        title = "Android",
                        body = "Jetpack Compose",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ContentQuadrant(title: String, body: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = body,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun Quadrants() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            ContentQuadrant(
                title = "Text composable",
                body = "Displays text and follows the recommended Material Design guidelines.",
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Red)
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
            ContentQuadrant(
                title = "Image composable",
                body = "Creates a composable that lays out and draws a given Painter class object.",
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Yellow)
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            ContentQuadrant(
                title = "Row composable",
                body = "A layout composable that places its children in a horizontal sequence.",
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Gray)
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
            ContentQuadrant(
                title = "Column composable",
                body = "A layout composable that places its children in a vertical sequence.",
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Green)
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        Quadrants()
    }
}