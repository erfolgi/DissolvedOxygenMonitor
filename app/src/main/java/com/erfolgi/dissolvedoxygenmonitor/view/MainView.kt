package com.erfolgi.dissolvedoxygenmonitor.view

import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed

interface MainView {
    fun showFeed (items : List<Feed>)
    fun showToast (text : String)
    fun showGraph (items : List<Feed>)
}