package ca.qc.cstj.andromia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.ui.theme.AndromiaTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ModelPreferencesManager.with(this)

        setContent {
            AndromiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                    // A surface container using the 'background' color from the theme
                )
                {
                    NavigationApp()
                }
            }
        }
    }
}