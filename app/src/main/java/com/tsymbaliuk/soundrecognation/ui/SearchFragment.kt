package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tsymbaliuk.soundrecognation.R
import com.tsymbaliuk.soundrecognation.SoundViewModel
import com.tsymbaliuk.soundrecognation.SoundViewModelFactory
import kotlinx.android.synthetic.main.search.view.*
import kotlinx.android.synthetic.main.search_fragment.view.*

class SearchFragment: Fragment() {

    private lateinit var soundViewModel: SoundViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_fragment, container, false)

        val viewModelFactory = SoundViewModelFactory()
        soundViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SoundViewModel::class.java)

        root.find_song_mb.setOnClickListener {
            if (root.find_song_mb.text.toString().isNotEmpty()) {
                soundViewModel.getLatestNews(root.search_tiet.text.toString())
                findNavController().navigate(R.id.action_searchFragment_to_resultFragment)
            }
        }

        return root
    }
}