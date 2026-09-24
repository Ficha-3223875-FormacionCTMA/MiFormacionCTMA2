package com.sofia.miformacionctma.data.remote.session

interface TokenProvider { fun token(): String? }

class SessionTokenProvider : TokenProvider {
    @Volatile private var sessionToken: String? = null
    override fun token(): String? = sessionToken
    fun actualizar(token: String?) { sessionToken = token }
}
