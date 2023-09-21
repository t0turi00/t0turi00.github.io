package com.example.newsapp.ImageHandler.Data

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.example.newsapp.ImageHandler.Data.Constants.IMAGES
import com.example.newsapp.ImageHandler.Data.Constants.IMAGE_NAME
import com.example.newsapp.ImageHandler.Data.Constants.UID
import com.example.newsapp.ImageHandler.Data.Constants.URL
import com.example.newsapp.ImageHandler.Data.Constants.CREATED_AT
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): ImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val downloadUrl = storage.reference.child(IMAGES).child(IMAGE_NAME)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Response.Success(downloadUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse {
        return try {
            db.collection(IMAGES).document(UID).set(mapOf(
                URL to download,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Response.Success(true)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse {
        return try {
            val imageUrl = db.collection(IMAGES).document(UID).get().await().getString(URL)
            Response.Success(imageUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }
}

