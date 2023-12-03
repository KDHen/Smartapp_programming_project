package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(viewModel)
        }
    }
}

@Composable
fun MainView(viewModel: SongViewModel) {
    val songList = viewModel.songList.observeAsState(emptyList())

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBar()
                ListenAgainBar()
                SongList(songList.value)
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.youtubemusic100),
            contentDescription = "App logo",
            modifier = Modifier
                .size(85.dp)
                .padding(16.dp)
        )

        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.search50),
            contentDescription = "Search icon",
            modifier = Modifier
                .size(55.dp)
                .padding(16.dp)
        )
        // Glide를 통해 동적으로 사용자 아이콘 로드
//        val imageUrl = /* 사용자 이미지 URL */
//            Glide.with(this)
//                .load(imageUrl)
//                .apply(RequestOptions().circleCrop())
//                .into(userIcon)
    }
}

@Composable
fun ListenAgainBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
//        Image(
//
//        )
        Text(text = "다시 듣기", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Button(
            onClick = {},
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
            contentPadding = PaddingValues(1.dp),
            border = BorderStroke(1.dp, Color.White),
//            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 2.dp),
//            shape = CircleShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp)
//                    RoundedCornerShape(8.dp)
        ){
            Text(text = "더보기", fontSize = 12.sp)
        }
    }
}

@Composable
fun SongList(list: List<Song>) {
    LazyRow {
        items(list.take(20).chunked(2)) { songs ->
            Column {
                for (song in songs) {
                    SongRow(song)
                }
            }
        }
    }
}

@Composable
fun SongRow(song: Song) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column (
            modifier = Modifier
                .width(100.dp)
                .padding(8.dp)
        ){
            AsyncImage(
                model = "https://picsum.photos/200/300?random",
                contentDescription = "노래 앨범 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
//                .clip(CircleShape)
                    .clip(RoundedCornerShape(percent = 10))
            )
//            Spacer(modifier = Modifier.width(10.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
////                .background((Color(0, 210, 210))),
//                verticalArrangement = Arrangement.SpaceAround
////                .padding(16.dp)
//            ) {
//                TextTitle(song.title)
//            }
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}
