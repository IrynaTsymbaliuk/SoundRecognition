package com.tsymbaliuk.soundrecognation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deezer.sdk.model.Track
import com.tsymbaliuk.soundrecognation.pojo.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SoundViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val soundRepository : SoundRepo = SoundRepo(ApiFactory.auddApi)
    val newsLiveData = MutableLiveData<MutableList<Result>>()

    var selectedSong = MutableLiveData<Track>()

    fun getLatestNews(lyrics: String) {
        scope.launch {
            val latestNews = soundRepository.getSoundByLyrics(lyrics)
            newsLiveData.postValue(latestNews)

        }
    }

    fun cancelRequests() = coroutineContext.cancel()
}