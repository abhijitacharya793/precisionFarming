@file:Suppress("DEPRECATION")

package com.example.phasal

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        sign_up_btn_si.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
        }
        sign_in_btn_si.setOnClickListener {
            userlogin()
        }

    }

    private fun userlogin() {

        val email_si = enter_email_si.text.toString()
        val password_si = enter_password_si.text.toString()
        when{
            TextUtils.isEmpty(email_si) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG)
            TextUtils.isEmpty(password_si) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG)
            else ->{
                val progressDialog = ProgressDialog(this@SigninActivity)
                progressDialog.setTitle("Signing in")
                progressDialog.setMessage("Please wait... this may take some while ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email_si,password_si).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            progressDialog.dismiss()

                            Toast.makeText(this, "You have signed in", Toast.LENGTH_LONG)

                            val goToMain = Intent(this@SigninActivity, MainActivity::class.java)
                            goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(goToMain)

                        }
                        else{
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Sign up Error",Toast.LENGTH_LONG)
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                            //finish()
                        }
                    }


            }

        }

    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser !=null ){
            val intent = Intent(this@SigninActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }


}
