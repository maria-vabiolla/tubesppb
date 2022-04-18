package com.example.masimelrowoo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.masimelrowoo.databinding.ActivityNavigationBinding
import com.example.masimelrowoo.entity.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class NavigationActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val user = User(
            acct?.displayName,
            acct?.id,
            acct?.email,
            acct?.photoUrl.toString()
        )
        val bundle = bundleOf("user" to user)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home, bundle)
                }
                R.id.navigation_send -> {
                    navController.navigate(R.id.navigation_send)
                }
                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard)
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                }
                R.id.navigation_friend -> {
                    navController.navigate(R.id.navigation_friend)
                }
            }
            true
        }
    }
    public fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}