package com.example.newsapp.ImageHandler.Data

import android.net.Uri

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageUrlToFirestoreResponse = Response<Boolean>
typealias GetImageUrlFromFirestoreResponse = Response<String>

interface ImageRepository {

    suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse
    suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse
    suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse

}