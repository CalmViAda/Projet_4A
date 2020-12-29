package com.example.projet_4a.presentation.main.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.projet_4a.R
import com.example.projet_4a.presentation.main.viewmodels.MainViewModel
import com.example.projet_4a.presentation.main.SignupError
import com.example.projet_4a.presentation.main.SignupSuccess
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject


class SignUp : AppCompatActivity()
{

    val mainViewModel : MainViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mainViewModel.signupLiveData.observe(this, Observer
        {
            when(it)
            {
                is SignupError ->
                {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Error")
                        .setMessage("Count already exits")
                        .setPositiveButton("Ok")
                        {
                                dialog, _ -> dialog.dismiss()
                        }
                        .show()
                }
                SignupSuccess ->
                {
                    //TODO NAVIGATE
                    Toast.makeText(this,"User Registered", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java);
                    startActivity(intent)
                }
            }
        })

        sigup_button.setOnClickListener{
            if(sig_email_edit.text.toString().trim().isNotEmpty() && sig_password_edit.text.toString().isNotEmpty() && conf_password_input_edit.text.toString().isNotEmpty())
            {
                if(sig_password_edit.text.toString() == conf_password_input_edit.text.toString())
                {
                    mainViewModel.OnClickedSignUp(
                        sig_email_edit.text.toString().trim(),
                        sig_password_edit.text.toString()
                    )
                }
                else
                {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Error")
                        .setMessage("Enter same password")
                        .setPositiveButton("Ok")
                        {
                                dialog, _ -> dialog.dismiss()
                        }
                        .show()
                }

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
    }


}
