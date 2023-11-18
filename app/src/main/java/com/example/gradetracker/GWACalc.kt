package com.example.gradetracker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class GWACalc : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val courseList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gwascore)

        fun toMENU(view: View) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Logout")
            alertDialogBuilder.setMessage("Are you sure you want to log out?")

            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                val i = Intent(this, Login::class.java)
                startActivity(i)
                finish()
            }

            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        fun toCalcGWA(view: View) {
            val i = Intent(this, YearLevel::class.java)
            startActivity(i)
        }

        fun toYEARLEVEL(view: View) {
            val i = Intent(this, YearLevel::class.java)
            startActivity(i)
        }
    }
}