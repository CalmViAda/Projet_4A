package com.example.projet_4a.presentation.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.projet_4a.R
import com.example.projet_4a.presentation.main.*
import com.example.projet_4a.presentation.main.viewmodels.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity()
{

    val mainViewModel : MainViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.loginLiveData.observe(this, Observer
        {
            when(it)
            {
                is LoginSuccess ->
                {
                    //TODO NAVIGATE
                    Toast.makeText(this,"Login Success", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MovieActivity::class.java)
                    this.startActivity(intent)
                }
                LoginError ->
                {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Error")
                        .setMessage("Unknown Count")
                        .setPositiveButton("Ok")
                        {
                                dialog, _ -> dialog.dismiss()
                        }
                        .show()
                }
            }
        }
        )

        login_button.setOnClickListener {
            if(login_edit.text.toString().trim().isNotEmpty() && password_edit.text.toString().isNotEmpty())
            {
                mainViewModel.onClickedLogin(
                    login_edit.text.toString().trim(),
                    password_edit.text.toString()
                )
            }
            else
            {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Enter an email and a password")
                    .setPositiveButton("Ok")
                    {
                            dialog, _ -> dialog.dismiss()
                    }
                    .show()
            }
        }

        signup_button.setOnClickListener{
            val intent = Intent(this, SignUp::class.java);
            startActivity(intent)
        }

    }
}
