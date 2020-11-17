package com.a5studios.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageurl: String? = null

    private fun loadMeme(){
        progressBar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this) /* first make a queue for volley as a request queue*/
        val url = "https://meme-api.herokuapp.com/gimme"  /* make val url to store the url from where we need data*/

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(    /* now we request data from url,,, it can be stringrequest or JSONobjectrequest*/
            Request.Method.GET, url,null,  //Request.GET means to get data , url is url from where we need data, null means we don't request anything to the server
            Response.Listener { response ->  /* Listener means.. what we need to show on screen*/
                currentImageurl=response.getString("url")   // get url attribute from the API of meme
                Glide.with(this).load(currentImageurl).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE

                        return false
                    }

                }).into(memeshareimage) //glide library for fetching the image from the url
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something wen wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    /* we use libraries for geting data from the url  .....we need volley library for fetching data from the url
    * it makes a queue in which object is added so that it can be procesed by volley.
     */

    fun clickshare(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this new meme from Reddit $currentImageurl")
        // chooser
        val chooser= Intent.createChooser(intent,"Share meme using.. ")
        startActivity(chooser)
    }
    fun clicknext(view: View) {
        loadMeme()
    }
}
