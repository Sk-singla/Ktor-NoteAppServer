package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.model.User

class JwtService {

    private val issuer = "noteServer"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    val varifier:JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user:User):String {
        return JWT.create()
            .withSubject("NOteAuthentication")
            .withIssuer(issuer)
            .withClaim("email",user.email)
            .sign(algorithm)
    }

}