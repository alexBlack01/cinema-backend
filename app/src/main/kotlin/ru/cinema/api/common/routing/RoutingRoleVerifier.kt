package ru.cinema.api.common.routing

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.user.roles.RoleVerifier
import ru.cinema.domain.user.roles.model.UserRole

object RoutingRoleVerifier : KoinComponent {
    private val roleVerifier by inject<RoleVerifier>()

    suspend fun PipelineContext<Unit, ApplicationCall>.verifyRoles(necessaryRoles: Set<UserRole>) {
        val userId = call.principalOrThrow<UserPrincipal>().userId
        roleVerifier.checkNecessaryRolesAndThrow(userId, necessaryRoles)
    }

    suspend fun verifyRolesByEmail(email: String, necessaryRoles: Set<UserRole>) {
        roleVerifier.checkNecessaryRolesByEmail(email, necessaryRoles)
    }
}
