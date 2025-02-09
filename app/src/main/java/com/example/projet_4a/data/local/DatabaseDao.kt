package com.example.projet_4a.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projet_4a.data.local.models.UserLocal
import com.example.projet_4a.domain.entity.User

@Dao
interface DatabaseDao
{

    @Query("SELECT * FROM userLocal")
    fun getAll()
            :List<UserLocal>

    @Query("SELECT * FROM userLocal WHERE email LIKE :email AND "+"password LIKE :password LIMIT 1")
    fun findByEmail(email: String, password: String)
            :UserLocal?

    @Insert
    fun insert(user: UserLocal)

    @Delete
    fun delete(user: UserLocal)
}