package com.erfolgi.dissolvedoxygenmonitor.model.write

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Write (
    @SerializedName("channel_id")
    @Expose
    var channelId: Int? = null,
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
    var field3: Any? = null,
    @SerializedName("field4")
    @Expose
    var field4: Any? = null,
    @SerializedName("field5")
    @Expose
    var field5: Any? = null,
    @SerializedName("field6")
    @Expose
    var field6: Any? = null,
    @SerializedName("field7")
    @Expose
    var field7: Any? = null,
    @SerializedName("field8")
    @Expose
    var field8: Any? = null,
    @SerializedName("latitude")
    @Expose
    var latitude: Any? = null,
    @SerializedName("longitude")
    @Expose
    var longitude: Any? = null,
    @SerializedName("elevation")
    @Expose
    var elevation: Any? = null,
    @SerializedName("status")
    @Expose
    var status: Any? = null

)



