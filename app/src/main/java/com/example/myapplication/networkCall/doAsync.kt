package com.example.myapplication.networkCall

import android.os.AsyncTask

class doAsync(private val listener: NetworkCallback, val handler: () -> String): AsyncTask<Void, Void, String>() {
    init {
        execute()
    }

    override fun doInBackground(vararg p0: Void?): String? {
        return handler()
    }

    override fun onPostExecute(result: String?) {
        listener.onNetworkDone(result ?: "error!")
    }
}