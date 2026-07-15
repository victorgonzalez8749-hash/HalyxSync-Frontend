package com.halyxsynck

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

// Verifica si el celular tiene sensor de huella disponible y configurado
fun huellaDisponible(activity: FragmentActivity): Boolean {

    val biometricManager = BiometricManager.from(activity)

    return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS

}

// Muestra el diálogo de huella y ejecuta una acción según el resultado
fun autenticarConHuella(
    activity: FragmentActivity,
    onExito: () -> Unit,
    onError: (String) -> Unit
) {

    val executor = ContextCompat.getMainExecutor(activity)

    val callback = object : BiometricPrompt.AuthenticationCallback() {

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onExito()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onError(errString.toString())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onError("Huella no reconocida")
        }

    }

    val biometricPrompt = BiometricPrompt(activity, executor, callback)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Iniciar sesión con huella")
        .setSubtitle("Coloca tu dedo en el sensor")
        .setNegativeButtonText("Cancelar")
        .build()

    biometricPrompt.authenticate(promptInfo)

}