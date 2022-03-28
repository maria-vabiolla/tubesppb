package com.example.masimelrowoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin: Button =
            findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val moveIntent = Intent(this@MainActivity, NavigationActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}