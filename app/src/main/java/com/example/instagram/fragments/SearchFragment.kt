package com.example.instagram.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.adapter.UserAdapter
import com.example.instagram.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var recyclearview:RecyclerView?=null
    private var userAdapter: UserAdapter?=null

    private var mUser: MutableList<User>?=null
    lateinit var   searchKeyWord:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
   ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_search, container, false)
        searchKeyWord= view.findViewById<EditText>(R.id.search_editext)
       recyclearview=view.findViewById(R.id.recyclear_search)
        recyclearview?.setHasFixedSize(true)
        recyclearview?.layoutManager=LinearLayoutManager(context)

        mUser=ArrayList()
        userAdapter=context?.let {
            UserAdapter(it,mUser as ArrayList<User>,true)
        }
        recyclearview?.adapter=userAdapter

            //Life Search
        searchKeyWord.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if (searchKeyWord.text.toString()=="")
               {

               }
                else
               {
                   recyclearview?.visibility=View.VISIBLE
                   retrivewUser()
                   searchUser(p0.toString().lowercase())
                   retrivewUser()
               }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        return view
   }

    private fun searchUser(input: String) {
        val query=FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .orderByChild("fullName")
            .startAt(input)
            .endAt(input + "\uf8ff")
        query.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                mUser?.clear()
                for (snapshot in datasnapshot.children)
                {
                    val user=snapshot.getValue(User::class.java)
                    if (user!=null)
                    {
                        mUser?.add(user)
                    }
                    }
                    userAdapter?.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



        }



    private fun retrivewUser() {

       val userRef=FirebaseDatabase.getInstance().getReference().child("Users")
       userRef.addValueEventListener(object :ValueEventListener
       {
           override fun onDataChange(datasnapshot: DataSnapshot)
           {
             if (searchKeyWord.text.toString()==""){
                 mUser?.clear()
                 for (snapshot in datasnapshot.children)
                 {
                    val user=snapshot.getValue(User::class.java)
                     if (user!=null)
                     {
                         mUser?.add(user)
                     }

                     }
                 userAdapter?.notifyDataSetChanged()
             }
           }

           override fun onCancelled(error: DatabaseError) {

           }

       })
    }

}