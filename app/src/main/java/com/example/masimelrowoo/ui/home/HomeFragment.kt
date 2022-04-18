package com.example.masimelrowoo.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masimelrowoo.NavigationActivity
import com.example.masimelrowoo.R
import com.example.masimelrowoo.databinding.FragmentHomeBinding
import com.example.masimelrowoo.databinding.FragmentNotificationsBinding
import com.example.masimelrowoo.entity.User
import com.example.masimelrowoo.ui.notifications.NotificationsViewModel
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user : User
    private lateinit var logout : Button

    companion object{
        private const val STATE_USER = "state_user"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        logout = root.findViewById(R.id.logout)
        logout.setOnClickListener(this)

        if(savedInstanceState != null) {
            val user_state = savedInstanceState.getParcelable<User>(HomeFragment.STATE_USER)
            if(user_state != null){
                user = user_state
            }

        }
        return root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.logout -> {
                    val builder = AlertDialog.Builder(activity)
                    //set title for alert dialog
                    builder.setTitle("Sign Out")
                    //set message for alert dialog
                    builder.setMessage("Are you sure want to sign out?")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)

                    //performing positive action
                    builder.setPositiveButton("Yes"){dialogInterface, which ->
                        val activity: NavigationActivity? = activity as NavigationActivity?
                        activity?.signOut()
                    }
                    //performing negative action
                    builder.setNegativeButton("No"){dialogInterface, which ->
                        Toast.makeText(activity,"sign out canceled", Toast.LENGTH_LONG).show()
                    }
                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(HomeFragment.STATE_USER, user)
    }
}