package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(viewModel, this)
        }
    }
}

@Composable
fun MainView(viewModel: SongViewModel, activity: ComponentActivity) {
    val songList = viewModel.songList.observeAsState(emptyList())
    val list = listOf("운동", "행복한 기분", "에너지 충전", "출퇴근 길", "휴식", "집중", "슬픔", "로맨스", "파티", "잠잘때")

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item{TopAppBar()}
                item{RecommendedList(list)}
                item{fastsonglistBar()}
                item{fastSongList(songList.value)}
                item{ListenAgainBar(activity)}
                item{SongList(songList.value)}
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { /* 아이콘 버튼 클릭 시 수행할 동작 */ },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.youtubemusic100),
                contentDescription = "App logo",
                modifier = Modifier
                    .size(85.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(55.dp)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search50),
                contentDescription = "Search icon",
                tint = Color.White
            )
        }
        // Glide를 통해 동적으로 사용자 아이콘 로드
//        val imageUrl = /* 사용자 이미지 URL */
//            Glide.with(this)
//                .load(imageUrl)
//                .apply(RequestOptions().circleCrop())
//                .into(userIcon)
    }
}

@Composable
fun RecommendedList(list: List<String>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) { item ->
            Recommendedbutton(item)
        }
    }
}

@Composable
fun Recommendedbutton(item: String) {
    Button(
        onClick = { /* 아이콘 버튼 클릭 시 수행할 동작 */ },
        modifier = Modifier
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray.copy(alpha = 0.9f), contentColor = Color.White), // 버튼의 배경색과 글씨색 설정
        shape = RoundedCornerShape(6.dp) // 버튼의 모양 설정
    ) {
        Text(text = item, fontSize = 12.sp)
    }
}

@Composable
fun fastsonglistBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = "빠른 선곡", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Button(
            onClick = {},
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
            contentPadding = PaddingValues(1.dp),
            border = BorderStroke(1.dp, Color.White)
        ){
            Text(text = "모두 재생", fontSize = 12.sp)
        }
    }
}

@Composable
fun fastSongList(list: List<Song>) {
    LazyRow {
        items(list.take(20).chunked(4)) { songs ->
            Column {
                for (song in songs) {
                    fastSong(song)
                }
            }
        }
    }
}

@Composable
fun fastSong(song: Song) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(8.dp)
            .background(Color.DarkGray)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min)
        ){
            AsyncImage(
                model = "https://picsum.photos/200/300?random",
                contentDescription = "노래 앨범 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(percent = 2))
            )
            Spacer(modifier = Modifier.width(2.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                song.title?.let { Text(text = it, color = Color.White) }
                song.artist?.let { Text(text = it, color = Color.White) }
            }
        }
    }
}

@Composable
fun ListenAgainBar(activity: ComponentActivity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = "다시 듣기", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Button(
            onClick = {
                val intent = Intent(activity, SeeMoreActivity::class.java)
                activity.startActivity(intent)
                      },
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
            contentPadding = PaddingValues(1.dp),
            border = BorderStroke(1.dp, Color.White)
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
                    Song(song)
                }
            }
        }
    }
}

@Composable
fun Song(song: Song) {
    Column (
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
    ){
        AsyncImage(
            model = "https://picsum.photos/200/300?random",
            contentDescription = "노래 앨범 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(percent = 2))
        )
        Spacer(modifier = Modifier.width(2.dp))
        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            song.title?.let { Text(text = it, color = Color.White) }
        }
    }
}