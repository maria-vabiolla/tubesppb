package com.example.masimelrowoo.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.masimelrowoo.CustomOnItemClickListener
import com.example.masimelrowoo.FriendListAddUpdateActivity
import com.example.masimelrowoo.R
import com.example.masimelrowoo.entity.FriendList

class FriendListAdapter (private val activity: Activity) : RecyclerView.Adapter<FriendListAdapter.NoteViewHolder>() {
    var listFriends = ArrayList<FriendList>()
        set(listFriends) {
            if (listFriends.size > 0) {
                this.listFriends.clear()
            }
            this.listFriends.addAll(listFriends)
            notifyDataSetChanged()
        }
    fun addItem(note: FriendList) {
        this.listFriends.add(note)
        notifyItemInserted(this.listFriends.size - 1)
    }
    fun updateItem(position: Int, note: FriendList) {
        this.listFriends[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listFriends.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFriends.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listFriends[position])
    }

    override fun getItemCount(): Int = this.listFriends.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv_item_title: TextView = itemView.findViewById(R.id.tv_item_title)
        private val cv_item_note: CardView = itemView.findViewById(R.id.cv_item_note)

        fun bind(friendList: FriendList) {
            with(itemView){
                tv_item_title.text = friendList.friendName


                cv_item_note.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, FriendListAddUpdateActivity::class.java)
                        intent.putExtra(FriendListAddUpdateActivity.EXTRA_POSITION, position)
                        intent.putExtra(FriendListAddUpdateActivity.EXTRA_FRIEND, friendList)
                        activity.startActivityForResult(intent, FriendListAddUpdateActivity.REQUEST_UPDATE)
                    }
                }))
            }
        }

    }
}