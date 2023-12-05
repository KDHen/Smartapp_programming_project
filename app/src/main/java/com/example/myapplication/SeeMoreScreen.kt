package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun SeeMoreMainView(navController: NavController, viewModel: SongViewModel) {
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
                SeeMoreTopBar(navController)
                SeemoreSongList(navController, songList.value)
            }
        }
    }
}

@Composable
fun SeeMoreTopBar(navController: NavController,modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                  navController.popBackStack()
            },
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
                painter = painterResource(id = R.drawable.search50),
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SeemoreSongList(navController: NavController, list: List<Song>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list) { item ->
            MusicItem(navController, item)
        }
    }
}

@Composable
fun MusicItem(navController: NavController, song: Song) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("detail/${song.id}")
            }
    ) {
        AsyncImage(
            model = "https://picsum.photos/200/300?random",
//            model = song?.imageUrl,
            contentDescription = "노래 앨범 사진",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(percent = 2))
        )

        Spacer(modifier = Modifier.width(4.dp))

        Column {
            song.title?.let { Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White) }
            song.artist?.let { Text(text = it, fontSize = 14.sp, color = Color.White) }
        }
    }
}