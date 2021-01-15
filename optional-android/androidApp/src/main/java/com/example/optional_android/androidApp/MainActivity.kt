package com.example.optional_android.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.optional_android.shared.Greeting
import android.widget.TextView
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { MainView() }
    }
}

@Composable
fun MainView() {
    Text(text = greet())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainView()
}
