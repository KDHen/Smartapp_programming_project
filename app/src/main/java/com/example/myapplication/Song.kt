package com.example.myapplication

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val albumCoverUrl: String
)

val dummySongs = List(20) {
    Song(
        id = it,
        title = "Song $it",
        artist = "Artist $it",
        albumCoverUrl = "https://picsum.photos/200/300"
    )
}