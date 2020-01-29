package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tsymbaliuk.soundrecognation.R
import kotlinx.android.synthetic.main.rules_fragment.view.*

class RulesFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.rules_fragment, container, false)

        root.find_song_mb.setOnClickListener {
        findNavController().navigate(R.id.action_rulesFragment_to_searchFragment)
        }

        return root
    }
}