package ru.cinema.api.auth.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("auth/register")
class Register

@Serializable
@Resource("auth/login")
class Login

@Serializable
@Resource("auth/refresh")
class RefreshToken
