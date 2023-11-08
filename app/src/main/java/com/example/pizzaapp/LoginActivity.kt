package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText

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
        //login check
        fun checkLogin(email:String, password:String):Boolean{
            val columns = arrayOf(COLUMN_NAME)
            val db = this.readableDatabase
            //selection criteria
            val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            //selection arguments
            val selectionArgs = arrayOf(email,password)

            val cursor = db.query(TABLE_ACCOUNT,// table to query
            colums, //columns to return
            selection,// )

        }
        }
    }
}