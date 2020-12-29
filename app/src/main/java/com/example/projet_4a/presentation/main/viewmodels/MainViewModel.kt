package com.example.projet_4a.presentation.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.projet_4a.domain.entity.User
import com.example.projet_4a.domain.usecase.CreateUserUseCase
import com.example.projet_4a.domain.usecase.GetUserUseCase
import com.example.projet_4a.presentation.main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel (
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase
):ViewModel()
{



    val loginLiveData:MutableLiveData<LoginStatus> = MutableLiveData()
    val signupLiveData:MutableLiveData<SignupStatus> = MutableLiveData()



    fun onClickedLogin(emailUser: String, password: String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {

            val user = getUserUseCase.invoke(emailUser, password)
            val loginStatus = if (user != null )
            {
                LoginSuccess(user.email)
            }
            else
            {
                LoginError
            }

            withContext(Dispatchers.Main)
            {
                loginLiveData.value = loginStatus
            }

        }
    }


    fun OnClickedSignUp(emailUser: String, password: String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {

            val user = getUserUseCase.invoke(emailUser, password)

            if (user==null)
            {
                createUserUseCase.invoke(User(emailUser, password))
            }
            val signupStatus = if (user == null )
            {
                SignupSuccess
            }
            else
            {
                SignupError(user.email)
            }
            withContext(Dispatchers.Main)
            {
                signupLiveData.value = signupStatus
            }
        }
    }

}