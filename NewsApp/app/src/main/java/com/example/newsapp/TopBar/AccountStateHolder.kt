package com.example.newsapp.TopBar

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AccountStateHolder() {

    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var size by mutableStateOf(Size.Zero)

    val items = listOf(
        "Käyttäjätiedot",
        "Kirjaudu ulos",
    ).map { it }

    fun onEnabled(newValue: Boolean){
        enabled = newValue
    }

    fun onSize(newValue: Size){
        size = newValue
    }
}

@Composable
fun exposedAccountStateHolder() = remember{ AccountStateHolder() }

