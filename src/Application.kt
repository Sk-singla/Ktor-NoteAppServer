package com.example

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.data.model.User
import com.example.repository.DatabaseFactory
import com.example.repository.repo
import com.example.routes.UserRoutes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()
    val db = repo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {


    }

    install(Locations)

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        UserRoutes(db,jwtService,hashFunction)

        route("/notes"){

            route("/create") {
                // localhost:8081/notes/create
                post {
                    val body = call.receive<String>()
                    call.respond(body)
                }
            }

            delete{
                val body = call.receive<String>()
                call.respond(body)
            }
        }


    }
}

data class MySession(val count: Int = 0)

