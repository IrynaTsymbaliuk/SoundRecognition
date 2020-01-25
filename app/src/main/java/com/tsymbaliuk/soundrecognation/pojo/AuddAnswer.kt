package com.tsymbaliuk.soundrecognation.pojo

import com.google.gson.annotations.SerializedName

data class AuddAnswer(
    @SerializedName("status")
    val status: String,
    @SerializedName("result")
    val result: List<Result>
)

data class Result(
    @SerializedName("song_id")
    val song_id: Int,
    @SerializedName("artist_id")
    val artist_id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_with_featured")
    val title_with_featured: String,
    @SerializedName("full_title")
    val full_title: String,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("lyrics")
    val lyrics: String
)

