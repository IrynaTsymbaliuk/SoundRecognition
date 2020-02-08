package com.tsymbaliuk.soundrecognation

import android.app.Activity
import android.os.Bundle
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.connect.event.DialogListener
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.networkcheck.WifiOnlyNetworkStateChecker

object ConnectManager {

    private lateinit var deezerConnect: DeezerConnect
    private lateinit var trackPlayer: TrackPlayer

    val dialogListener = object : DialogListener {
        override fun onComplete(values: Bundle) {}
        override fun onCancel() {}
        override fun onException(e: Exception) {}
    }

    fun getConnect(activity: Activity): DeezerConnect {
        if (!this::deezerConnect.isInitialized) {
            deezerConnect = DeezerConnect(activity, Constants.deezerAppId)
            deezerConnect.authorize(activity, Constants.permissions, dialogListener)
        }
        return deezerConnect
    }

    fun getPlayer(): TrackPlayer {
        if (!this::trackPlayer.isInitialized) {
            trackPlayer = TrackPlayer(
                App.instance, deezerConnect,
                WifiOnlyNetworkStateChecker()
            )
        }
        return trackPlayer
    }

}