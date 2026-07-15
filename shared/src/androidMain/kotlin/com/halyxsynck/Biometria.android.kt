package com.halyxsynck

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

actual object Biometria {

    // Guardamos una referencia débil a la Activity actual, para no causar fugas de memoria
    var activityRef: WeakReference<FragmentActivity>? = null

    actual fun disponible(): Boolean {

        val activity = activityRef?.get() ?: return false

        val biometricManager = BiometricManager.from(activity)

        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS

    }

    actual fun autenticar(onExito: () -> Unit, onError: (String) -> Unit) {

        val activity = activityRef?.get() ?: run {
            onError("No se pudo acceder al sensor")
            return
        }

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

}