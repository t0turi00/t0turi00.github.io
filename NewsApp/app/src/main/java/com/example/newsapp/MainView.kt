package com.example.newsapp


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.newsapp.TopBar.SecondTopBar
import com.example.newsapp.TopBar.SimplifiedMainBarView
import com.example.newsapp.TopBar.TopMainBarView
import com.example.newsapp.UserAccount.AccountView
import com.example.newsapp.UserAccount.CreateUserView
import com.example.newsapp.UserAccount.LoginView
import com.example.newsapp.UserAccount.UserViewModel
import com.example.newsapp.news.NewsListItem
import com.example.newsapp.news.NewsScreen
import com.example.newsapp.news.NewsViewModel.NewsViewModel
import com.example.newsapp.news.SetData
import com.example.newsapp.news.models.News


val TOP_BAR_HEIGHT = 56.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Composable
fun MainNavigation(news: MutableList<News>, navigateToProfile: (News) -> Unit){
    val navControl = rememberNavController()
    NavHost(navController = navControl, startDestination = "home") {
        composable("home"){ MainScreen(navControl, news, navigateToProfile) }
        composable("login"){ LoginView(userVM = UserViewModel(), navControl) }
        composable("create_user"){ CreateUserView(userVM = UserViewModel(), navControl) }
        composable("user_account"){ AccountView(userVM = UserViewModel(), navControl ) }
    }
}


@Composable
fun MainScreen(navController: NavHostController, news: MutableList<News>, navigateToProfile: (News) -> Unit) {
    val lazyListState = rememberLazyListState()

    Scaffold( content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize())
            {
                TopMainBarView(lazyListState = lazyListState, navController)
                //SecondTopBar(lazyListState = lazyListState, navController)
                Column(modifier = Modifier.fillMaxSize())
                {
                    if (lazyListState.isScrolled) SimplifiedMainBarView(lazyListState = lazyListState, navController )
                    else TOP_BAR_HEIGHT
                    //MainContent(lazyListState = lazyListState)
                    MainContent(lazyListState = lazyListState, news = news, navigateToProfile = navigateToProfile, navController = navController )

                }
            }
        }
    }
    )
}

@Composable
fun MainContent(lazyListState: LazyListState,navController: NavHostController, news: MutableList<News>, navigateToProfile: (News) -> Unit) {

    val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else 5.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val viewModel = viewModel<NewsViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Spacer(modifier = Modifier.height(10.dp))
    if (searchText.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier.padding(top = padding)
        ) {
            items(persons) { person ->
                NewsListItem(news = person, navigateToProfile, navController)
            }
        }
    }
    if (searchText.isBlank()){
    LazyColumn(modifier = Modifier.padding(top = padding), state = lazyListState) {
        items(
            items = news,
            itemContent = {
                NewsListItem(news = it, navigateToProfile, navController)
            }
        )
    }}

}

@Composable
fun BottomBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(Color(0xFF93A56A))) {
        Column() {


            Text(
                text = "AamuLehti",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp, top = 15.dp)
            )

            Text(
                text = "@ 2023 t0turi00",
                fontSize = 10.sp,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp, top = 5.dp)
            )
        }
    }
}