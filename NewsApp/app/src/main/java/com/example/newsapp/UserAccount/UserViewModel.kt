package com.example.newsapp.UserAccount

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

import com.google.firebase.storage.StorageReference


var Failure: Boolean = false
var Continue: Boolean = false
var Canceled: Boolean = false

class UserViewModel: ViewModel() {

    var userEmail = mutableStateOf("")

    fun loginUser(email: String, password: String) {

        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userEmail.value = email
            }.addOnFailureListener {
                Failure = true
            }.addOnCanceledListener {
                Canceled = true
            }.addOnCompleteListener {
                Continue = true
            }
    }

    fun logOutUser() {
        Firebase.auth.signOut()
        userEmail.value = ""

    }

    fun CreateUser(email: String, password: String) {

        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userEmail.value = email
            }.addOnFailureListener {
                Failure = true
            }
    }

}



