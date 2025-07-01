package com.example.skinlytics.model

interface ProfileRepository {
    fun getUserName(): String
    fun getUserEmail(): String
}

class DefaultProfileRepository : ProfileRepository {
    override fun getUserName() = "Your Name"
    override fun getUserEmail() = "user@email.com"
} 