package com.test.mytest.model

data class User(
    val userType: String,
    val coolingStartTime: String,
    val coolingEndTime: String,
    val accessibleModules: List<String>
)
