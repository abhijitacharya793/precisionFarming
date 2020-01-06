package com.example.phasal

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_post.*

@Suppress("DEPRECATION")
class AddPostActivity : AppCompatActivity() {

    private lateinit var firebaseUser : FirebaseUser
    private var myUrl = ""
    private var imageUri : Uri? = null
    private var storagePostPicsRef : StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storagePostPicsRef = FirebaseStorage.getInstance().reference.child("Post Pics")

        save_post_btn.setOnClickListener { uploadImage() }
        close_add_post_btn.setOnClickListener {
            startActivity(Intent(this@AddPostActivity, MainActivity::class.java))
        }

        CropImage.activity()
            .setAspectRatio(1,1)
            .start(this@AddPostActivity)

    }

    private fun uploadImage() {

        when{


            imageUri == null->Toast.makeText(this,"Please select image first",Toast.LENGTH_LONG)
            TextUtils.isEmpty(post_description.text) -> Toast.makeText(this,"Please Add short description", Toast.LENGTH_LONG)

            else ->{

                val progressDialog  = ProgressDialog(this@AddPostActivity)
                progressDialog.setTitle("Adding your new post")
                progressDialog.setMessage("Please wait... this may take some while ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val fileRef = storagePostPicsRef!!.child(System.currentTimeMillis().toString() + ".jpg")
                var uploadTask : StorageTask<*>
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
                        val oldlikes : Int = 0

                        val ref = FirebaseDatabase.getInstance().reference.child("Post")
                        val postid = ref.push().key
                        val postMap = HashMap<String,Any>()
                        postMap["postid"] = postid!!
                        postMap["description"] = post_description.text.toString()
                        postMap["publisher"] = firebaseUser.uid
                        postMap["postimage"] = myUrl
                        postMap["comment"] = "Crop......Disease......Remedies"


                        ref.child(postid).updateChildren(postMap)

                        Toast.makeText(this, "New post added ", Toast.LENGTH_LONG)
                        val intent = Intent(this@AddPostActivity, MainActivity::class.java)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null ){
            val result = CropImage.getActivityResult(data)
            imageUri =result.uri
            View_post_image.setImageURI(imageUri)


        }
    }

}
