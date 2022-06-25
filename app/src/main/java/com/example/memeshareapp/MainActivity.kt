package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest as JsonObjectRequest1

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var memeImage: ImageView

    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
        memeImage = findViewById(R.id.memeImage)
    }

    private fun loadMeme() {
        // Instantiate the RequestQueue.


        val url = "https://meme-api.herokuapp.com/gimme"
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest1(Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl  = response.getString("url")
                Glide.with(this).load(currentImageUrl ).into(memeImage)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this,"something went wrong", Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest )
    }


    fun shareMeme(view: View) {
       val intent =Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!!! Checkout this cool meme I got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent," Share this meme using...")
       startActivity(chooser)


    }

    fun nextMeme(view: View) {
        loadMeme()
    }
}
