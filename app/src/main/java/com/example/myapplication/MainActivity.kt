package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SongApp(viewModel, this)
        }
    }
}

@Composable
fun SongApp(viewModel: SongViewModel, activity: ComponentActivity) {
    val songList = viewModel.songList.observeAsState(emptyList())
    val list = listOf("운동", "행복한 기분", "에너지 충전", "출퇴근 길", "휴식", "집중", "슬픔", "로맨스", "파티", "잠잘때")

    val navController = rememberNavController()
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            NavHost(
                navController = navController,
                startDestination = MainScreen.List.route,
            ) {
                composable(route = MainScreen.List.route) {
                    MainView(navController, viewModel, activity, songList.value, list)
                }
                composable(route = MainScreen.Detail.route) { backStackEntry ->
                    val songId = backStackEntry.arguments?.getString("songId")?.toInt()

                    val song = songList.value.find {it.id == songId}
                    SongDetail(navController, song)
                }
                composable(route = MainScreen.SeeMore.route) {
                    SeeMoreMainView(navController, viewModel)
                }
            }
        }
    }
}