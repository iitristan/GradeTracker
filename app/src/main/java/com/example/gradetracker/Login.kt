package com.example.gradetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signUp = findViewById<Button>(R.id.signup2btn2)

        signUp.setOnClickListener {
            val i = Intent(this, SignUp::class.java)
            startActivity(i)
            finish()
        }

        fun toMENU(view: View) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        fun toYEARLEVEL(view: View) {
            val i = Intent(this, YearLevel::class.java)
            startActivity(i)
        }
    }
}