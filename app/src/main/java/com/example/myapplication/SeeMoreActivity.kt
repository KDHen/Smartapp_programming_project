package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme

class SeeMoreActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeeMoreMainView(viewModel)
        }
    }
}

@Composable
fun SeeMoreMainView(viewModel: SongViewModel) {
    val songList = viewModel.songList.observeAsState(emptyList())

    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                SeeMoreTopBar()
                SeemoreSongList(songList.value)
            }
        }
    }
}

@Composable
fun SeeMoreTopBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // 뒤로가기 버튼
        IconButton(
            onClick = {
                (context as? Activity)?.finish()
                val intent = Intent()
                intent.putExtra("key", "value")
                (context as? Activity)?.setResult(Activity.RESULT_OK, intent)},
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.icon_left_arrow),
                tint = Color.White,
                contentDescription = "Back"
            )
        }

        // 다시듣기 텍스트
        Text(
            text = "다시 듣기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = { /* Handle search button click */ },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search50), // 검색 아이콘의 리소스 ID
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }

//            // 사용자 아이콘
//            Image(
//                painter = painterResource(id = R.drawable.ic_user),
//                contentDescription = "User Icon",
//                modifier = Modifier
//                    .size(50.dp)
//                    .padding(8.dp)
//                    .clip(CircleShape)
//            )

    }
}

@Composable
fun SeemoreSongList(list: List<Song>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(list.take(5).chunked(2)) { songs ->
            Row{
                for(song in songs)
                    MusicItem(song)
            }
        }
    }
}

@Composable
fun MusicItem(song: Song) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        // 각 음악 항목의 UI를 작성
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://picsum.photos/200/300?random",
                contentDescription = "노래 앨범 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                song.title?.let { Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                song.artist?.let { Text(text = it, fontSize = 14.sp) }
            }
        }
    }
}