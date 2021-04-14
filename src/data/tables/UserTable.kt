package com.example.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable:Table() {

    val email = varchar("email",256).uniqueIndex()
    val name = varchar("name",512)
    val passwordHash = varchar("passwordHash",128)


    override val primaryKey: PrimaryKey = PrimaryKey(email)
}