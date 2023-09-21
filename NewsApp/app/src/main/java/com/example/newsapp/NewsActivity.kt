package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.UserAccount.AccountView
import com.example.newsapp.UserAccount.CreateUserView
import com.example.newsapp.UserAccount.LoginView
import com.example.newsapp.UserAccount.UserViewModel
import com.example.newsapp.news.NewsListItem
import com.example.newsapp.news.NewsScreen
import com.example.newsapp.news.models.News
import com.example.newsapp.ui.theme.NewsAppTheme

class NewsActivity : ComponentActivity() {
    private val news: News by lazy {
        intent?.getSerializableExtra(ID) as News
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val navControl = rememberNavController()
                NewsScreen(news = news, navController = navControl, userVM = UserViewModel())
            }
        }
    }

    companion object {
        private const val ID = "id"
        fun newIntent(context: Context, news: News) =
            Intent(context, NewsActivity::class.java).apply {
                putExtra(ID, news)
            }
    }

}