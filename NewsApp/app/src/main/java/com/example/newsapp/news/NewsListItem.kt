package com.example.newsapp.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.news.models.News

@Composable
fun NewsListItem(news: News, navigateToProfile: (News) -> Unit, navController: NavHostController) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        //backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {

        Row(
            Modifier.clickable { navigateToProfile(news) }
        ) {
            NewsImage(news = news)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = news.Name.toString(), fontWeight = FontWeight.Bold)
                //Text(text = "Lue lisää")
                Spacer(modifier = Modifier.size(5.dp))
                Row(){

                    Text(
                    text = "Julkaistu: ${news.published.toString()}",
                    color = Color.LightGray,
                    fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.padding(start = 80.dp))
                    Text(
                        text = news.tag.toString(),
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsImage(news: News) {
    Column() {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(news.Image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(125.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }
}