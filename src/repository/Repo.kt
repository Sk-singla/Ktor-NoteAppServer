package com.example.repository

import com.example.data.models.User
import com.example.data.tables.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {

    suspend fun addUser(email:String,name:String,passwordHash:String){
        dbQuery{
            UserTable.insert { user->
                user[UserTable.email] = email
                user[UserTable.name] = name
                user[UserTable.passwordHash]  = passwordHash
            }
        }
    }

    suspend fun getUserByEmail(email:String) = dbQuery {
        UserTable.select{ UserTable.email.eq(email) }
            .map{ rowToUser(it) }
            .singleOrNull()
    }



    private fun rowToUser(row:ResultRow?):User?{
        if(row == null){
            return  null
        }

        return User(
            email = row[UserTable.email],
            name = row[UserTable.name],
            passwordHash =  row[UserTable.passwordHash]
        )
    }
}