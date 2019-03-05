package com.erfolgi.dissolvedoxygenmonitor.model.read

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Feed (
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("entry_id")
    @Expose
    var entryId: Int? = null,
    @SerializedName("field1")
    @Expose
    var field1: String? = null,
    @SerializedName("field2")
    @Expose
    var field2: String? = null,
    @SerializedName("field3")
    @Expose
    var field3: String? = null,
    @SerializedName("field4")
    @Expose
    var field4: String? = null

)



