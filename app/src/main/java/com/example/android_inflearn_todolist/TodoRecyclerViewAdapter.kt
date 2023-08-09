package com.example.android_inflearn_todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_inflearn_todolist.databinding.ItemTodoBinding
import com.example.android_inflearn_todolist.db.TodoEntity

class TodoRecyclerViewAdapter(private val todoList : ArrayList<TodoEntity>, private val listener: OnItemLongClickListener) :
    RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        val root = binding.root
    }

    //myViewHolder를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    //아이템의 개수를 반환
    override fun getItemCount(): Int {
        return todoList.size
    }
    //ViewHolder와 Data를 연결
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when(todoData.importance) {
            1-> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2-> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3-> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }

        }
        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title

        holder.root.setOnLongClickListener {
            listener.onLonClick(position)
            false
        }
    }
}