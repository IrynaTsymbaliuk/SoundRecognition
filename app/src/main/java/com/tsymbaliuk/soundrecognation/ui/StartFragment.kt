package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tsymbaliuk.soundrecognation.R
import kotlinx.android.synthetic.main.start_fragment.view.*

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.start_fragment, container, false)

        root.find_song_mb.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_searchFragment)
        }

        root.rules_tv.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_rulesFragment)
        }

        return root
    }
}