package ru.cinema.data.db.userrole.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.user.roles.model.UserRole
import java.util.*

object UserRoleTable : UUIDTable(name = "user_roles") {
    val role = enumerationByName(name = "role", length = 50, klass = UserRole::class)
    val userId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
}

class UserRoleEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var role by UserRoleTable.role
    var userId by UserRoleTable.userId

    companion object : UUIDEntityClass<UserRoleEntity>(UserRoleTable)
}
