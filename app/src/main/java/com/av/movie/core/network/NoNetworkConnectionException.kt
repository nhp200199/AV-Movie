package com.av.movie.core.network

import java.io.IOException

class NoNetworkConnectionException: IOException() {
    override val message: String
        get() = "No internet connection"
}