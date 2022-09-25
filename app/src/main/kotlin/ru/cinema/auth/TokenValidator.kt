package ru.cinema.auth

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.Payload
import ru.cinema.domain.user.roles.model.UserRole

interface TokenValidator {
    /**
     * Token verifier
     */
    val verifier: JWTVerifier

    /**
     * Getting user ID from token data
     *
     * @param payload is payload from token
     * @return User ID or null if not exist in payload
     */
    fun getUserIdFromPayload(payload: Payload): String?

    /**
     * Getting user roles set from token data
     * @param payload is payload from token
     * @return Set of user roles
     */
    fun getUserRolesFromPayload(payload: Payload): Set<UserRole>
}
