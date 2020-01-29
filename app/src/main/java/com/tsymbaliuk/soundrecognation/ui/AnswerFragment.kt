package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.deezer.sdk.network.request.DeezerRequestFactory
import com.tsymbaliuk.soundrecognation.R
import com.tsymbaliuk.soundrecognation.SoundViewModel
import com.tsymbaliuk.soundrecognation.SoundViewModelFactory
import kotlinx.android.synthetic.main.answer_layout.view.*

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

        /*  rootView.album_cover.setImageResource()
             rootView.singer_cover.setImageResource()
            rootView.play.setOnClickListener { }
           rootView.singer_name.setText()
                rootView.album_song_name.setText()
                rootView.lyrics.setText() =*/

        return rootView
    }
}