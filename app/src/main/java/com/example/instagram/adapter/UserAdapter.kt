package com.example.instagram.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instagram.R
import com.example.instagram.model.User
import com.google.firebase.platforminfo.UserAgentPublisher
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (
    private var mContext: Context,
    private var mUser: List<User>,
    private val isFragment:Boolean=false):RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.ViewHolder {
       val view= LayoutInflater.from(mContext).inflate(R.layout.user_item_layout,parent,false)
                return  UserAdapter.ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return mUser.size
    }
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
            val user=mUser[position]

        holder.userTextView.text=user.getUserName()
        holder.profieTextview.text= user.getFullName()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into((holder.userImage))
    }



    class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){
        var userTextView=itemView.findViewById<TextView>(R.id.userNameSearch)
        var profieTextview=itemView.findViewById<TextView>(R.id.userFullName)
        var userImage=itemView.findViewById<CircleImageView>(R.id.userImageSearch)
        var followButton=itemView.findViewById<Button>(R.id.followButton)

    }
}