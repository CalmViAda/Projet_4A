package com.example.projet_4a.presentation.main

sealed class SignupStatus

data class SignupError (val email:String) : SignupStatus()
object SignupSuccess : SignupStatus()
