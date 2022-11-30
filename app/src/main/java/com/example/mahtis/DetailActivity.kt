package com.example.mahtis

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.mahtis.R
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    var foodDescription: TextView? = null
    var foodDescription2: TextView? = null
    var RecipeName: TextView? = null
    var RecipeRating: TextView? = null
    var foodImage: ImageView? = null
    var key: String? = ""
    var imageUrl: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        RecipeName = findViewById<View>(R.id.txtRecipeName) as TextView
        RecipeRating = findViewById<View>(R.id.txtRating) as TextView
        foodDescription = findViewById<View>(R.id.txtDescription) as TextView
        foodDescription2 = findViewById<View>(R.id.txtDescription2) as TextView
        foodImage = findViewById<View>(R.id.ivImage2) as ImageView
        val mBundle = intent.extras
        if (mBundle != null) {

            foodDescription!!.text = mBundle.getString("Description")
            foodDescription2!!.text = mBundle.getString("Instructions")
            key = mBundle.getString("keyValue")
            imageUrl = mBundle.getString("Image")
            RecipeName!!.text = mBundle.getString("RecipeName")
            RecipeRating!!.text = mBundle.getString("Rating")
            
            Glide.with(this)
                .load(mBundle.getString("Image"))
                .into(foodImage!!)
        }
    }
}