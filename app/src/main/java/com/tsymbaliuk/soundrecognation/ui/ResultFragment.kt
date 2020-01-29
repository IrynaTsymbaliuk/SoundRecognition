package com.tsymbaliuk.soundrecognation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.deezer.sdk.model.PaginatedList
import com.deezer.sdk.model.Permissions
import com.deezer.sdk.model.PlayableEntity
import com.deezer.sdk.model.Track
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.connect.event.DialogListener
import com.deezer.sdk.network.request.DeezerRequestFactory
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.deezer.sdk.network.request.event.RequestListener
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.event.PlayerWrapperListener
import com.deezer.sdk.player.networkcheck.WifiOnlyNetworkStateChecker
import com.tsymbaliuk.soundrecognation.*
import com.tsymbaliuk.soundrecognation.pojo.Result
import kotlinx.android.synthetic.main.result_fragment.view.*

class ResultFragment: Fragment() {

    private lateinit var soundViewModel: SoundViewModel

    lateinit var deezerConnect: DeezerConnect
    lateinit var dialogListener: DialogListener
    lateinit var playerWrapperListener: PlayerWrapperListener
    lateinit var requestListener: RequestListener
    lateinit var trackPlayer: TrackPlayer

    var attempt = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.result_fragment, container, false)

        val permissions = arrayOf<String>(
            Permissions.BASIC_ACCESS,
            Permissions.MANAGE_LIBRARY,
            Permissions.LISTENING_HISTORY
        )

        dialogListener = object : DialogListener {
            override fun onComplete(values: Bundle) {}
            override fun onCancel() {}
            override fun onException(e: Exception) {}
        }

        deezerConnect = DeezerConnect(context, Constants.deezerAppId)
        deezerConnect.authorize(activity, permissions, dialogListener)

        trackPlayer = TrackPlayer(
            App.instance, deezerConnect,
            WifiOnlyNetworkStateChecker()
        )

        requestListener = object : JsonRequestListener() {
            override fun onResult(result: Any?, requestID: Any?) {
                trackPlayer.addPlayerListener(playerWrapperListener)
                result as PaginatedList<Track>
                if (attempt < result.size+1) {
                    trackPlayer.playTrack(result[attempt].id)
                }
                soundViewModel.selectedSong.value = result[attempt]
            }

            override fun onUnparsedResult(p0: String?, p1: Any?) {}
            override fun onException(p0: java.lang.Exception?, p1: Any?) {}

        }

        playerWrapperListener = object : PlayerWrapperListener {
            override fun onAllTracksEnded() {}
            override fun onPlayTrack(p0: PlayableEntity?) {}
            override fun onRequestException(p0: java.lang.Exception?, p1: Any?) {}
            override fun onTrackEnded(p0: PlayableEntity?) {}

        }

        val viewModelFactory = SoundViewModelFactory()
        soundViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SoundViewModel::class.java)

        soundViewModel.newsLiveData.observe(activity!!, Observer {
            if (it != null) {
                onDataChange()
            }
        })

        root.like.setOnClickListener {
            val action =
            ResultFragmentDirections.actionResultFragmentToAnswerFragment(attempt)
            findNavController().navigate(action)
        }

        root.dislike.setOnClickListener {
            attempt++
            onDataChange()
        }

        return root
    }

    private fun onDataChange() {
        if (soundViewModel.newsLiveData.value != null) {
            deezerConnect.requestAsync(
                DeezerRequestFactory.requestSearchTracks(soundViewModel.newsLiveData.value!![attempt].title), requestListener
            )
        }
    }


}