package com.example.newsapp.ImageHandler.Data

import android.net.Uri
import androidx.compose.runtime.getValue
import com.example.newsapp.ImageHandler.Data.Response.Success
import com.example.newsapp.ImageHandler.Data.Response.Loading
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ImageHandler.Data.ImageRepository
import com.example.newsapp.ImageHandler.Data.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: ImageRepository
): ViewModel() {
    var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Success(null))
        private set
    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Success(null))
        private set
    var getImageUrlFromFirestoreResponse by mutableStateOf<Response<String>>(Success(null))
        private set

    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        addImageToStorageResponse = Loading
        addImageToStorageResponse = repo.addImageToFirebaseStorage(imageUri)
    }

    fun addImageToDatabase(downloadUrl : Uri) = viewModelScope.launch {
        addImageToDatabaseResponse = Loading
        addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)
    }

    fun getImageFromDatabase() = viewModelScope.launch {
        getImageUrlFromFirestoreResponse = Loading
        getImageUrlFromFirestoreResponse = repo.getImageUrlFromFirestore()
    }
}
