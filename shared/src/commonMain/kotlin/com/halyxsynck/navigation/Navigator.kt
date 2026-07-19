package com.halyxsynck.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Navigator {

    private val backStack = mutableStateListOf<Screen>(Screen.Splash)

    var version by mutableStateOf(0)
        private set

    val currentScreen: Screen
        get() = backStack.last()

    fun navigate(screen: Screen) {
        backStack.add(screen)
        version++
    }

    fun navigateAndClear(screen: Screen) {
        backStack.clear()
        backStack.add(screen)
        version++
    }

    fun goBack(): Boolean {
        return if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            version++
            true
        } else {
            false
        }
    }

}