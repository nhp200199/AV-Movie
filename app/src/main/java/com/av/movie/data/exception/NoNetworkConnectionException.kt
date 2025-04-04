package com.av.movie.data.exception

import java.io.IOException

class NoNetworkConnectionException: IOException() {
    override val message: String
        get() = "No internet connection"
}