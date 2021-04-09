package com.example.data.models

data class User(
    val userId:Int,
    val email:String,
    val name:String,
    val passwordHash:String
)
