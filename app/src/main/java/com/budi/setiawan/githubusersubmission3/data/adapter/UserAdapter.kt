package com.budi.setiawan.githubusersubmission3.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.budi.setiawan.githubusersubmission3.data.model.UserItems
import com.budi.setiawan.githubusersubmission3.data.util.UserDiffCallback
import com.budi.setiawan.githubusersubmission3.databinding.ItemUserBinding
import com.bumptech.glide.Glide

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val list = ArrayList<UserItems>()
    var onItemClickCallback: OnItemClickCallback? = null

    @JvmName("setOnItemClickCallback1")
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<UserItems>){
        val diffCallback = UserDiffCallback(list, users)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userItems: UserItems){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(userItems)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(userItems.avatar_url)
                    .circleCrop()
                    .into(ivAvatar)
                tvUsername.text = userItems.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: UserItems)
    }

}