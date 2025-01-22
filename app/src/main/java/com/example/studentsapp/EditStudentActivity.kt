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
        val saveButton: Button = findViewById(R.id.saveButton)

        cancelButton.setOnClickListener {
            finish()
        }

        deleteButton.setOnClickListener {
            val student = getStudentDataFromIntent()
            onDeleteClicked(student)
            finish()
        }

        saveButton.setOnClickListener {
            val student = getStudentDataFromIntent()
            updateStudentValues(student)
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

    private fun updateStudentValues(student: Student?) {

        val editNameValue: EditText = findViewById(R.id.editName)
        val editedIdValue: EditText = findViewById(R.id.editId)
        val editPhoneValue: EditText = findViewById(R.id.editPhone)
        val editAddressValue: EditText = findViewById(R.id.editAddress)
        val checkboxValue: CheckBox = findViewById(R.id.checkBox)

        student?.id = editedIdValue.text?.toString() ?: ""
        student?.name = editNameValue.text?.toString() ?: ""
        student?.phone = editPhoneValue.text?.toString() ?: ""
        student?.address = editAddressValue.text?.toString() ?: ""
        student?.isChecked = checkboxValue.isChecked
    }
}