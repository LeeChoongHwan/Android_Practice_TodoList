package com.example.android_inflearn_todolist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_inflearn_todolist.databinding.ActivityMainBinding
import com.example.android_inflearn_todolist.db.AppDatabase
import com.example.android_inflearn_todolist.db.TodoDao
import com.example.android_inflearn_todolist.db.TodoEntity

class MainActivity : AppCompatActivity(), OnItemLongClickListener {

    private lateinit var binding : ActivityMainBinding

    private lateinit var db : AppDatabase
    private lateinit var todoDao: TodoDao
    private lateinit var todoList : ArrayList<TodoEntity>
    private lateinit var adapter: TodoRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()

        binding.btnMainAddtodo.setOnClickListener {
            val intent = Intent(this,CreateTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAllTodo())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList, this)
            binding.recyclerviewMain.adapter = adapter
            binding.recyclerviewMain.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLonClick(position: Int) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_no), null)
        builder.setPositiveButton(getString(R.string.alert_yes),
            object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)
                }

            })
        builder.show()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTodo(position : Int) {
        Thread {
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }.start()
    }
}