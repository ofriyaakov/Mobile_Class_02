package com.example.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cancelButton: Button = findViewById(R.id.cancelButton)
        val deleteButton: Button = findViewById(R.id.deleteButton)

        cancelButton.setOnClickListener {
            finish()
        }

        deleteButton.setOnClickListener {
            val student = getStudentDataFromIntent()
            onDeleteClicked(student)
            finish()
        }
    }

    private fun getStudentDataFromIntent() : Student? {
        val student: Student? = intent.getParcelableExtra("student")
        return student
    }

    private fun onDeleteClicked(student: Student?) {
        Model.shared.deleteStudent(student) {
            finish()
        }
    }
}