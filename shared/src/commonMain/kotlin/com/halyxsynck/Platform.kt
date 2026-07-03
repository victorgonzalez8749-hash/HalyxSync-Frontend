package com.halyxsynck

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform