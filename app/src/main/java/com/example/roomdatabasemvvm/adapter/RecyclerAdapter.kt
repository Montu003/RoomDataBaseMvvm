package com.example.roomdatabasemvvm.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasemvvm.databinding.ItemUserBinding
import com.example.roomdatabasemvvm.model.LoginTableModel

class RecyclerAdapter(var activity: Activity, var userList: MutableList<LoginTableModel>) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    lateinit var binding: ItemUserBinding

    class RecyclerViewHolder(itemView: ItemUserBinding) : RecyclerView.ViewHolder(itemView.root) {

        var bind = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(activity), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var user = userList[position]

        holder.bind.tvName.text = "${user.Username}"
        holder.bind.tvEmail.text = "${user.Password}"
    }

    override fun getItemCount() = userList.size

    fun setData(userList: MutableList<LoginTableModel>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}