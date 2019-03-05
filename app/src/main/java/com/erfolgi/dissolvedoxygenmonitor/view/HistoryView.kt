package com.erfolgi.dissolvedoxygenmonitor.view

import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed

interface HistoryView {
    fun showFeed (items : List<Feed>)
    fun showToast (text : String)

}