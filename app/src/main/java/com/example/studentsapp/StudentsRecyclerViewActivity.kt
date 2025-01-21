package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.adapter.StudentsAdapter
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentsRecyclerViewActivity : AppCompatActivity() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemClick(student: Student?)
    }


    var students: MutableList<Student>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        students = Model.shared.students
        Log.d("TAG", "HELLO!")
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = StudentsRecyclerAdapter(students)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("TAG", "On click Activity listener on position $position")
            }
            override fun onItemClick(student: Student?) {
                Log.d("TAG", "On student clicked name: ${student?.name}")
            }
        }
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.addStudentButton).setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        students = Model.shared.students
        val adapter = StudentsRecyclerAdapter(students)
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.adapter = adapter
    }


    class StudentViewHolder(
        itemView: View,
        listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(itemView) {
        private var nameTextView: TextView? = null
        private var idTextView: TextView? = null
        private var studentCheckBox: CheckBox? = null
        private var student: Student? = null
        init {
            nameTextView = itemView.findViewById(R.id.student_row_name_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            studentCheckBox = itemView.findViewById(R.id.student_row_checkbox)
            studentCheckBox?.apply {
                setOnClickListener {
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (it as? CheckBox)?.isChecked ?: false
                    }
                }
            }
            itemView.setOnClickListener {
                Log.d("TAG", "On click listener on position $adapterPosition")
                listener?.onItemClick(student)
            }
        }
        fun bind(student: Student?, position: Int) {
            this.student = student
            nameTextView?.text = student?.name
            idTextView?.text = student?.id
            studentCheckBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
        }
    }
    class StudentsRecyclerAdapter(private val students: MutableList<Student>?): RecyclerView.Adapter<StudentViewHolder>() {
        var listener: OnItemClickListener? = null
        override fun getItemCount(): Int = students?.size ?: 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.activity_student_row,
                parent,
                false
            )
            return StudentViewHolder(itemView, listener)
        }
        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(
                student = students?.get(position),
                position = position
            )
        }
    }
}
