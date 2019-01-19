package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.myapplication.networkCall.NetworkCallback
import com.example.myapplication.networkCall.doAsync

import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity(), NetworkCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        doCall("http://www.google.com")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doCall(url: String) {
        Handler().postDelayed({
            doAsync(this) {
                val serviceUrl = URL(url)
                val conn : HttpURLConnection = serviceUrl.openConnection() as HttpURLConnection
                conn.apply {
                    requestMethod = "GET"
                    doOutput = false
                    connectTimeout = 5000
                    readTimeout = 5000
                    connect()
                }

                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                var content = ""

                reader.readLines().forEach {line ->
                    content += "$line \n"
                }
                return@doAsync content
            }
        }, 10000)
    }

    override fun onNetworkDone(result: String) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }
}