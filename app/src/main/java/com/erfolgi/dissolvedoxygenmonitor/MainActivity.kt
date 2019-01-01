package com.erfolgi.dissolvedoxygenmonitor

import android.annotation.SuppressLint
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
import org.jetbrains.anko.ctx


class MainActivity : AppCompatActivity(),MainView {
    var f1:String="0"
    var f2:String="0"
    var f3:String="0"
    lateinit var mHandler: Handler
    private lateinit var mainPresenter: MainPresenter
    lateinit var graph:GraphView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.mHandler = Handler()

        //this.mHandler.post(refresher)
        graph = findViewById<GraphView>(R.id.graph) as GraphView


        loading.visibility=VISIBLE
        mainPresenter=MainPresenter(this)
        mainPresenter.getFeed()

        co2button.setOnClickListener {
            loading.visibility=VISIBLE
            mainPresenter.cancelRequest()
            mHandler.removeCallbacks(refresher)
            mHandler.removeCallbacksAndMessages(null)
            if (f3=="1"){
                //f3="0"
                mainPresenter.setFeed(f1,f2,"0")
            }else{
                //f3="1"
                mainPresenter.setFeed(f1,f2,"1")
            }
            //mainPresenter.setFeed(f1,f2,f3)
        }
        o2button.setOnClickListener {
            loading.visibility=VISIBLE
            mainPresenter.cancelRequest()
            mHandler.removeCallbacks(refresher)
            mHandler.removeCallbacksAndMessages(null)
            if (f2=="1"){
             //   f2="0"
                mainPresenter.setFeed(f1,"0",f3)
            }else{
                mainPresenter.setFeed(f1,"1",f3)
               // f2="1"
            }
           // mainPresenter.setFeed(f1,f2,f3)
        }
    }
    @SuppressLint("SetTextI18n")
    override fun showFeed(items: List<Feed>) {
        f1= items[4].field1!!
        f2= items[4].field2!!
        f3= items[4].field3!!
        if (f2=="1"){
            o2status.text="Aerator : ON"
        }else{
            o2status.text="Aerator : OFF"
        }
        if (f3=="1"){
            co2status.text="CO2 : ON"
        }else{
            co2status.text="CO2 : OFF"
        }
        dostatus.text=f1
        loading.visibility=INVISIBLE
        this.mHandler.post(refresher)
    }

    override fun showToast(text: String) {
        loading.visibility=VISIBLE
        Toast.makeText(this,text,LENGTH_SHORT).show()

        mainPresenter.getFeed()
    }

    override fun showGraph(items: List<Feed>) {
        graph.removeAllSeries()
        val series = LineGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.0, items[0].field1!!.toDouble()),
                DataPoint(1.0, items[1].field1!!.toDouble()),
                DataPoint(2.0, items[2].field1!!.toDouble()),
                DataPoint(3.0, items[3].field1!!.toDouble()),
                DataPoint(4.0, items[4].field1!!.toDouble())
            )
        )
        graph.addSeries(series)
        this@MainActivity.mHandler.postDelayed(refresher, 5000)
        Toast.makeText(this@MainActivity,"refreshed", LENGTH_SHORT).show()
    }

    private val refresher = object : Runnable {
        override fun run() {
            mainPresenter.getGraph()
            Toast.makeText(this@MainActivity,"Delay", LENGTH_SHORT).show()
        }
    }//runnable
}
