package com.example.masimelrowoo.ui.friend

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masimelrowoo.FriendListAddUpdateActivity
import com.example.masimelrowoo.R
import com.example.masimelrowoo.adapter.FriendListAdapter
import com.example.masimelrowoo.databinding.FragmentFriendBinding
import com.example.masimelrowoo.db.FriendListHelper
import com.example.masimelrowoo.entity.FriendList
import com.example.masimelrowoo.helper.MappingHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FriendFragment : Fragment() {
    private lateinit var friendViewModel: FriendViewModel
    private var _binding: FragmentFriendBinding? = null
    private lateinit var adapter: FriendListAdapter
    private lateinit var friendListHelper: FriendListHelper
    private lateinit var rv_notes : RecyclerView
    private lateinit var fab_add : FloatingActionButton
    private lateinit var progressbar : ProgressBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendViewModel =
            ViewModelProvider(this).get(FriendViewModel::class.java)

        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        friendListHelper = FriendListHelper.getInstance(requireActivity().applicationContext)
        friendListHelper.open()

        progressbar = root.findViewById(R.id.progressbar)
        rv_notes = root.findViewById(R.id.rv_notes) as RecyclerView
        rv_notes.layoutManager = LinearLayoutManager(activity)
        rv_notes.setHasFixedSize(true)
        adapter = FriendListAdapter(requireActivity())
        rv_notes.adapter = adapter

        fab_add = root.findViewById(R.id.fab_add)
        fab_add.setOnClickListener {
            val intent = Intent(this@FriendFragment.requireActivity(), FriendListAddUpdateActivity::class.java)
            startActivityForResult(intent, FriendListAddUpdateActivity.REQUEST_ADD)
        }

        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FriendList>(BluetoothAdapter.EXTRA_STATE)
            if (list != null) {
                adapter.listFriends = list
            }
        }
        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                FriendListAddUpdateActivity.REQUEST_ADD -> if (resultCode == FriendListAddUpdateActivity.RESULT_ADD) {
                    val friendList = data.getParcelableExtra<FriendList>(FriendListAddUpdateActivity.EXTRA_FRIEND)
                    if (friendList != null) {
                        adapter.addItem(friendList)
                    }
                    rv_notes.smoothScrollToPosition(adapter.itemCount - 1)

                    showSnackbarMessage("Berhasil menambahkan teman!")
                }
                FriendListAddUpdateActivity.REQUEST_UPDATE ->
                    when (resultCode) {
                        FriendListAddUpdateActivity.RESULT_UPDATE -> {
                            val friendList = data.getParcelableExtra<FriendList>(FriendListAddUpdateActivity.EXTRA_FRIEND)
                            val position = data.getIntExtra(FriendListAddUpdateActivity.EXTRA_POSITION, 0)

                            adapter.updateItem(position, friendList!!)
                            rv_notes.smoothScrollToPosition(position)

                            showSnackbarMessage("Username teman berhasil diupdate")
                        }
                        FriendListAddUpdateActivity.RESULT_DELETE -> {
                            val position = data.getIntExtra(FriendListAddUpdateActivity.EXTRA_POSITION, 0)

                            adapter.removeItem(position)

                            showSnackbarMessage("Anda berhasil melakukan unfriend")
                        }
                    }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_notes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        friendListHelper.close()
    }
    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = friendListHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listFriends = notes
            } else {
                adapter.listFriends = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BluetoothAdapter.EXTRA_STATE, adapter.listFriends)
    }
}