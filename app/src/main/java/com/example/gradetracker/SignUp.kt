package com.example.gradetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val registerButton: Button = findViewById(R.id.signup2button)
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val u_user = findViewById<EditText>(R.id.etusername)
        val u_snum = findViewById<EditText>(R.id.etsnu)
        val u_pass = findViewById<EditText>(R.id.etpasswd)

        val user = u_user.text.toString().trim()
        val snum = u_snum.text.toString().trim()
        val pword = u_pass.text.toString().trim()

        if (user.isNotEmpty() && snum.isNotEmpty() && pword.isNotEmpty()) {
            val databaseHandler = CombinedDatabase(this)

            if (isValidPassword(pword)) {
                // Create an EmpClassModel instance with only the user fields populated
                val status = databaseHandler.addUser(user, snum, pword)
                if (status > -1) {
                    Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_LONG).show()
                    clearFields(u_user, u_pass, u_snum)
                    navigateToMainActivity()
                } else {
                    Toast.makeText(this, "Credentials are used already", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "You must follow the indicated requirements for the password", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Credentials must not be blank", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields(vararg editTexts: EditText) {
        editTexts.forEach { it.text.clear() }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*\\d)" +
                    "(?=.*[@#$%^&+=!])" +
                    ".{8,}"
        )
        return passwordPattern.matcher(password).matches()
    }

    fun toMENU(view: View) {
        navigateToMainActivity()
    }
}
