package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance text
        val txtUsername:EditText = findViewById(R.id.editTextEmail)
        val txtPassword:EditText = findViewById(R.id.editTextPassword)
        //instance button login
        val btnLogin:Button = findViewById(R.id.buttonLogin)

        //event button login
        btnLogin.setOnClickListener {
            val intentLogin = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intentLogin)
            //event button login
            btnLogin.setOnClickListener {
                //object class databasehelper
                val dataBaseHelper = DataBaseHelper(this)

                val email = txtUsername.text.toString().trim()
                val password = txtPassword.text.toString().trim()

                //check login
                val result:Boolean = dataBaseHelper.checkLogin(email,password)
                if (result == true){
                    Toast.makeText(this@LoginActivity, "Login Success",
                        Toast.LENGTH_SHORT).show()
                    val intentLogin = Intent(this@LoginActivity,
                        MainActivity::class.java)
                   startActivity(intentLogin)
                }else{
                    Toast.makeText(this@LoginActivity,"Login Failed, Try  Again !!!",
                    Toast.LENGTH_SHORT).show()
                }
            }

        }
        }
    }
