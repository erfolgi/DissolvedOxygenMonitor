package com.erfolgi.dissolvedoxygenmonitor

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.erfolgi.dissolvedoxygenmonitor.model.read.Feed
import com.erfolgi.dissolvedoxygenmonitor.presenter.MainPresenter
import com.erfolgi.dissolvedoxygenmonitor.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import android.support.v4.os.HandlerCompat.postDelayed
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity(),MainView {
    var f1:String="0"
    var f2:String="0"
    var f3:String="0"
    lateinit var mHandler: Handler
    private lateinit var mainPresenter: MainPresenter
    lateinit var graph:GraphView

    private var menuItem: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        menuItem = menu
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.history_button -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Dissolved Oxygen Monitor"
        this.mHandler = Handler()
        this.mHandler.post(refresher)
        //this.mHandler.post(refresher)
        graph = findViewById<GraphView>(R.id.graph) as GraphView


        loading.visibility=VISIBLE
        mainPresenter=MainPresenter(this)



        fuzzy.setOnClickListener {
            if(fuzzy.isChecked){
                loading.visibility=VISIBLE
                mainPresenter.cancelRequest()
                mHandler.removeCallbacks(refresher)
                mHandler.removeCallbacksAndMessages(null)
                mainPresenter.setFeed(f1,f2,"2.00","0.00")
                button.isEnabled = false
                button.background= getDrawable(R.drawable.roundfuzzy)
            }else{
                loading.visibility=VISIBLE
                mainPresenter.cancelRequest()
                mHandler.removeCallbacks(refresher)
                mHandler.removeCallbacksAndMessages(null)
                mainPresenter.setFeed(f1,f2,"0.00","0.00")
                button.isEnabled = true
                button.background= getDrawable(R.drawable.round)
            }
        }

        button.setOnClickListener {
            loading.visibility=VISIBLE
            mainPresenter.cancelRequest()
            mHandler.removeCallbacks(refresher)
            mHandler.removeCallbacksAndMessages(null)
            if (f3=="1.00"){
                //f3="0"
                button.background= getDrawable(R.drawable.round)
                mainPresenter.setFeed(f1,f2,"0.00","0.00")
            }else if (f3=="0.00"){
                //f3="1"
                mainPresenter.setFeed(f1,f2,"1.00","0.00")
                button.background= getDrawable(R.drawable.roundon)
            }
            //mainPresenter.setFeed(f1,f2,f3)
        }

    }

    override fun onResume() {
        super.onResume()
        mainPresenter.getGraph()
    }

    @SuppressLint("SetTextI18n")
    override fun showFeed(items: List<Feed>) {
        f1= items[9].field1!!
        f2= items[9].field2!!
        f3= items[9].field3!!

        when (f3) {
            "1.00" -> {
                fuzzy.isChecked=false
                button.background= getDrawable(R.drawable.roundon)
                button.isEnabled = true
                Log.e("f3","one")
            }
            "0.00" -> {
                fuzzy.isChecked=false
                button.background= getDrawable(R.drawable.round)
                button.isEnabled = true
                Log.e("f3","two")
            }
            "2.00" -> {
                Log.e("f3","three")
                fuzzy.isChecked=true
                button.background= getDrawable(R.drawable.roundfuzzy)
                button.isEnabled = false
            }
        }
        when (f2) {
            "1.00" -> {
                log.text = "Aerator Status: ON"
            }
            "0.00" -> {
                log.text = "Aerator Status: OFF"
            }

        }
        dostatus.text=items[9].field1!!
        var datado= items[9].field1!!.toFloat()
        if (datado<3.0){
            dostatus.text=items[9].field1!!+" (LOW)"
        }else if (datado>6.0){
            dostatus.text=items[9].field1!!+" (HIGH)"
        }else if (datado in 6.0..3.0){
            dostatus.text=items[9].field1!!+" (MID)"
        }

        loading.visibility=INVISIBLE

    }

    override fun showToast(text: String) {
        loading.visibility=INVISIBLE
        Toast.makeText(this,text,LENGTH_SHORT).show()
        //loading.visibility=INVISIBLE
        this@MainActivity.mHandler.postDelayed(refresher, 10000)
    }

    override fun showGraph(items: List<Feed>) {
        graph.removeAllSeries()

        val series = LineGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.0, items[4].field1!!.toDouble()),
                DataPoint(1.0, items[5].field1!!.toDouble()),
                DataPoint(2.0, items[6].field1!!.toDouble()),
                DataPoint(3.0, items[7].field1!!.toDouble()),
                DataPoint(4.0, items[8].field1!!.toDouble()),
                DataPoint(5.0, items[9].field1!!.toDouble())
            )
        )
        graph.addSeries(series)
        this@MainActivity.mHandler.postDelayed(refresher, 10000)
        Toast.makeText(this@MainActivity,"refreshed", LENGTH_SHORT).show()
    }

    private val refresher = object : Runnable {
        override fun run() {
            mainPresenter.getGraph()
            mainPresenter.getFeed()
           // Toast.makeText(this@MainActivity,"Delay", LENGTH_SHORT).show()
        }
    }//runnable
}
