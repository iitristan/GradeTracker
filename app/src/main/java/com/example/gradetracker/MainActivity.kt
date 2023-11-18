package com.example.gradetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun toLOGIN(view: View) {
        val i = Intent(this,Login::class.java)
        startActivity(i)
    }
    fun toSIGNUP(view: View) {
        val i = Intent(this,SignUp::class.java)
        startActivity(i)
    }
    fun toMANUAL(view: View) {
        val i = Intent(this,Manual::class.java)
        startActivity(i)
    }
}
