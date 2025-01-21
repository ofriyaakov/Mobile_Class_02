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

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cancelButton: Button = findViewById(R.id.cancelButton)
        val saveButton: Button = findViewById(R.id.saveButton)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            onSaveClicked()
        }
    }

    private fun onSaveClicked() {
        val editNameText: EditText = findViewById(R.id.editName)
        val editIdText: EditText = findViewById(R.id.editId)
        val editPhoneText: EditText = findViewById(R.id.editPhone)
        val editAddressText: EditText = findViewById(R.id.editAddress)
        val checkedCheckbox: CheckBox = findViewById(R.id.checkBox)

        val student = Student(
            id = editIdText.text?.toString() ?: "",
            name = editNameText.text?.toString() ?: "",
            phone = editPhoneText.text?.toString() ?: "",
            address = editAddressText.text?.toString() ?: "",
            isChecked = checkedCheckbox.isChecked
        )

        Model.shared.addStudent(student) {
            finish()
        }
    }

}