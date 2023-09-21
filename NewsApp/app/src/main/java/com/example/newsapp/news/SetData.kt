package com.example.newsapp.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsapp.MainNavigation
import com.example.newsapp.news.NewsViewModel.NewsViewModel
import com.example.newsapp.news.models.News
import com.example.newsapp.news.sealed.DataState

@Composable
fun SetData(viewModel: NewsViewModel, navigateToProfile: (News) -> Unit) {
    when(val result = viewModel.response.value){
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }
        }
        is DataState.Success -> {
            MainNavigation(result.data, navigateToProfile)
        }
        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = result.message,
                    fontSize = MaterialTheme.typography.h5.fontSize
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = "Error fetching data",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )
            }
        }
    }
}