package com.example.newsapp.ImageHandler.Data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.ImageHandler.Data.Response.Success
import com.example.newsapp.ImageHandler.Data.Response.Loading
import com.example.newsapp.ImageHandler.Data.Response.Failure
import com.example.newsapp.ProgressBar

@Composable
fun AddImageToDatabase(
    viewModel: ImageViewModel = hiltViewModel(),
    showSnackBar: (isImageAddedToDatabase : Boolean)-> Unit
) {
    when (val addImageToDatabaseResponse = viewModel.addImageToDatabaseResponse){
        is Loading -> ProgressBar()
        is Success -> addImageToDatabaseResponse.data?.let {isImageAddedToDatabase->
            LaunchedEffect(isImageAddedToDatabase){
                showSnackBar(isImageAddedToDatabase)
            }
        }
        is Failure -> print(addImageToDatabaseResponse.e)
    }
}
