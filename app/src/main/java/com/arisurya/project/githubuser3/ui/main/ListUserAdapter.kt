package com.arisurya.project.githubuser3.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.databinding.ItemRowUsersBinding
import com.bumptech.glide.Glide

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val listData = ArrayList<User>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(users: ArrayList<User>) {
        listData.clear()
        listData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(imgUserAvatar)
                tvUserName.text = user.login
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size


    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}