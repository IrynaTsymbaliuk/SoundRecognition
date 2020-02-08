package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.deezer.sdk.network.request.DeezerRequestFactory
import com.squareup.picasso.Picasso
import com.tsymbaliuk.soundrecognation.R
import com.tsymbaliuk.soundrecognation.SoundViewModel
import com.tsymbaliuk.soundrecognation.SoundViewModelFactory
import kotlinx.android.synthetic.main.answer_layout.*
import kotlinx.android.synthetic.main.answer_layout.view.*
import kotlinx.android.synthetic.main.answer_layout.view.singer_name
import kotlinx.android.synthetic.main.rv_item.view.*

class AnswerFragment : Fragment() {

    private lateinit var soundViewModel: SoundViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.answer_layout, container, false)

        val viewModelFactory = SoundViewModelFactory()
        soundViewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(SoundViewModel::class.java)

        Picasso.get().load(soundViewModel.selectedSong.value?.album?.bigImageUrl)
            .into(rootView.album_cover)

        rootView.singer_name.text = soundViewModel.selectedSong.value?.artist?.name
        rootView.album_song_name.text = "${soundViewModel.selectedSong.value?.album?.title}: ${soundViewModel.selectedSong.value?.title}"

        if (arguments?.get("answerIndex") != null) {
            val answerIndex: Int = Integer.parseInt(arguments?.get("answerIndex").toString())
            rootView.lyrics.text = soundViewModel.newsLiveData.value!![answerIndex].lyrics
        }

        return rootView
    }
}