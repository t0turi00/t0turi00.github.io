package com.example.newsapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.newsapp.news.NewsViewModel.NewsViewModel
import com.example.newsapp.news.SetData
import com.example.newsapp.news.models.News
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                MyApp{
                    startActivity(NewsActivity.newIntent(this, it))
                }
            }
        }
    }
}

@Composable
fun MyApp(navigateToProfile: (News) -> Unit) {
    Scaffold(
        content = {
            SetData(navigateToProfile = navigateToProfile, viewModel = NewsViewModel())
        }
    )

}