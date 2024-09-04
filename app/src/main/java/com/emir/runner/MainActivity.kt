package com.emir.runner

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.example.core.presentation.designsystem.RunnerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text("Android Hello")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Scaffold(containerColor = MaterialTheme.colorScheme.secondary) {
        Column {
            Text(
                text = "Hello $name!",
                modifier = modifier.padding(it),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Button(onClick = {}) {
                Text("Hello")
            }
        }
    }

}

@Preview(showBackground = false, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    backgroundColor = 0xFFFFEB3B, device = "id:pixel_7_pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GreetingPreview() {
    RunnerTheme {
        Greeting("Android")
    }
}