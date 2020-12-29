package com.example.projet_4a.domain.usecase

import com.example.projet_4a.data.repository.UserRepository
import com.example.projet_4a.domain.entity.User

class CreateUserUseCase(private val userRepository: UserRepository)
{
    suspend fun invoke(user:User)
    {
        userRepository.createUser(user)
    }
}