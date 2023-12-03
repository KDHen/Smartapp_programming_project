package com.example.myapplication

import retrofit2.http.GET
interface SongApi {
    @GET("song")
    suspend fun getSongs(): List<Song>
}