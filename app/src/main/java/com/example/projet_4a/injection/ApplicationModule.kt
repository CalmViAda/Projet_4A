package com.example.projet_4a.injection

import android.content.Context
import androidx.room.Room
import com.example.projet_4a.data.local.AppDatabase
import com.example.projet_4a.data.local.DatabaseDao
import com.example.projet_4a.data.repository.UserRepository
import com.example.projet_4a.domain.usecase.CreateUserUseCase
import com.example.projet_4a.domain.usecase.GetUserUseCase
import com.example.projet_4a.presentation.main.viewmodels.MainViewModel
import com.example.projet_4a.presentation.main.ui.SignUp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val presentationModule =
    module{
        factory {
            MainViewModel(
                get(),
                get()
            )
        }
        factory { SignUp() }
    }

val domainModule=
    module {
        factory { CreateUserUseCase(get()) }
        factory { GetUserUseCase(get()) }
    }

val dataModule =
    module {
        single { UserRepository(get()) }
        single{ createDatabase(androidContext()) }
    }


fun createDatabase(context: Context): DatabaseDao
{
    val appDatabase =
        Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
        ).build()
    return appDatabase.databaseDao()
}

