package com.example.newsapp.ImageHandler.Data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Constants {
    const val IMAGES = "images"
    const val URL = "url"
    const val CREATED_AT = "createdAt"
     val UID = Firebase.auth.currentUser?.uid.toString() //change to user's own uid
     val IMAGE_NAME = "$UID.jpg"
    const val ALL_IMAGES = "image/*"
}