package com.tsymbaliuk.soundrecognation

import com.deezer.sdk.model.Permissions

object Constants {

    const val auddApiToken = BuildConfig.AUDD_API_TOKEN
    const val deezerAppId = BuildConfig.DEEZER_APP_ID

    val permissions = arrayOf<String>(
        Permissions.BASIC_ACCESS,
        Permissions.MANAGE_LIBRARY,
        Permissions.LISTENING_HISTORY
    )

}