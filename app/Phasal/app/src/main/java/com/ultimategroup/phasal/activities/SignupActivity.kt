package com.ultimategroup.phasal

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        login_btn_su.setOnClickListener{
            startActivity(Intent(this,SigninActivity::class.java))
        }

        sign_up_btn_su.setOnClickListener {
            CreateAccount()
        }
    }

    fun CreateAccount() {
        val fullname = enter_full_name_su.text.toString().toLowerCase()
        val username = enter_user_name_su.text.toString().toLowerCase()
        val email = enter_email_su.text.toString()
        val password = enter_password_su.text.toString()

        when{
            TextUtils.isEmpty(fullname) -> Toast.makeText(this,"Full name is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(username) -> Toast.makeText(this,"User name is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required",Toast.LENGTH_LONG)

            else -> {
                val progressDialog  = ProgressDialog(this@SignupActivity)
                progressDialog.setTitle("Signup")
                progressDialog.setMessage("Please wait... this may take some while ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            saveUserInfo(fullname,username,email,progressDialog)
                        }
                        else{
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Sign up Error",Toast.LENGTH_LONG)
                            progressDialog.dismiss()
                        }
                    }
            }
            }
    }

    fun saveUserInfo(fullname: String, username: String, email: String,progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullname
        userMap["username"] = username
        userMap["email"] = email
        userMap["bio"] = "Hey im on PHASAL "
        userMap["image"] = "gs://agrifyp2019.appspot.com/profile_icon.png"

        userRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()

                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(currentUserID)
                        .child("Following").child(currentUserID).setValue(true)

                    Toast.makeText(this, "Account has been created", Toast.LENGTH_LONG)
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Sign up Error",Toast.LENGTH_LONG)
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()


                }
            }
    }
    internal fun currentUsername() :String{
        val currentusername :String = enter_user_name_su.text.toString().toLowerCase()
        return currentusername

    }

}
