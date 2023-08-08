package com.example.android_inflearn_todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_inflearn_todolist.databinding.ActivityCreateTodoBinding
import com.example.android_inflearn_todolist.db.AppDatabase
import com.example.android_inflearn_todolist.db.TodoDao
import com.example.android_inflearn_todolist.db.TodoEntity

class CreateTodoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateTodoBinding

    private lateinit var db : AppDatabase
    private lateinit var todoDao: TodoDao
    private lateinit var todoList : ArrayList<TodoEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()
    }

    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAllTodo())
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {

    }
}