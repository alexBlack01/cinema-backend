package ru.cinema.domain.user

import ru.cinema.domain.auth.model.RegisterViaEmailParams
import ru.cinema.domain.user.model.User
import ru.cinema.domain.user.model.UserProfile
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*

interface UserDbDataSource {
    suspend fun hasNecessaryRoles(userId: UUID, necessaryRoles: Set<UserRole>): Boolean

    suspend fun getUserById(userId: UUID): UserProfile?

    suspend fun getUserByEmail(email: String): User?

    suspend fun getUserRoles(userId: UUID): Set<UserRole>

    suspend fun addNewUser(userProfile: RegisterViaEmailParams, password: String): User

    suspend fun addUserRole(userId: UUID, role: UserRole)
}
