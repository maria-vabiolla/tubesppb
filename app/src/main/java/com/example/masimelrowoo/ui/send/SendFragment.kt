package com.example.masimelrowoo.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.masimelrowoo.databinding.FragmentSendBinding
import com.example.masimelrowoo.ui.send.SendViewModel

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel
    private var _binding: FragmentSendBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProvider(this).get(SendViewModel::class.java)

        _binding = FragmentSendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textSend
        // sendViewModel.text.observe(viewLifecycleOwner, Observer {
        //     textView.text = it
        //  })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}