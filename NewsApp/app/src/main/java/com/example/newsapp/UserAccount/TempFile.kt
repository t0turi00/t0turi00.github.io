package com.example.newsapp.UserAccount

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


/*
val context = LocalContext.current
val myImage: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.ic_menu_gallery)
val result = remember{ mutableStateOf(myImage) }
val loadImage = rememberLauncherForActivityResult( ActivityResultContracts.GetContent()){
    if(Build.VERSION.SDK_INT<29){
        result.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        if (it != null) {
            storageRef.child("images/$result").putFile(it)
        }
    }else{
        val source =
            it?.let { it1 ->
                ImageDecoder.createSource(context.contentResolver, it1)
            }
        result.value = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }!!
    }
}
Column(
modifier = Modifier.fillMaxWidth(),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center
) {
    Image(
        result.value.asImageBitmap(),
        contentDescription = "image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .clickable { loadImage.launch("image/*") }
    )
}
*/