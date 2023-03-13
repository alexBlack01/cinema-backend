package ru.cinema.domain.user

import ru.cinema.domain.auth.model.RegisterViaEmailParams
import ru.cinema.domain.user.model.EditUserProfile
import ru.cinema.domain.user.model.User
import ru.cinema.domain.user.model.UserProfile
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*
@Suppress("TooManyFunctions")
interface UserDbDataSource {
    suspend fun hasNecessaryRoles(userId: UUID, necessaryRoles: Set<UserRole>): Boolean
    suspend fun hasNecessaryRolesByEmail(email: String, necessaryRoles: Set<UserRole>): Boolean
    suspend fun getAllUserProfiles(): List<UserProfile>
    suspend fun getUserById(userId: UUID): UserProfile?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserRoles(userId: UUID): Set<UserRole>
    suspend fun addNewUser(userProfile: RegisterViaEmailParams, password: String): User
    suspend fun addUserRole(userId: UUID, role: UserRole)
    suspend fun insertAvatarInProfile(userId: UUID, avatarId: UUID?)
    suspend fun patchUserProfile(userProfile: EditUserProfile): UserProfile?
    suspend fun deleteUserById(userId: UUID)
}
