package com.example.newsapp.news


//import com.example.newsapp.TopBar.SecondTopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.BottomBar
import com.example.newsapp.TopBar.TopMainBarView
import com.example.newsapp.UserAccount.UserViewModel
import com.example.newsapp.news.models.News
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun NewsScreen(news: News, navController: NavController, userVM: UserViewModel) {
    val scrollState = rememberScrollState()
    val userId = Firebase.auth.currentUser
    var userInfoData2 by remember { mutableStateOf(listOf<String>()) }
    var userPlus by remember { mutableStateOf(false) }
    val items = listOf(
        "username",
        "fname",
        "lname",
        "streetAddress",
        "postalCode",
        "postalDistrict",
        "plusUser"
    ).map { it }

    Firebase.firestore.collection("users")
        .document(userId?.uid.toString())
        .get().addOnSuccessListener {
            val data = mutableListOf<String>()
            items.forEach { item ->
                data.add(it.get(item).toString())
                if (data.contains("true")) {
                    data.removeAt(6)
                    data.add("Tilattu")
                    userPlus = data.contains("Tilattu")
                }
                if (data.contains("false")) {
                    data.removeAt(6)
                    data.add("Tilaamatta")
                    userPlus != data.contains("Tilaamatta")
                }
            }
            userInfoData2 = data
        }

    Column(modifier = Modifier.fillMaxSize()) {

        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState))
                {

                    TopMainBarView(lazyListState = LazyListState(), navController = navController as NavHostController)
                    //SecondTopBar(lazyListState = LazyListState(), navController = navController)
                    ProfileHeader(news, containerHeight = this@BoxWithConstraints.maxHeight)

                    if (userPlus && news.plus == true){
                    ProfileContent(news, containerHeight = this@BoxWithConstraints.maxHeight)
                    }
                    if (!userPlus && (news.plus == true)){
                        PlusContent()
                    }
                    if(userPlus && news.plus == false){
                        ProfileContent(news, containerHeight = this@BoxWithConstraints.maxHeight)
                    }
                    if(!userPlus && news.plus == false){
                        ProfileContent(news, containerHeight = this@BoxWithConstraints.maxHeight)
                    }
                    BottomBar()
                }
            }
        }

    }
}


@Composable
private fun ProfileHeader(
    news: News,
    containerHeight: Dp
) {
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(news.Image)
        .crossfade(true)
        .build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
    )
}

@Composable
fun ProfileContent(news: News, containerHeight: Dp) {
    Column {
        //var newsText = news.newsContent.toString()
        if (news.newsContent.toString().contains("_n")) {
            val newName: String = news.newsContent!!.replace("_n", "\n")
            news.newsContent = newName
        }
        Title(news)
        ProfileProperty(label = news.newsLabel.toString(), value = news.newsContent.toString())
        //ProfileProperty(label = "wow2", value = puppy.age.toString())
        //ProfileProperty(label = "wow3", value = puppy.description)
        Spacer(modifier = Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
fun PlusContent() {
    Column(modifier = Modifier
        .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
        .background(Brush.verticalGradient(listOf(Color(0xFF93A56A), Color(0xFFFDFFFA))))
        .padding(top = 15.dp, start = 15.dp)
        .height(900.dp)) {
        Text(
            text = "Tilaa uutisPlus lukeaksesi tämän uutisen",
            fontSize = 50.sp, color = Color.White
        )
    }
}
@Composable
fun Title(news: News) {
    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp)) {
        Text(
            text = news.Name.toString(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(10.dp))
       Row() {
        Text(
            text = "Julkaistu: ${news.published.toString()}",
            color = Color.LightGray
        )
           var newSize: Dp = 200.dp
           if (news.subTag!!.length > 7){
               newSize = 185.dp
           }
           if (news.subTag!!.isBlank()){
        Text(
            text = news.tag.toString(),
            color = Color.LightGray,
            modifier = Modifier.padding(start = 200.dp)
        )}else{
               Text(
                   text = news.subTag.toString(),
                   color = Color.LightGray,
                   modifier = Modifier.padding(start = newSize)
               )
        }
       }
        Spacer(modifier = Modifier.size(5.dp))
    }
}

@Composable
private fun ProfileProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
    {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            fontSize = 20.sp,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Visible
        )

        //MaterialTheme.typography.body1
    }
}
