package com.erfolgi.dissolvedoxygenmonitor.model.read

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Channel (

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null,
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null,
    @SerializedName("field1")
    @Expose
    var field1: String? = null,
    @SerializedName("field2")
    @Expose
    var field2: String? = null,
    @SerializedName("field3")
    @Expose
    var field3: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    @SerializedName("last_entry_id")
    @Expose
    var lastEntryId: Int? = null

)