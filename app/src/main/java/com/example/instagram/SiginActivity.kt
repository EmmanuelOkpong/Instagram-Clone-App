package com.example.instagram

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SiginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        val newAccount=findViewById<Button>(R.id.new_Account)
        val logInBTN=findViewById<Button>(R.id.login_account_butn)
        newAccount.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java ))
        }
        logInBTN.setOnClickListener {
            logginUser()
        }
    }

    private fun logginUser() {


        val email = findViewById<EditText>(R.id.email_login).text.toString()
        val password = findViewById<EditText>(R.id.password_login).text.toString()
        when {
            TextUtils.isEmpty(email) -> Toast.makeText(
                this,
                "Email Address required",
                Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "Password required",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                val progressDial = ProgressDialog(this@SiginActivity)
                progressDial.setTitle("Sign In")
                progressDial.setMessage("Please this may take some time")
                progressDial.setCanceledOnTouchOutside(false)
                progressDial.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDial.dismiss()

                        val intent = Intent(this@SiginActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDial.dismiss()

                    }
                }
            }
        }
    }
        override fun onStart() {

            super.onStart()
            if (FirebaseAuth.getInstance().currentUser != null) {
                val intent = Intent(this@SiginActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

        }
    }


