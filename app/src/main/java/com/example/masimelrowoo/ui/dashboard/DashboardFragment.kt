package com.example.masimelrowoo.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.masimelrowoo.R
import com.example.masimelrowoo.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DashboardFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.dashboard_text_1,
            R.string.dashboard_text_2,
            R.string.dashboard_text_3
        )
    }

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.dashboardViewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.dashboardTabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(DashboardFragment.TAB_TITLES[position])
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}