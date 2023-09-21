package com.example.newsapp.UserAccount

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun CreateUserView(userVM: UserViewModel, navController: NavHostController) {


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    fun mToast(context: Context, email: String){
        Toast.makeText(context, "Käyttäjä $email luotu", Toast.LENGTH_LONG).show()
    }
    fun mToast2(context: Context, email: String){
        Toast.makeText(context, "Sähköposti $email on jo käytössä", Toast.LENGTH_LONG).show()
    }

    val mContext = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF40472E)),
        contentAlignment = Alignment.Center)
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.98f)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = "User",
                Modifier.size(250.dp),
                tint = Color(0xFF2F3522)
            )
            Spacer(modifier = Modifier.size(50.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Sähköposti") }
            )

            //Login to firebase
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        if ( !passwordVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                                contentDescription = "Hide",
                                Modifier.size(35.dp),
                                tint = Color(0xFF2F3522)
                            )
                        }
                        else
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                                contentDescription = "UnHide",
                                Modifier.size(35.dp),
                                tint = Color(0xFF5F1717)
                            )
                    }
                },
                onValueChange = { password = it },
                label = { Text(text = "Salasana") },
            )

            Spacer(modifier = Modifier.size(15.dp))

            Row( modifier = Modifier.background(Color.LightGray)) {

                if (password.length == 6 || password == password.lowercase()) {
                    if (password.isNotEmpty()){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color(0xFFFF4D4D))
                    )}
                }
                if(password.length > 6 && password != password.lowercase()){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color(0x6B4CAF50))
                    )
                }
                if (password.isEmpty()){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(6.dp)
                            .background(Color(0x81F5F1F1)).border(width = 1.dp, color = Color.LightGray)
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                if (password.length <= 8 || password == password.lowercase()) {
                    if (password.length >= 7){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color(0xFFFD2525))
                    )}
                }
                if(password.length > 8 && password != password.lowercase()){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color(0x7C4CAF50))
                    )
                }
                if(password.length < 7 ){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(6.dp)
                            .background(Color(0x81F5F1F1)).border(width = 1.dp, color = Color.LightGray)
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                if (password.length >= 10 && password != password.lowercase()) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color(0xFF4CAF50))
                    )
                }
                if (password.length >= 10 && password == password.lowercase()) {
                    if (password.length >= 10) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(2.dp)
                                .background(Color(0xFFFF0000))
                        )
                    }
            }
                if(password.length < 10 ){
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(6.dp)
                            .background(Color(0x81F5F1F1)).border(width = 1.dp, color = Color.LightGray)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))

            Box(modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.25f)) {
                Column() {
                    if (password.length < 6) {
                        Text(
                            text = "Salasanan tulee olla vähintään 6 merkkiä pitkä, sisältäen isoja kirjaimia sekä numeroita",
                            fontSize = 11.sp, textAlign = TextAlign.Center
                        )
                    }else if (password == password.lowercase() || password == password.uppercase()){
                        Text(
                            text = "Salasanasta puuttuu isoja/pieniä kirjaimia",
                            fontSize = 11.sp, textAlign = TextAlign.Center
                        )
                    }

                }

            }


            Spacer(modifier = Modifier.size(35.dp))


            if (password.length < 6){
                Text(
                    text = "Salasana on liian lyhyt",
                    fontSize = 11.sp, textAlign = TextAlign.Center
                )
            } else {
                OutlinedButton(onClick = {
                    userVM.CreateUser(email, password)
                    Thread.sleep(2000)
                    if(Firebase.auth.currentUser?.email == null) {
                        Failure = true
                    }
                    if (Failure){
                        email = ""
                        password = ""
                        mToast2(mContext,email)
                    }
                    if (!Failure) {
                        mToast(mContext, email)
                        email = ""
                        password = ""
                        Thread.sleep(1000)
                        navController.navigate("home")
                    }
                }, colors = ButtonDefaults.buttonColors(Color(0xFF99AD6C))
                ) {
                    Text(text = "Luo käyttäjä", color = Color(0xFF4C5537))
                }
            }


        }
    }
}

