package com.example.projet_4a.data.repository

import com.example.projet_4a.data.local.DatabaseDao
import com.example.projet_4a.data.local.models.UserLocal
import com.example.projet_4a.data.local.models.toData
import com.example.projet_4a.data.local.models.toEntity
import com.example.projet_4a.domain.entity.User

class UserRepository(private val databaseDao: DatabaseDao)
{

    suspend fun createUser(user: User)
    {
        databaseDao.insert(user.toData())
    }

    fun getUser(email: String, password:String) : User?
    {
        val userLocal = databaseDao.findByEmail(email, password)
        return userLocal?.toEntity()
    }
}