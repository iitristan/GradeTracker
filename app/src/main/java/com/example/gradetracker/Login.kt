package com.example.gradetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    private lateinit var etuser: EditText
    private lateinit var etPassL: EditText
    private lateinit var databaseHandler: CombinedDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize EditTexts
        etuser = findViewById(R.id.etusername2)
        etPassL = findViewById(R.id.etpasswd2)

        // Initialize DatabaseHandler
        databaseHandler = CombinedDatabase(this)
    }

    fun login(view: View) {
        val uname = etuser.text.toString()
        val pword = etPassL.text.toString()

        if (uname.trim().isNotEmpty() && pword.trim().isNotEmpty()) {
            val user = databaseHandler.getUser(uname, pword)

            if (user != null) {
                Toast.makeText(this, "You have successfully logged in!", Toast.LENGTH_SHORT).show()
                val u = Intent(this, ViewListContents::class.java)
                startActivity(u)
            } else {
                Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Username and Password are required", Toast.LENGTH_SHORT).show()
        }
    }

    fun toMENU(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
