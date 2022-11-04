package ru.cinema.auth

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.Payload

interface TokenValidator {
    /**
     * Access token verifier
     */
    val accessVerifier: JWTVerifier

    /**
     * Refresh token verifier
     */
    val refreshVerifier: JWTVerifier

    /**
     * Getting user ID from token data
     *
     * @param payload is payload from token
     * @return User ID or null if not exist in payload
     */
    fun getUserIdFromPayload(payload: Payload): String?
}
