package com.example.gradetracker

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CombinedDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "grades.db"

        private const val TABLE_GRADES = "grades_data"
        private const val COL1 = "ID"
        private const val COL2 = "COURSE"
        private const val COL3 = "UNITS"
        private const val COL4 = "YEARSEM"
        private const val COL5 = "QUIZ_PERCENTAGE"
        private const val COL6 = "CLASS_STANDING_PERCENTAGE"
        private const val COL7 = "EXAMS_PERCENTAGE"
        private const val COL8 = "PRELIM_GRADE"
        private const val COL9 = "FINAL_GRADE"
        private const val COL10 = "PRELIM_QUIZ_SCORE"
        private const val COL11 = "PRELIM_QUIZ_TS"
        private const val COL12 = "PRELIM_CS_SCORE"
        private const val COL13 = "PRELIM_CS_TS"
        private const val COL14 = "PRELIM_EXAM_SCORE"
        private const val COL15 = "PRELIM_EXAM_TS"
        private const val COL16 = "FINALS_QUIZ_SCORE"
        private const val COL17 = "FINALS_QUIZ_TS"
        private const val COL18 = "FINALS_CS_SCORE"
        private const val COL19 = "FINALS_CS_TS"
        private const val COL20 = "FINALS_EXAM_SCORE"
        private const val COL21 = "FINALS_EXAM_TS"

        private const val TABLE_USER = "UserTable"
        private const val KEY_UNAME = "uname"
        private const val KEY_SNUM = "snum"
        private const val KEY_PWORD = "pword"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createGradesTable = ("CREATE TABLE " + TABLE_GRADES + "("
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " REAL, "
                + COL5 + " REAL, "
                + COL6 + " REAL, "
                + COL7 + " REAL, "
                + COL8 + " REAL, "
                + COL9 + " REAL, "
                + COL10 + " REAL, "
                + COL11 + " REAL, "
                + COL12 + " REAL, "
                + COL13 + " REAL, "
                + COL14 + " REAL, "
                + COL15 + " REAL, "
                + COL16 + " REAL, "
                + COL17 + " REAL, "
                + COL18 + " REAL, "
                + COL19 + " REAL, "
                + COL20 + " REAL, "
                + COL21 + " REAL)")
        db.execSQL(createGradesTable)

        val createUserTable = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_UNAME + " TEXT PRIMARY KEY, "
                + KEY_SNUM + " TEXT, "
                + KEY_PWORD + " TEXT)")
        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GRADES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // Method to add a new user record
    fun addUser(userName: String, studentNumber: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_UNAME, userName);
        contentValues.put(KEY_SNUM, studentNumber)
        contentValues.put(KEY_PWORD, password)

        val success = db.insert(TABLE_USER, null, contentValues)
        db.close()
        return success
    }

    fun deleteUser(userName: String): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_USER, "$KEY_UNAME=?", arrayOf(userName))
        db.close()
        return success
    }

    // Method to update a user record
    fun updateUser(oldUserName: String, newUserName: String, newStudentNumber: String, newPassword: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_UNAME, newUserName)
        contentValues.put(KEY_SNUM, newStudentNumber)
        contentValues.put(KEY_PWORD, newPassword)

        val success = db.update(TABLE_USER, contentValues, "$KEY_UNAME=?", arrayOf(oldUserName))
        db.close()
        return success
    }

    // Method to retrieve a single user record
    @SuppressLint("Range")
    fun getUser(userName: String, pword: String): EmpClassModel? {
        val db = this.readableDatabase
        var user: EmpClassModel? = null

        val cursor = db.query(
            TABLE_USER,
            arrayOf(KEY_UNAME, KEY_SNUM, KEY_PWORD),
            "$KEY_UNAME=?",
            arrayOf(userName),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            val uname = cursor.getString(cursor.getColumnIndex(KEY_UNAME))
            val snum = cursor.getString(cursor.getColumnIndex(KEY_SNUM))
            val pword = cursor.getString(cursor.getColumnIndex(KEY_PWORD))
            user = EmpClassModel(uname, snum, pword)
        }
        cursor.close()
        db.close()
        return user
    }

    // Method to retrieve all user records
    @SuppressLint("Range")
    fun getAllUsers(): List<EmpClassModel> {
        val userList = ArrayList<EmpClassModel>()
        val selectQuery = "SELECT * FROM $TABLE_USER"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val userName = cursor.getString(cursor.getColumnIndex(KEY_UNAME))
                val studentNumber = cursor.getString(cursor.getColumnIndex(KEY_SNUM))
                val password = cursor.getString(cursor.getColumnIndex(KEY_PWORD))
                val user = EmpClassModel(userName, studentNumber, password)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }
    // Method to add a new grade record
    fun addGrade(
        course: String = "DefaultCourse",
        units: String = "DefaultUnits",
        yearSem: String = "DefaultYearSem",
        quizPercentage: Double = 0.0,
        classStandingPercentage: Double = 0.0,
        examsPercentage: Double = 0.0,
        prelimGrade: Double = 0.0,
        finalGrade: Double = 0.0,
        prelimQuizScore: Double = 0.0,
        prelimQuizTS: Double = 0.0,
        prelimCSScore: Double = 0.0,
        prelimCSTS: Double = 0.0,
        prelimExamScore: Double = 0.0,
        prelimExamTS: Double = 0.0,
        finalsQuizScore: Double = 0.0,
        finalsQuizTS: Double = 0.0,
        finalsCSScore: Double = 0.0,
        finalsCSTS: Double = 0.0,
        finalsExamScore: Double = 0.0,
        finalsExamTS: Double = 0.0
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL2, course)
            put(COL3, units)
            put(COL4, yearSem)
            put(COL5, quizPercentage)
            put(COL6, classStandingPercentage)
            put(COL7, examsPercentage)
            put(COL8, prelimGrade)
            put(COL9, finalGrade)
            put(COL10, prelimQuizScore)
            put(COL11, prelimQuizTS)
            put(COL12, prelimCSScore)
            put(COL13, prelimCSTS)
            put(COL14, prelimExamScore)
            put(COL15, prelimExamTS)
            put(COL16, finalsQuizScore)
            put(COL17, finalsQuizTS)
            put(COL18, finalsCSScore)
            put(COL19, finalsCSTS)
            put(COL20, finalsExamScore)
            put(COL21, finalsExamTS)
        }
        val success = db.insert(TABLE_GRADES, null, contentValues)
        db.close()
        return success
    }

    fun deleteGrade(course: String): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_GRADES, "$COL2=?", arrayOf(course))
        db.close()
        return success
    }

    // Method to update a grade record
    fun updateGrade(oldCourse: String, newCourse: String, newUnits: String, newYearSem: String, newQuizPercentage: Double, newClassStandingPercentage: Double, newExamsPercentage: Double, newPrelimGrade: Double, newFinalGrade: Double, newPrelimQuizScore: Double, newPrelimQuizTS: Double, newPrelimCSScore: Double, newPrelimCSTS: Double, newPrelimExamScore: Double, newPrelimExamTS: Double, newFinalsQuizScore: Double, newFinalsQuizTS: Double, newFinalsCSScore: Double, newFinalsCSTS: Double, newFinalsExamScore: Double, newFinalsExamTS: Double): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL2, newCourse)
            put(COL3, newUnits)
            put(COL4, newYearSem)
            put(COL5, newQuizPercentage)  // Adjusted from COL4 to COL5
            put(COL6, newClassStandingPercentage)
            put(COL7, newExamsPercentage)
            put(COL8, newPrelimGrade)
            put(COL9, newFinalGrade)  // Adjusted from COL8 to COL9
            put(COL10, newPrelimQuizScore)
            put(COL11, newPrelimQuizTS)
            put(COL12, newPrelimCSScore)
            put(COL13, newPrelimCSTS)
            put(COL14, newPrelimExamScore)
            put(COL15, newPrelimExamTS)
            put(COL16, newFinalsQuizScore)
            put(COL17, newFinalsQuizTS)
            put(COL18, newFinalsCSScore)
            put(COL19, newFinalsCSTS)
            put(COL20, newFinalsExamScore)
            put(COL21, newFinalsExamTS)
        }
        val success = db.update(TABLE_GRADES, contentValues, "$COL2=?", arrayOf(oldCourse))
        db.close()
        return success
    }

    // Method to retrieve a single grade record
    fun getGrade(course: String): Cursor {
        val db = this.readableDatabase
        return db.query(TABLE_GRADES, null, "$COL2=?", arrayOf(course), null, null, null)
    }

    // Method to retrieve all grade records
    fun getAllGrades(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_GRADES", null)
    }

    // ... (previous code in CombinedDatabase)

    // Method to retrieve all grade records as a list of GradeModel objects
    @SuppressLint("Range")
    fun getAllGradeModels(): ArrayList<EmpClassModel> {
        val gradeList = ArrayList<EmpClassModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_GRADES", null)

        if (cursor.moveToFirst()) {
            do {
                val grade = EmpClassModel(
                    userName = "",
                    userSNumber = "",
                    userPass = "",
                    course = cursor.getString(cursor.getColumnIndex(COL2)),
                    units = cursor.getString(cursor.getColumnIndex(COL3)),
                    yearSem = cursor.getString(cursor.getColumnIndex(COL4)),  // Added this line
                    quizPercentage = cursor.getDouble(cursor.getColumnIndex(COL5)),  // Adjusted from COL4 to COL5
                    classStandingPercentage = cursor.getDouble(cursor.getColumnIndex(COL6)),
                    examsPercentage = cursor.getDouble(cursor.getColumnIndex(COL7)),
                    prelimGrade = cursor.getDouble(cursor.getColumnIndex(COL8)),
                    finalGrade = cursor.getDouble(cursor.getColumnIndex(COL9)),  // Adjusted from COL8 to COL9
                    prelimQuizScore = cursor.getDouble(cursor.getColumnIndex(COL10)),
                    prelimQuizTS = cursor.getDouble(cursor.getColumnIndex(COL11)),
                    prelimCSScore = cursor.getDouble(cursor.getColumnIndex(COL12)),
                    prelimCSTS = cursor.getDouble(cursor.getColumnIndex(COL13)),
                    prelimExamScore = cursor.getDouble(cursor.getColumnIndex(COL14)),
                    prelimExamScoreTS = cursor.getDouble(cursor.getColumnIndex(COL15)),
                    finalsQuizScore = cursor.getDouble(cursor.getColumnIndex(COL16)),
                    finalsQuizTS = cursor.getDouble(cursor.getColumnIndex(COL17)),
                    finalsCSScore = cursor.getDouble(cursor.getColumnIndex(COL18)),
                    finalsCSTS = cursor.getDouble(cursor.getColumnIndex(COL19)),
                    finalsExamScore = cursor.getDouble(cursor.getColumnIndex(COL20)),
                    finalsExamScoreTS = cursor.getDouble(cursor.getColumnIndex(COL21))  // Adjusted from COL20 to COL21
                )
                gradeList.add(grade)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return gradeList
    }

    fun updateSemestralGradeTS(course: String, averageFinalGrade: Double) {

    }
    private fun convertGrade(grade: Double): Double {
        return when (grade) {
            in 0.0..74.0 -> 5.00
            in 75.0..78.0 -> 3.00
            in 79.0..81.0 -> 2.75
            in 82.0..83.0 -> 2.50
            in 84.0..86.0 -> 2.25
            in 87.0..88.0 -> 2.00
            in 89.0..91.0 -> 1.75
            in 92.0..93.0 -> 1.50
            in 94.0..95.0 -> 1.25
            in 96.0..100.0 -> 1.00
            else -> grade
        }
    }
    fun getAverageOfFinalGradesForYearSem(yearSem: String): Double {
        val db = this.readableDatabase
        var average = 0.0
        val sumCursor = db.rawQuery("SELECT SUM($COL9) FROM $TABLE_GRADES WHERE $COL4 = ?", arrayOf(yearSem))
        val countCursor = db.rawQuery("SELECT COUNT($COL9) FROM $TABLE_GRADES WHERE $COL4 = ?", arrayOf(yearSem))

        if (sumCursor.moveToFirst() && countCursor.moveToFirst()) {
            val sum = sumCursor.getDouble(0)
            val count = countCursor.getInt(0)
            if (count > 0) {
                average = sum / count
            }
        }

        sumCursor.close()
        countCursor.close()
        db.close()
        return average
    }

    // Inside CombinedDatabase class

    // Method to get grades and units for a specific year-semester
    @SuppressLint("Range")
    fun getGradesAndUnitsForYearSem(yearSem: String): List<Pair<Double, Int>> {
        val db = this.readableDatabase
        val gradesAndUnitsList = mutableListOf<Pair<Double, Int>>()

        val query = "SELECT $COL9, $COL3 FROM $TABLE_GRADES WHERE $COL4 = ?"
        val cursor = db.rawQuery(query, arrayOf(yearSem))

        if (cursor.moveToFirst()) {
            do {
                val rawGrade = cursor.getDouble(cursor.getColumnIndex(COL9)) // Original Final Grade
                val convertedGrade = convertGrade(rawGrade) // Apply the grade conversion
                val units = cursor.getInt(cursor.getColumnIndex(COL3)) // Units
                gradesAndUnitsList.add(Pair(convertedGrade, units))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return gradesAndUnitsList
    }


    // ... rest of the CombinedDatabase class ...
    }



private fun ContentValues.put(keyUname: String, userName: EmpClassModel) {

}
