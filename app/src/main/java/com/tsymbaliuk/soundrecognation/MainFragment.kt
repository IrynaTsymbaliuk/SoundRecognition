package com.tsymbaliuk.soundrecognation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.tsymbaliuk.soundrecognation.pojo.Result
import kotlinx.android.synthetic.main.search_fragment.view.*

class MainFragment : Fragment(), SoundRecyclerViewAdapter.OnItemClickListener {

    private lateinit var soundViewModel: SoundViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SoundRecyclerViewAdapter
    private var listForAdapter = listOf<Result>()

    lateinit var deezerConnect: DeezerConnect
    lateinit var dialogListener: DialogListener
    lateinit var playerWrapperListener: PlayerWrapperListener
    lateinit var requestListener: RequestListener

    override fun onItemClick(
        adapter: RecyclerView.Adapter<*>,
        view: View?,
        position: Int,
        id: Int
    ) {
        deezerConnect.requestAsync(
            DeezerRequestFactory.requestSearchTracks(soundViewModel.newsLiveData.value!![position].title),
            requestListener
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.search_fragment, container, false)

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

        requestListener = object : JsonRequestListener() {
            override fun onResult(result: Any?, requestID: Any?) {
                val trackPlayer = TrackPlayer(
                    App.instance, deezerConnect,
                    WifiOnlyNetworkStateChecker()
                )
                trackPlayer.addPlayerListener(playerWrapperListener)
                result as PaginatedList<Track>
                trackPlayer.playTrack(result[0].id)
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

        soundViewModel.newsLiveData.observe(this, Observer {
            if (it != null) {
                onDataChange(it)
            }
        })

        rootView.search_mb.setOnClickListener {
            if (rootView.enter_lyrics_tiet.text.toString().isNotEmpty()) {
                soundViewModel.getLatestNews(rootView.enter_lyrics_tiet.text.toString())
            }
        }

        recyclerView = rootView.music_rv_rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        adapter = SoundRecyclerViewAdapter(listForAdapter)
        adapter.onItemClickListener = this
        recyclerView.adapter = adapter

        return rootView
    }

    private fun onDataChange(it: List<Result>) {
        listForAdapter = it
        if (::adapter.isInitialized) {
            adapter.updateData(listForAdapter)
            adapter.notifyDataSetChanged()
        }
    }

}

