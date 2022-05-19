package com.example.masimelrowoo.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masimelrowoo.R
import com.example.masimelrowoo.databinding.ActivityMainBinding
import com.example.masimelrowoo.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding
    private val list = ArrayList<Message>()
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNotificationsBinding.inflate(layoutInflater)

        binding.rvMessages.setHasFixedSize(true)

        list.addAll(getListMessages())
        showRecyclerList()
    }

    fun getListMessages(): ArrayList<Message>{
        val dataTime = resources.getStringArray(R.array.data_time)
        val dataMessage = resources.getStringArray(R.array.message)

        val listMessage = ArrayList<Message>()
        for (position in dataTime.indices) {
            val message = Message(
                dataTime[position],
                dataMessage[position],
            )
            listMessage.add(message)
        }
        return listMessage
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecyclerList() {
        binding.rvMessages.layoutManager = LinearLayoutManager(this@NotificationsFragment.requireActivity())
        val listMessageAdapter = ListMessageAdapter(list)
        binding.rvMessages.adapter = listMessageAdapter
    }
}