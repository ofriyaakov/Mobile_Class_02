package com.example.studentsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentsListVIewActivity : AppCompatActivity() {

    var students: MutableList<Student>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        students = Model.shared.students

        val listView: ListView = findViewById(R.id.students_list_view)
        listView.adapter = StudentsAdapter()

    }

    inner class StudentsAdapter(): BaseAdapter(){
        override fun getCount(): Int = students?.size ?: 0

        override fun getItem(p0: Int): Any {
//            TODO("Not yet implemented")
            return 0
        }

        override fun getItemId(p0: Int): Long {
//            TODO("Not yet implemented")
            return 0
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(parent?.context)

            val view = convertView ?: inflater.inflate(R.layout.activity_student_row, parent, false).apply {
                findViewById<CheckBox>(R.id.student_row_checkbox).apply {
                    setOnClickListener {
                        (tag as? Int)?.let { tag ->
                            val student = students?.get(tag)
                            student?.isChecked = (it as? CheckBox)?.isChecked ?: false
                        }
                    }
                }
            }


            val nameTextView: TextView? = view?.findViewById(R.id.student_row_name_text_view)
            val idTextView: TextView? = view?.findViewById(R.id.student_row_id_text_view)
            val studentCheckBox: CheckBox? = view?.findViewById(R.id.student_row_checkbox)

            val student = students?.get(position)

            nameTextView?.text = student?.name
            idTextView?.text = student?.id

            studentCheckBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
            return view!!
        }

    }
}