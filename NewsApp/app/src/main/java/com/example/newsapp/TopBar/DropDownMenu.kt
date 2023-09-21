package com.example.newsapp.TopBar

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.UserAccount.UserViewModel


@Composable
fun DropDown(navController: NavHostController) {
    val stateHolder = exposedAccountStateHolder()
    AccountMenu(stateHolder = stateHolder, navController = navController)
}

@Composable
fun AccountMenu(stateHolder: AccountStateHolder, navController: NavHostController) {

    val userVM = viewModel<UserViewModel>()


    Column{
        Box {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_account),
                        tint = Color.White,
                        contentDescription = "Account",
                        modifier = Modifier
                            .size(35.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = { stateHolder.onEnabled(!(stateHolder.enabled)) },
                                )
                            }
                            .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) }
                    )
                }

        DropdownMenu(
            expanded = stateHolder.enabled,
            onDismissRequest = {
                stateHolder.onEnabled(false)
            }, offset = DpOffset(x = 0.dp, y = 0.dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { stateHolder.size.width * 5.toDp() })
                .background(color = Color(0xFF93A56A))
                .border(width = 2.dp, color = Color(0xFF647048))
        ) {
            stateHolder.items.forEachIndexed{index, s ->
                DropdownMenuItem(
                    onClick = {
                        Log.v(ContentValues.TAG, "index=$index")
                        //For testing purposes, keep stateHolder commented!
                        stateHolder.onEnabled(false)
                        //Navigate has to be inside an onClick to work properly, otherwise it may cause problems in the future. For exmaple flashing and unresponsive screen
                        if (index == 0){
                            navController.navigate("user_account")
                        }
                        if (index == 1){
                            userVM.logOutUser()
                            navController.navigate("home")
                        }
                    }
                ) {
                    Text(text = s, fontSize = 10.sp, color = Color.White)
                }
            }
        }
    }
}
