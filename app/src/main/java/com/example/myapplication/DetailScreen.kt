package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun SongDetail(navController: NavController, song: Song?) {
    Column {
        TopBarUI(navController)
        ImageUI(song)
        TextUI(song)
    }

}

@Composable
fun TopBarUI(navController: NavController) {
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
}

@Composable
fun ImageUI(song: Song?) {
    AsyncImage(
        model = "https://picsum.photos/200/300?random",
//        model = song?.imageUrl, // imageUrl은 Song 객체의 이미지 URL을 나타냅니다.
        contentDescription = "노래 앨범 사진",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(35.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(percent = 2))
    )
}

@Composable
fun TextUI(song: Song?) {
    Column(
        modifier = Modifier
            .padding(start = 35.dp)
    ) {
        song?.title?.let { Text(text = it, color = Color.White) }
        song?.artist?.let { Text(text = it, color = Color.White) }
    }

}