package com.example.newsapp.TopBar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.*
import com.example.newsapp.R
import com.example.newsapp.UserAccount.UserViewModel
import com.example.newsapp.news.NewsViewModel.NewsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopMainBarView(lazyListState: LazyListState, navController: NavHostController) {
    //val userVM = viewModel<UserViewModel>()
    var expanded by remember { mutableStateOf (false) }
    var expanded2 by remember { mutableStateOf (false) }
    var expanded3 by remember { mutableStateOf (false) }
    val viewModel = viewModel<NewsViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF93A56A))
            .padding(10.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "AL", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.White)
       // TopAppBarComponents(navController, UserViewModel(), newsViewModel = NewsViewModel())
        //val userVM = viewModel<UserViewModel>()

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
                TextField(value =searchText, onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier.width(150.dp).height(50.dp),
                    singleLine = true,
                    leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null, modifier = Modifier.clickable { expanded = false })},
                    placeholder = { Text(text = "Etsi")}
                )
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
                expanded3 = true
            }
            if (!expanded2){
                expanded3 = false
            }
        }
    }

     var scroll by remember { mutableStateOf (56.dp) }
    if (expanded3){
        scroll = 365.dp
    }
    if (!expanded3){
        scroll = 56.dp
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (lazyListState.isScrolled) 0.dp else scroll),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!expanded3){
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
            Column(horizontalAlignment = Alignment.Start) {
            if (expanded3){
                Text(
                    text = "Uutiset",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                )
                Text(
                    text = "Kotimaa",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Ulkomaat",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Politiikka",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Pääkirjoitus",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Tampere",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Turku",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Oulu",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Helsinki",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Espoo",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "Vantaa",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!expanded3) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_trending),
                    contentDescription = "Trending",
                    Modifier.size(35.dp),
                    tint = Color.Black
                )
                Text(text = "Nousussa", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.Start) {
                if (expanded3) {
                    Text(
                        text = "Viihde",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "TV & elokuva",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Musiikki",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Muoti",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Taide",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Pelit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )

                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!expanded3){
            Icon(
                painter = painterResource(id = R.drawable.ic_new),
                contentDescription = "Newest",
                Modifier.size(35.dp),
                tint = Color.Black
            )
            Text(text = "Uudet", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.Start) {
                if (expanded3) {
                    Text(
                        text = "Urheilu",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Formula 1",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Ravit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Jalkapallo",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Jääkiekko",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Hiihtolajit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Yleisurheilu",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "SM-liiga",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "NHL",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Veikkausliiga",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Ralli",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!expanded3){
            Icon(
                painter = painterResource(id = R.drawable.ic_cloud),
                contentDescription = "Search",
                Modifier.size(35.dp),
                tint = Color.Black
            )
            Text(text = "Sää", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.Start) {
                if (expanded3) {
                    Text(
                        text = "Ruoka",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Ravintolat",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Reseptit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Viinit",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Oluet",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Hyvä olo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Terveys",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Laihdutus",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Kuntoilu",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(color = Color.LightGray)
    )
}

@Composable
fun SimplifiedMainBarView(lazyListState: LazyListState, navController: NavHostController) {
    //val userVM = viewModel<UserViewModel>()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFC8E090))
            .padding(10.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
            Text(text = "AL", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF93A56A))
            SimplifiedBarComponents(lazyListState, navController)
    }
}
