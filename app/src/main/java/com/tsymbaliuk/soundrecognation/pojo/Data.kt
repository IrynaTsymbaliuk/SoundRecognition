package com.tsymbaliuk.soundrecognation.pojo

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("id") val id : Int,
	@SerializedName("readable") val readable : Boolean,
	@SerializedName("title") val title : String,
	@SerializedName("title_short") val title_short : String,
	@SerializedName("title_version") val title_version : String,
	@SerializedName("link") val link : String,
	@SerializedName("duration") val duration : Int,
	@SerializedName("rank") val rank : Int,
	@SerializedName("explicit_lyrics") val explicit_lyrics : Boolean,
	@SerializedName("explicit_content_lyrics") val explicit_content_lyrics : Int,
	@SerializedName("explicit_content_cover") val explicit_content_cover : Int,
	@SerializedName("preview") val preview : String,
	@SerializedName("artist") val artist : Artist,
	@SerializedName("album") val album : Album,
	@SerializedName("type") val type : String
)