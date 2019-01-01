package com.erfolgi.dissolvedoxygenmonitor.model.read

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ReadObject (
    @SerializedName("channel")
    @Expose
    var channel: Channel? = null,
    @SerializedName("feeds")
    @Expose
    var feeds: List<Feed>? = null

)



