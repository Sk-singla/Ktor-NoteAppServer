package com.example.repository

import com.example.data.table.NoteTable
import com.example.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

object DatabaseFactory {


    fun init(){
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(NoteTable)
        }

    }




    fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER")
//        config.jdbcUrl =
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        val uri = URI(System.getenv("DATABASE_URL"))
        val username = uri.userInfo.split(":").toTypedArray()[0]
        val password = uri.userInfo.split(":").toTypedArray()[1]

        config.jdbcUrl =
            "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"


        config.validate()

        return HikariDataSource(config)
    }


    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

}