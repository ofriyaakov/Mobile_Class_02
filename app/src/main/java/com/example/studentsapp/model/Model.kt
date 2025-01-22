package com.example.studentsapp.model

class Model private constructor() {

    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    init {
        for (i in 0..20){
            val student = Student(
                name = "Name $i",
                id = i.toString(),
                isChecked = false,
                phone = "",
                address = ""
            )

            students.add(student)
        }
    }

    fun addStudent(student: Student, callback: () -> Unit) {
        students.add(student)
        callback()
    }

    fun deleteStudent(student: Student?, callback: () -> Unit) {
        students.remove(student)
        callback()
    }

}