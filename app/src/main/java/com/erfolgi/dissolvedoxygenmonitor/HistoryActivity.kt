package com.erfolgi.dissolvedoxygenmonitor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.erfolgi.dissolvedoxygenmonitor.adapter.HistoryAdapter
import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed
import com.erfolgi.dissolvedoxygenmonitor.presenter.HistoryPresenter
import com.erfolgi.dissolvedoxygenmonitor.presenter.MainPresenter
import com.erfolgi.dissolvedoxygenmonitor.view.HistoryView
import kotlinx.android.synthetic.main.activity_history.*
import org.jetbrains.anko.ctx

class HistoryActivity : AppCompatActivity(),HistoryView {
    private lateinit var historyPresenter: HistoryPresenter
    private lateinit var historyAdapter : HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        historyPresenter= HistoryPresenter(this)
        historyPresenter.getFeed()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
       // historyPresenter.cancelRequest()
    }

    override fun showFeed(items: List<Feed>) {
        rv_history.layoutManager=  LinearLayoutManager(ctx)
        historyAdapter=HistoryAdapter(this,items)
        rv_history.adapter=historyAdapter

    }

    override fun showToast(text: String) {

    }

}
