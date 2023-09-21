package com.example.newsapp.UserAccount


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



@Composable
fun LoginView(userVM: UserViewModel, navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

   // val userVM = viewModel<UserViewModel>()
    val context = LocalContext.current

    fun mToast(context: Context, email: String) {
        Toast.makeText(context, "Tervetuloa käyttäjä $email", Toast.LENGTH_LONG).show()
    }

    fun mToast2(context: Context) {
        Toast.makeText(context, "Salasana tai sähköposti oli väärä", Toast.LENGTH_LONG).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF40472E)),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.98f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFC8E090),
                            Color(0xFF4C5537)
                        ),
                        startY = 100f,
                    )
                ),
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
                        if (!passwordVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                                contentDescription = "Hide",
                                Modifier.size(35.dp),
                                tint = Color(0xFF2F3522)
                            )
                        } else
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

            Text(text = "Luo tili",
                color = Color.White,
                modifier = Modifier.clickable { navController.navigate("create_user") })

            Spacer(modifier = Modifier.size(35.dp))


            OutlinedButton(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        userVM.loginUser(email, password)
                    }
                    //Adding Delay here because the email and password values are null before the actual login
                    Thread.sleep(1000)
                    Log.d("******************", email)
                    if (Firebase.auth.currentUser?.email != null) {
                        Thread.sleep(1000)
                        navController.navigate("home")
                        mToast(context, email)
                    }
                    if (Firebase.auth.currentUser?.email == null) {
                        mToast2(context)

                    }

                }, colors = ButtonDefaults.buttonColors(Color(0xFF99AD6C))
            ) {
                Text(text = "Login", color = Color(0xFF4C5537))
            }
        }
    }
}



