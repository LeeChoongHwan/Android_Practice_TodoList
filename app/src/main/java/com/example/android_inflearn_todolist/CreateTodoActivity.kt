package com.example.android_inflearn_todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_inflearn_todolist.databinding.ActivityCreateTodoBinding
import com.example.android_inflearn_todolist.db.AppDatabase
import com.example.android_inflearn_todolist.db.TodoDao
import com.example.android_inflearn_todolist.db.TodoEntity

class CreateTodoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCreateTodoAddtodo.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.etCreateTodoTitle.text.toString()
        var todoImportance = binding.radiogroupCreateTodoImportance.checkedRadioButtonId

        var impData = 0
        when(todoImportance) {
            R.id.importance_high -> {
                impData = 1
            }
            R.id.importance_middle -> {
                impData = 2
            }
            R.id.importance_low -> {
                impData = 3
            }
        }

        if(impData == 0 || todoTitle.isBlank()) {
            Toast.makeText(this,"모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            Thread {
                todoDao.insertTodo(TodoEntity(null,todoTitle,impData))
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }

}