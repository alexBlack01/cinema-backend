package ru.cinema.domain.user.roles

import ru.cinema.domain.common.error.NoNecessaryRole
import ru.cinema.domain.user.UserDbDataSource
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*

interface RoleVerifier {
    suspend fun checkNecessaryRolesAndThrow(userId: UUID, necessaryRoles: Set<UserRole>)
}
class RoleVerifierImpl(
    private val userDbDataSource: UserDbDataSource
) : RoleVerifier {
    override suspend fun checkNecessaryRolesAndThrow(userId: UUID, necessaryRoles: Set<UserRole>) {
        if (!userDbDataSource.hasNecessaryRoles(userId, necessaryRoles)) {
            throw NoNecessaryRole()
        }
    }
}
