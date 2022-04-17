package com.budi.setiawan.githubusersubmission3.data.util

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.budi.setiawan.githubusersubmission3.data.model.UserItems

class UserDiffCallback(private val oldList: ArrayList<UserItems>, private val newList: ArrayList<UserItems>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login === newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, id, avatar_url) = oldList[oldItemPosition]
        val (_, id1, avatar_url1) = newList[newItemPosition]

        return avatar_url == avatar_url1 && id == id1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

}