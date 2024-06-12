// MainActivity.kt
package com.example.agendaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.agendaapp.ui.theme.AgendaAppTheme
import com.example.agendaapp.view.AgendaScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaAppTheme {
                AgendaScreen()
            }
        }
    }
}
