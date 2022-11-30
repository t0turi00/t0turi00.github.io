package com.example.mahtis

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.mahtis.R
import android.content.Intent
import android.app.Activity
import android.widget.Toast
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import android.app.ProgressDialog
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener
import com.example.mahtis.FoodData
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.annotations.Nullable
import java.text.DateFormat
import java.util.*

class Upload_Recipe : AppCompatActivity() {
    var recipeImage: ImageView? = null
    var uri: Uri? = null
    var txt_name: EditText? = null
    var txt_description: EditText? = null
    var txt_description2: EditText? = null
    var txt_rating: EditText? = null
    var imageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_recipe)
        recipeImage = findViewById<View>(R.id.iv_foodImage) as ImageView
        txt_name = findViewById<View>(R.id.txt_recipe_name) as EditText
        txt_description = findViewById<View>(R.id.text_description) as EditText
        txt_description2 = findViewById<View>(R.id.text_description2) as EditText
        txt_rating = findViewById<View>(R.id.text_rating) as EditText
    }

    fun btnSelectImage(view: View?) {
        val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.type = "image/*"
        startActivityForResult(photoPicker, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data!!.data
            recipeImage!!.setImageURI(uri)
        } else Toast.makeText(this, "Valitse kuva!", Toast.LENGTH_SHORT).show()
    }

    fun uploadImage() {
        val storageReference = FirebaseStorage.getInstance()
            .reference.child("RecipeImage").child(uri!!.lastPathSegment!!)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Reseptiä lähetetään....")
        progressDialog.show()
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageUrl = urlImage.toString()
            uploadRecipe()
            progressDialog.dismiss()
        }
            .addOnFailureListener { progressDialog.dismiss() }
    }

    fun btnUploadRecipe(view: View?) {
        uploadImage()
    }

    fun uploadRecipe() {
        val foodData = FoodData(
            txt_name!!.text.toString(),
            txt_description!!.text.toString(),
            txt_description2!!.text.toString(),
            txt_rating!!.text.toString(),
            imageUrl
        )
        val myCurrentDateTime = DateFormat.getDateTimeInstance()
            .format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("Recipe")
            .child(myCurrentDateTime).setValue(foodData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Upload_Recipe, "Resepti Lähetetty", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@Upload_Recipe,
                    e.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}