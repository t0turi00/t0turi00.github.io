package com.example.newsapp.ImageHandler.Data

import android.net.Uri
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.ImageHandler.Data.Response.Success
import com.example.newsapp.ImageHandler.Data.Response.Loading
import com.example.newsapp.ImageHandler.Data.Response.Failure
import com.example.newsapp.ProgressBar

@Composable
fun AddImageToStorage(
    viewModel: ImageViewModel = hiltViewModel(),
    addImageToDatabase : (downloadUrl: Uri)->Unit
) {
    when (val addImageToStorageResponse = viewModel.addImageToStorageResponse) {
        is Loading -> CircularProgressIndicator()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl)
            }
        }
        is Failure -> print(addImageToStorageResponse.e)
    }
}