package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val studentIndex: Int = intent.getIntExtra("student", 0)
        val student = getStudentDataFromIntent(studentIndex)

        setStudentDetails(student)

        cancelButton.setOnClickListener {
            finish()
        }

        deleteButton.setOnClickListener {
            onDeleteClicked(student)
            startActivity(Intent(this, StudentsRecyclerViewActivity::class.java))
        }

        saveButton.setOnClickListener {
            updateStudentValues(student)
            startActivity(Intent(this, StudentsRecyclerViewActivity::class.java))
        }
    }

    private fun getStudentDataFromIntent(studentIndex: Int) : Student? {
        return Model.shared.students[studentIndex]
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

    private fun setStudentDetails(student: Student?) {
        val nameValue: TextView = findViewById(R.id.editName)
        val idValue: TextView = findViewById(R.id.editId)
        val phoneValue: TextView = findViewById(R.id.editPhone)
        val addressValue: TextView = findViewById(R.id.editAddress)
        val isChecked: CheckBox = findViewById(R.id.checkBox)

        nameValue.text = student?.name
        idValue.text = student?.id
        phoneValue.text = student?.phone
        addressValue.text = student?.address
        isChecked.isChecked = student?.isChecked ?: false
    }
}