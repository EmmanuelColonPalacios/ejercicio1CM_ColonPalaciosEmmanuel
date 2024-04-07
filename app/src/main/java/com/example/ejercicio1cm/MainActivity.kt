package com.example.ejercicio1cm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ejercicio1cm.ui.theme.Ejercicio1CMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenSplash = installSplashScreen()
        screenSplash.setOnExitAnimationListener { splashScreenView ->
            // Splash screen
            startActivity(Intent(this, PaymentActivity2::class.java))
            finish()
        }
    }
}
