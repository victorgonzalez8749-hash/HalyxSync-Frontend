package com.halyxsynck

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.halyxsynck.navigation.Navigator
import java.lang.ref.WeakReference

class MainActivity : FragmentActivity() {

    private val permisoNotificaciones = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* concedido o no, no bloqueamos la app */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Registramos esta Activity para que Biometria.kt pueda usarla
        Biometria.activityRef = WeakReference(this)

        // NUEVO: pedir permiso de notificaciones (obligatorio desde Android 13)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // NUEVO: obtener el token de Firebase desde el inicio
        FirebaseMessaging.getInstance().token.addOnCompleteListener { tarea ->
            if (tarea.isSuccessful) {
                TokenManager.tokenPendiente = tarea.result
            }
        }

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