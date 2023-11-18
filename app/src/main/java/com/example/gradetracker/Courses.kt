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

class Courses : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val courseList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        listView = findViewById(R.id.courseslist)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseList)
        listView.adapter = adapter

        val openAddCourse = findViewById<Button>(R.id.addcourse)

        openAddCourse.setOnClickListener {
            val message = "Add a Course"
            showAddCourse(message)
        }
    }

    private fun showAddCourse(message: String) {
        val dialog2 = Dialog(this)
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog2.setCancelable(false)
        dialog2.setContentView(R.layout.addcourseentry)
        dialog2.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage2 = dialog2.findViewById<TextView>(R.id.tvMessage2)
        val btnaddcourse = findViewById<Button>(R.id.btnaddcourse)
        val btndelcourse = findViewById<Button>(R.id.delcourse)
        val btnupdcourse = findViewById<Button>(R.id.updcourse)
        val btnexit = dialog2.findViewById<Button>(R.id.btnexit)
        val course = dialog2.findViewById<EditText>(R.id.etCourse)

        tvMessage2.text = message
        dialog2.show()

        btnexit.setOnClickListener {
            dialog2.dismiss()
        }

        btnaddcourse.setOnClickListener {
            val courseName = course.text.toString().trim()
            if (courseName.isNotEmpty()) {
                courseList.add(courseName)
                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged()
                // Clear the EditText
                course.text.clear()
            }



        }

        btndelcourse.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Logout")
            alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

            alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
            }

            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
    }

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
            dialog.dismiss() // Dismiss the dialog if the user selects "No"
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun toCalcGWA(view: View) {
        val i = Intent(this, GWACalc::class.java)
        startActivity(i)
    }

    fun toYEARLEVEL(view: View) {
        val i = Intent(this, YearLevel::class.java)
        startActivity(i)
    }
}
