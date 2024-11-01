package com.example.gradetracker

class EmpClassModel(
    // User Data
    val userName: String,
    val userSNumber: String,
    val userPass: String,

    // Grades Data
    val course: String = "",
    val units: String = "",
    val yearSem: String = "",
    val quizPercentage: Double = 0.0,
    val classStandingPercentage: Double = 0.0,
    val examsPercentage: Double = 0.0,
    val prelimGrade: Double = 0.0,
    val finalGrade: Double = 0.0,
    val prelimQuizScore: Double = 0.0,
    val prelimQuizTS: Double = 0.0,
    val prelimCSScore: Double = 0.0,
    val prelimCSTS: Double = 0.0,
    val prelimExamScore: Double = 0.0,
    val prelimExamScoreTS: Double = 0.0,
    val finalsQuizScore: Double = 0.0,
    val finalsQuizTS: Double = 0.0,
    val finalsCSScore: Double = 0.0,
    val finalsCSTS: Double = 0.0,
    val finalsExamScore: Double = 0.0,
    val finalsExamScoreTS: Double = 0.0
) {
    var semestralGradeTS: Double = 0.0
}
