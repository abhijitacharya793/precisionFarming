package com.example.phasal

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phasal.Model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_account_settings.*

@Suppress("DEPRECATION")
class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var firebaseUser :FirebaseUser
    private var check = ""
    private var myUrl = ""
    private var imageUri : Uri? = null
    private var storageProfilePicsRef : StorageReference? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilePicsRef = FirebaseStorage.getInstance().reference.child("Profile Pics")

        logout_account_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@AccountSettingsActivity, SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        save_profile_btn.setOnClickListener {
            if(check=="clicked"){
                uploadImageAndInfo()

            }else{
                updateUserInfoOnly()
            }
        }
        profile_image_view_profile_image.setOnClickListener {
            check = "clicked"
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@AccountSettingsActivity)
        }

        getUserinfo()
        getRegionList()

    }

    private fun getRegionList() {
        val regionRef = FirebaseDatabase.getInstance().reference.child("Region")

    }

    private fun uploadImageAndInfo() {




        if(enter_full_name.text.toString()==""){
            Toast.makeText(this, "Please enter Full name", Toast.LENGTH_LONG)
        }
        else if(enter_user_name.text.toString()==""){
            Toast.makeText(this, "Please enter User name", Toast.LENGTH_LONG)
        }
        else if(enter_bio.text.toString()==""){
            Toast.makeText(this, "Please enter Bio", Toast.LENGTH_LONG)
        }
        else if(imageUri==null){
            Toast.makeText(this, "Please select a profile pic", Toast.LENGTH_LONG)
        }
        else{
            val progressDialog  = ProgressDialog(this@AccountSettingsActivity)
            progressDialog.setTitle("Updating your user information")
            progressDialog.setMessage("Please wait... this may take some while ")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val fileRef = storageProfilePicsRef!!.child(firebaseUser!!.uid+".jpg")
            var uploadTask :StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)
            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it
                        progressDialog.dismiss()
                    }
                }
                return@Continuation fileRef.downloadUrl

            }).addOnCompleteListener ( OnCompleteListener<Uri>{task ->
                if(task.isSuccessful ){
                val downloadurl = task.result
                    myUrl = downloadurl.toString()

                    val ref = FirebaseDatabase.getInstance().reference.child("Users")
                    val userMap = HashMap<String,Any>()
                    userMap["fullname"] = enter_full_name.text.toString().toLowerCase()
                    userMap["username"] = enter_user_name.text.toString().toLowerCase()
                    userMap["bio"] = enter_bio.text.toString().toLowerCase()
                    userMap["image"] = myUrl

                    ref.child(firebaseUser.uid).updateChildren(userMap)

                    Toast.makeText(this, "Account Information has been updated", Toast.LENGTH_LONG)
                    val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    progressDialog.dismiss()
                }else{
                    progressDialog.dismiss()
                }
                })



        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null ){
            val result = CropImage.getActivityResult(data)
            imageUri =result.uri
            profile_image_view_profile_image.setImageURI(imageUri)


        }
    }

    private fun getUserinfo(){
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                /*if(context != null) {
                    return
                }*/
                if(p0.exists()){
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getimage()).placeholder(R.drawable.profile).into(profile_image_view_profile_image)
                    enter_full_name.setText(user.getfullname())
                    enter_user_name.setText(user.getUsername())
                    enter_bio.setText(user.getbio())
                }
            }

        })
    }

    private fun updateUserInfoOnly(){
        if(enter_full_name.text.toString()==""){
            Toast.makeText(this, "Please enter Full name", Toast.LENGTH_LONG)
        }
        else if(enter_user_name.text.toString()==""){
            Toast.makeText(this, "Please enter User name", Toast.LENGTH_LONG)
        }
        else if(enter_bio.text.toString()==""){
            Toast.makeText(this, "Please enter Bio", Toast.LENGTH_LONG)
        }
        else{

        val userRef = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["fullname"] = enter_full_name.text.toString().toLowerCase()
        userMap["username"] = enter_user_name.text.toString().toLowerCase()
        userMap["bio"] = enter_bio.text.toString().toLowerCase()

        userRef.child(firebaseUser.uid).updateChildren(userMap)

            Toast.makeText(this, "Account Information has been updated", Toast.LENGTH_LONG)
            val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }

}
