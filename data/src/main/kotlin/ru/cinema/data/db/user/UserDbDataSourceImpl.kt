package ru.cinema.data.db.user

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.mapLazy
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.deleteWhere
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.data.db.userrole.model.UserRoleEntity
import ru.cinema.domain.auth.model.RegisterViaEmailParams
import ru.cinema.domain.user.UserDbDataSource
import ru.cinema.domain.user.model.EditUserProfile
import ru.cinema.domain.user.model.User
import ru.cinema.domain.user.model.UserProfile
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*

@Suppress("TooManyFunctions")
class UserDbDataSourceImpl(
    override val db: Database
) : UserDbDataSource, DatabaseDataSource {
    override suspend fun hasNecessaryRoles(userId: UUID, necessaryRoles: Set<UserRole>): Boolean = dbQuery {
        UserEntity.findById(userId)?.roles
            ?.mapLazy { it.role }
            ?.toSet()
            .orEmpty()
            .intersect(necessaryRoles)
            .isNotEmpty()
    }

    override suspend fun hasNecessaryRolesByEmail(email: String, necessaryRoles: Set<UserRole>): Boolean = dbQuery {
        UserEntity.find { UserTable.email eq email }.firstOrNull()?.roles
            ?.mapLazy { it.role }
            ?.toSet()
            .orEmpty()
            .intersect(necessaryRoles)
            .isNotEmpty()
    }

    override suspend fun getAllUserProfiles(): List<UserProfile> = dbQuery {
        UserEntity.all()
            .orderBy(UserTable.lastName to SortOrder.ASC)
            .map { it.toDomainWithoutPassword() }
    }

    override suspend fun getUserById(userId: UUID): UserProfile? = dbQuery {
        UserEntity.findById(userId)?.toDomainWithoutPassword()
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        UserEntity.find { UserTable.email eq email }.firstOrNull()?.toDomain()
    }

    override suspend fun getUserRoles(userId: UUID): Set<UserRole> = dbQuery {
        UserEntity.findById(userId)?.roles?.map { it.role }?.toSet().orEmpty()
    }

    override suspend fun addNewUser(userProfile: RegisterViaEmailParams, password: String): User = dbQuery {
        val user = UserEntity.new {
            this.email = userProfile.email
            this.password = password
            this.firstName = userProfile.firstName
            this.lastName = userProfile.lastName
        }

        user.toDomain()
    }

    override suspend fun addUserRole(userId: UUID, role: UserRole) = dbQueryWithoutResult {
        UserRoleEntity.new {
            this.userId = EntityID(userId, UserTable)
            this.role = role
        }
    }

    override suspend fun insertAvatarInProfile(userId: UUID, avatarId: UUID?) = dbQueryWithoutResult {
        UserTable.update({ UserTable.id eq userId }) {
            it[this.avatarId] = avatarId
        }
    }

    override suspend fun patchUserProfile(userProfile: EditUserProfile): UserProfile? = dbQuery {
        val user = UserEntity.findById(userProfile.id)?.apply {
            userProfile.firstName?.let { name ->
                firstName = name
            }
            userProfile.lastName?.let { surname ->
                lastName = surname
            }
            userProfile.password?.let { newPassword ->
                password = newPassword
            }
        }

        user?.toDomainWithoutPassword()
    }

    override suspend fun deleteUserById(userId: UUID) = dbQueryWithoutResult {
        UserTable.deleteWhere { UserTable.id eq userId }
    }
}
