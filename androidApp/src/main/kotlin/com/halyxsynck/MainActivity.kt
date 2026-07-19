package com.halyxsynck

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.halyxsynck.navigation.Navigator
import java.lang.ref.WeakReference

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Registramos esta Activity para que Biometria.kt pueda usarla
        Biometria.activityRef = WeakReference(this)

        setContent {
            // Intercepta el botón físico de "atrás" del celular
            BackHandler(enabled = true) {
                val huboRetroceso = Navigator.goBack()
                if (!huboRetroceso) {
                    finish() // ya no hay a dónde regresar, aquí sí cierra la app
                }
            }

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}