package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentsRecyclerViewActivity : AppCompatActivity() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemClick(student: Student?)
    }

    private var students: MutableList<Student>? = null
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
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = StudentsRecyclerAdapter(students)
        findViewById<Button>(R.id.addStudentButton).setOnClickListener {
            toNew()
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
    inner class StudentsRecyclerAdapter(private val students: MutableList<Student>?): RecyclerView.Adapter<StudentViewHolder>() {
        private var listener: OnItemClickListener? = null
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

            holder.itemView.setOnClickListener {
                toDetails(position)
            }

        }
    }

    private fun toDetails(pos: Int){
        intent.setClass(this, StudentDetailsActivity::class.java)
        intent.putExtra("student", pos)
        startActivity(intent)
    }

    private fun toNew(){
        startActivity(Intent(this, AddStudentActivity::class.java))
    }
}