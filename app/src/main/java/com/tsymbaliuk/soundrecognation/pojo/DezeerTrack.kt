package com.tsymbaliuk.soundrecognation.pojo

import com.google.gson.annotations.SerializedName

data class DezeerTrack(

    @SerializedName("data") val data : List<Data>,
    @SerializedName("total") val total : Int,
    @SerializedName("next") val next : String
)