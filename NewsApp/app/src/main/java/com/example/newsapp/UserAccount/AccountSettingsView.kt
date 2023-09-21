package com.example.newsapp.UserAccount



import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.*
import com.example.newsapp.ImageHandler.Data.AddImageToStorage
import com.example.newsapp.ImageHandler.Data.Constants.ALL_IMAGES
import com.example.newsapp.ImageHandler.Data.ImageViewModel
import com.example.newsapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*



@Composable
fun AccountView(userVM: UserViewModel, navController: NavHostController, viewModel: ImageViewModel = hiltViewModel()) {


    val userId = Firebase.auth.currentUser
    var username by remember { mutableStateOf("") }
    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var streetAddress by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var postalDistrict by remember { mutableStateOf("") }
    var plusUser by remember { mutableStateOf(false) }
    val fireStore = Firebase.firestore
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val currentDateAndTime = sdf.format(Date())
    val time = remember { mutableStateOf(currentDateAndTime) }
    val user = User(
        username,
        fName,
        lName,
        streetAddress,
        postalCode,
        postalDistrict,
        plusUser,
        time.value
    )
    val context = LocalContext.current
    var userInfoData by remember { mutableStateOf(listOf<String>()) }

    val items = listOf(
        "username",
        "fname",
        "lname",
        "streetAddress",
        "postalCode",
        "postalDistrict",
        "plusUser"
    ).map { it }

    val itemsInfo = listOf(
        "Käyttäjänimi",
        "Etunimi",
        "Sukunimi",
        "Katuosoite",
        "Postinumero",
        "Postitoimipaikka",
        "UutisPlus"
    ).map { it }.toMutableList()

    fun mToast(context: Context) {
        Toast.makeText(context, "Käyttäjätiedot päivitetty.", Toast.LENGTH_LONG).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF93A56A)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {


        Firebase.firestore.collection("users")
            .document(userId?.uid.toString())
            .get().addOnSuccessListener {
                val data = mutableListOf<String>()
                items.forEach { item ->
                    data.add(it.get(item).toString())
                    //Check if this needs to be changed for the actual newsPlus subscription
                    // You can use the toBoolean() to get the Boolean value represented by the specified string.
                    // The function returns true if the specified string is equal to “true” (case ignored) and false otherwise.
                    if (data.contains("true")) {
                        data.removeAt(6)
                        data.add("Tilattu")
                    }
                    if (data.contains("false")) {
                        data.removeAt(6)
                        data.add("Tilaamatta")
                    }
                }
                userInfoData = data
            }

        if (!userInfoData.contains("null")) {

            viewModel.getImageFromDatabase()

            Text(
                text = "Käyttäjä: ${Firebase.auth.currentUser?.email}",
                fontSize = 20.sp,
                color = Color.White,
            )
            GetImageFromDatabase(
                createImageContent = { imageUrl ->
                    ImageContent(imageUrl)
                }
            )

            Row(
                modifier = Modifier
                    .border(
                        width = 10.dp,
                        color = Color(0xFF93A56A),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = Color(0xFFC8E090))
                    .border(
                        width = 10.dp,
                        color = Color(0xFF93A56A),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(35.dp)
            ) {
                Column() {
                    itemsInfo.forEach {
                        Text(
                            text = "$it:",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF616D46),
                            textAlign = TextAlign.Start, modifier = Modifier.width(160.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(160.dp)
                                .height(1.dp)
                                .background(Color(0xFF93A56A))
                        )
                    }

                    Spacer(modifier = Modifier.size(25.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "close",
                        Modifier
                            .size(35.dp)
                            .clickable { navController.navigate("home") },
                        tint = Color.White
                    )
                }
                Column() {
                    userInfoData.forEach {
                        Text(
                            text = it,
                            fontSize = 15.sp,
                            color = Color(0xFF616D46),
                            textAlign = TextAlign.Start, modifier = Modifier.width(160.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(200.dp)
                                .height(1.dp)
                                .background(Color(0xFF93A56A))
                        )
                    }
                    Spacer(modifier = Modifier.size(25.dp))

                }

            }
        }
        if(userInfoData.contains("null")){

            Column(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .background(Color(0xFFC8E090))
                    .padding(bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var imageUri by remember { mutableStateOf<Uri?>(null) }
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    imageUri = uri
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    imageUri?.let { viewModel.addImageToStorage(imageUri!!) }
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                        error = painterResource(R.drawable.ic_account),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 64.dp)
                            .clip(CircleShape)
                            .width(128.dp)
                            .height(128.dp)
                    )
                    Button(onClick = { launcher.launch(ALL_IMAGES) },
                        colors = ButtonDefaults.buttonColors(Color(0xFF99AD6C))) {
                        Text(text = "Profiilikuva",  color = Color(0xFF4C5537))
                    }
                }

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Käyttäjänimi") }, modifier = Modifier.width(300.dp)
                )
                Row() {
                    OutlinedTextField(
                        value = fName,
                        onValueChange = { fName = it },
                        label = { Text(text = "Etunimi") }, modifier = Modifier.width(145.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = lName,
                        onValueChange = { lName = it },
                        label = { Text(text = "Sukunimi") }, modifier = Modifier.width(145.dp)
                    )
                }
                Row() {
                    OutlinedTextField(
                        value = streetAddress,
                        onValueChange = { streetAddress = it },
                        label = { Text(text = "katuosoite") }, modifier = Modifier.width(160.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = { Text(text = "Postinumero") }, modifier = Modifier.width(130.dp)
                    )
                }

                OutlinedTextField(
                    value = postalDistrict,
                    onValueChange = { postalDistrict = it },
                    label = { Text(text = "Postitoimipaikka") }, modifier = Modifier.width(200.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Card(
                    backgroundColor = Color(0xFFC8E090),
                    border = BorderStroke(width = 2.dp, color = Color(0xFF93A56A))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(
                            text = "Liity UutisPlus jäseneksi alkaen 0e/kk",
                            fontSize = 10.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center, modifier = Modifier.width(110.dp)
                        )
                        Switch(
                            checked = plusUser,
                            onCheckedChange = { plusUser = it },
                            modifier = Modifier.width(80.dp),
                            colors = SwitchDefaults.colors(Color(0xFF93A56A))
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))
                Column() {
                    OutlinedButton(
                        onClick = {
                            fireStore
                                .collection("users")
                                .document(userId?.uid.toString())
                                .set(user)
                            FirebaseDatabase.getInstance().getReference("users").database
                            Thread.sleep(500)
                            username = ""
                            fName = ""
                            lName = ""
                            streetAddress = ""
                            postalCode = ""
                            postalDistrict = ""
                            plusUser = false
                            mToast(context)
                            navController.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF99AD6C)),
                        modifier = Modifier.width(170.dp)
                    ) {
                        Text(text = "Päivitä käyttäjätiedot", color = Color(0xFF4C5537))
                    }

                    AddImageToStorage(
                        addImageToDatabase = { downloadUrl ->
                            viewModel.addImageToDatabase(downloadUrl)
                        }
                    )
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                Modifier
                    .size(35.dp)
                    .clickable { navController.navigate("home") },
                tint = Color.White,
            )
        }
    }

}


