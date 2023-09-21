package com.example.newsapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.ImageHandler.Data.ImageViewModel
import com.example.newsapp.ImageHandler.Data.Response.Success
import com.example.newsapp.ImageHandler.Data.Response.Loading
import com.example.newsapp.ImageHandler.Data.Response.Failure


@Composable
fun GetImageFromDatabase(
    viewModel: ImageViewModel = hiltViewModel(),
    createImageContent: @Composable (imageUrl: String)-> Unit
) {
    when (val getImageFromDatabaseResponse = viewModel.getImageUrlFromFirestoreResponse){
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let {imageUrl->
            createImageContent(imageUrl)
        }
        is Failure -> print(getImageFromDatabaseResponse.e)
    }
}