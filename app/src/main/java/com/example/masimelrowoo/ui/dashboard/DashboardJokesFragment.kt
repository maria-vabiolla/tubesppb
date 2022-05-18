package com.example.masimelrowoo.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.masimelrowoo.BuildConfig
import com.example.masimelrowoo.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class DashboardJokesFragment : Fragment() {

    // lateinit var progressBar: ProgressBar
    lateinit var listJokes: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getListJokes()
        return inflater.inflate(R.layout.fragment_dashboard_jokes, container, false)
    }

    private fun getListJokes() {
        // progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        val url = BuildConfig.JOKE_API_URL + "search"

        client.addHeader("Accept", "application/json")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers : Array<Header>, responseBody: ByteArray) {
                // progressBar.visibility = View.INVISIBLE

                val listJoke = ArrayList<String>()
                val json = String(responseBody)

                try {
                    val obj = JSONObject(json)
                    val jsonArray = obj.getJSONArray("results")

                    for(i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val joke = jsonObject.getString("joke")
                        listJoke.add("\n$joke\n")
                    }
                    // TODO: change the display the listJoke from toast to DashboardJokesFragment
                    Toast.makeText(this@DashboardJokesFragment.requireActivity(), listJoke.toString(), Toast.LENGTH_LONG).show()
                    val adapter = ArrayAdapter(this@DashboardJokesFragment.requireActivity(), android.R.layout.activity_list_item, listJoke)
                    // listJokes.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@DashboardJokesFragment.requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers : Array<Header>, responseBody: ByteArray, error: Throwable) {
                // progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }

                Toast.makeText(this@DashboardJokesFragment.requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}