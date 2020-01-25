package com.tsymbaliuk.soundrecognation

import com.tsymbaliuk.soundrecognation.pojo.AuddAnswer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuddApi {

    @GET("findLyrics")
    suspend fun getSoundByLyrics(
        @Query("q") lyrics: String
    ): Response<AuddAnswer>

}