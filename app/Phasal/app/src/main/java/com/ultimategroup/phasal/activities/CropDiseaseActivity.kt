package com.ultimategroup.phasal.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ultimategroup.phasal.DiseaseApi
import com.ultimategroup.phasal.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class CropDiseaseActivity : AppCompatActivity() {

    private val TAG = "PermissionDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_disease)
        setupPermissions()
        dispatchTakePictureIntent()
        uploadFoto()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            var image : ImageView = findViewById(R.id.imageView)
            image.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadFoto() {
        val image_path = "/mnt/sdcard/Download/images.jpeg" //Setup image path
        val imageFile = File(image_path)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(DiseaseApi.DJANGO_SITE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postApi: DiseaseApi = retrofit.create<DiseaseApi>(DiseaseApi::class.java)
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse("multipart/data"), imageFile)
        val multiPartBody = MultipartBody.Part
            .createFormData("model_pic", imageFile.getName(), requestBody)
        val call: Call<RequestBody?>? = postApi.uploadFile(multiPartBody)
        call?.enqueue(object : Callback<RequestBody?> {
            override fun onResponse(
                call: Call<RequestBody?>,
                response: Response<RequestBody?>
            ) {
                Log.d("good", "good")
            }

            override fun onFailure(
                call: Call<RequestBody?>,
                t: Throwable
            ) {
                Log.d("fail", "fail")
            }
        })
    }
}
