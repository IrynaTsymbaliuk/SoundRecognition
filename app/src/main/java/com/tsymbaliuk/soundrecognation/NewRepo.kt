package com.tsymbaliuk.soundrecognation

import com.tsymbaliuk.soundrecognation.pojo.Result

class SoundRepo(private val apiInterface: AuddApi) : BaseRepository() {

    suspend fun getSoundByLyrics(lyrics: String):  MutableList<Result>?{
        return safeApiCall(
            call = {apiInterface.getSoundByLyrics(lyrics)},
            error = "Error fetching news"
        )?.result?.toMutableList()
    }
}