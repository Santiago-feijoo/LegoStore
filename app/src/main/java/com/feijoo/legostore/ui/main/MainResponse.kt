package com.feijoo.legostore.ui.main

import javax.inject.Singleton

@Singleton
sealed class MainResponse {
    data class SessionStarted(val status: Boolean, val message: String): MainResponse()

}
