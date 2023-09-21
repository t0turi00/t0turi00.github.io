package com.example.newsapp.TopBar


import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.TOP_BAR_HEIGHT
import com.example.newsapp.UserAccount.UserViewModel
import com.example.newsapp.isScrolled
import com.example.newsapp.news.NewsViewModel.NewsViewModel
import com.example.newsapp.news.models.News
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


@Composable
fun TopAppBarComponents(navController: NavHostController, userViewModel: UserViewModel) {

    //val userVM = viewModel<UserViewModel>()
    var expanded by remember { mutableStateOf (false) }
    var expanded2 by remember { mutableStateOf (false) }
    var text by remember { mutableStateOf ("") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {

        if (Firebase.auth.currentUser == null) {
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = "Account",
            Modifier
                .size(35.dp)
                .clickable { navController.navigate("login") },
            tint = Color.White
        )}
        else
            DropDown(navController = navController)


        Spacer(modifier = Modifier.size(15.dp) )

        if (expanded){
        TextField(
            value = text ,
            onValueChange = {
                text = it
                expanded = false },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null, modifier = Modifier.clickable { expanded = false })},
            modifier = Modifier.width(150.dp).height(50.dp))
        }

        if(!expanded){
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            Modifier
                .size(35.dp)
                .clickable { expanded = !expanded },
            tint = Color.White
        )
        }

        Spacer(modifier = Modifier.size(15.dp) )
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu",
            Modifier
                .size(35.dp)
                .clickable { expanded2 = !expanded2 },
            tint = Color.White
        )
        if (expanded2){
            expanded2 = true
        }
        if (!expanded2){
            expanded2 = false
        }
    }
}

@Composable
fun SecondTopBar(lazyListState: LazyListState, navController: NavHostController) {

   /* var scroll by remember { mutableStateOf (56.dp) }
    if (expanded3){
        scroll = 500.dp
    } else 56.dp*/


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                Modifier
                    .size(35.dp)
                    .clickable { navController.navigate("home") },
                tint = Color.Black,
            )
            Text(text = "Etusivu",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_trending),
                contentDescription = "Trending",
                Modifier.size(35.dp),
                tint = Color.Black
            )
            Text(text = "Nousussa", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_new),
                contentDescription = "Newest",
                Modifier.size(35.dp),
                tint = Color.Black
            )
            Text(text = "Uudet", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cloud),
                contentDescription = "Search",
                Modifier.size(35.dp),
                tint = Color.Black
            )
            Text(text = "Sää", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(color = Color.LightGray)
    )
}


@Composable
fun SimplifiedBarComponents(lazyListState: LazyListState, navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {

        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Menu",
            Modifier
                .size(35.dp)
                .clickable { navController.navigate("home") },
            tint = Color(0xFF93A56A)
        )
    }
}
