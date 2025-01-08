package com.example.focustime.presentation.root

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentRootBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.presentation.focus.FocusFragment
import com.example.focustime.presentation.friends.FriendsFragment
import com.example.focustime.presentation.history.HistoryFragment
import com.example.focustime.presentation.indicators.IndicatorsFragment
import com.example.focustime.presentation.offlineSetting.OfflineSettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class RootFragment: Fragment(R.layout.fragment_root) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRootBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        makeCurrentFragment(HistoryFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_focus -> makeCurrentFragment(FocusFragment())
                R.id.nav_friends -> makeCurrentFragment(setFragment())
                R.id.nav_indicator -> makeCurrentFragment(HistoryFragment())
                R.id.nav_create_indicator -> makeCurrentFragment(IndicatorsFragment())
            }
            true
        }
        checkOfflineMode()
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    private fun checkOfflineMode(){
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        if(!offlineMode) return
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val el = bottomNav.menu.findItem(R.id.nav_friends)
        el.title = "Настройки"
    }

    private fun setFragment(): Fragment{
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        return if(offlineMode)
            OfflineSettingFragment()
        else
            FriendsFragment()
    }
}