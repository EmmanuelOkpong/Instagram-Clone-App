package com.example.instagram

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val signUpButton:Button= findViewById(R.id.register)

        val alredayHave=findViewById<Button>(R.id.move_to_login)
        alredayHave.setOnClickListener {
            startActivity(Intent(this,SiginActivity::class.java))
        }
        signUpButton.setOnClickListener {
            createAccount()
        }

    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDial:ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("")

        val useMap = HashMap<String,Any>()
        useMap["uid"]=currentUserID
        useMap["fullName"]=findViewById<EditText>(R.id.fullname).text.toString()
        useMap["user Name"]=findViewById<TextView>(R.id.userName).text.toString()
        useMap["email"]=findViewById<TextView>(R.id.email).text.toString()
        useMap["bio"]="Hey. i am using Instagram Clone App"
        useMap["image"]="gs://instagram-clone-971b4.appspot.com/Defaults images/profile.png"

        usersRef.child(currentUserID).setValue(useMap)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    progressDial.dismiss()
                    Toast.makeText(this,"Account Successfully Created",Toast.LENGTH_LONG).show()

                    val intent=Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }

                else
                {
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDial.dismiss()

                }

            }

    }

    private fun createAccount() {
        val fullName=findViewById<TextView>(R.id.fullname).text.toString().lowercase()
        val userName=findViewById<TextView>(R.id.userName).text.toString().lowercase()
        val email=findViewById<TextView>(R.id.email).text.toString()
        val password=findViewById<TextView>(R.id.password).text.toString()

        when {
            TextUtils.isEmpty(fullName)-> Toast.makeText(this, "full name required",Toast.LENGTH_SHORT).show()

            TextUtils.isEmpty(userName)-> Toast.makeText(this, "User name required",Toast.LENGTH_SHORT).show()

            TextUtils.isEmpty(email)-> Toast.makeText(this, "Email Address required",Toast.LENGTH_SHORT).show()

            TextUtils.isEmpty(password)-> Toast.makeText(this, "Password required",Toast.LENGTH_SHORT).show()

            else ->{
                val progressDial=ProgressDialog(this@SignUpActivity)
                progressDial.setTitle("SignUo")
                progressDial.setMessage("Please this may take some time")
                progressDial.setCanceledOnTouchOutside(false)
                progressDial.show()

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task->
                        if (task.isSuccessful){
                            saveUserInfo(fullName,userName,email,progressDial)
                        }


                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()
                            progressDial.dismiss()
                        }
            }
        }

    }
}}