package com.example.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cancelButton: Button = findViewById(R.id.cancelButton)
        val editButton: Button = findViewById(R.id.editButton)
        val studentIndex: Int = intent.getIntExtra("student", 0)
        val student = getStudent(studentIndex)
        setStudentDetails(student)

        cancelButton.setOnClickListener {
            finish()
        }

        editButton.setOnClickListener {
            toEdit(studentIndex)
        }

    }

    private fun getStudent(studentIndex: Int) : Student? {
        return Model.shared.students[studentIndex]
    }

    private fun setStudentDetails(student: Student?) {
        val nameValue: TextView = findViewById(R.id.nameValue)
        val idValue: TextView = findViewById(R.id.idValue)
        val phoneValue: TextView = findViewById(R.id.phoneValue)
        val addressValue: TextView = findViewById(R.id.addressValue)
        val isChecked: CheckBox = findViewById(R.id.checkBox)

        nameValue.text = student?.name
        idValue.text = student?.id
        phoneValue.text = student?.phone
        addressValue.text = student?.address
        isChecked.isChecked = student?.isChecked ?: false
    }

    private fun toEdit(studentIndex: Int){
        intent.setClass(this, EditStudentActivity::class.java)
        intent.getIntExtra("student", studentIndex)
        startActivity(intent)
    }

}