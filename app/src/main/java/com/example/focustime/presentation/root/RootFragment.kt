package com.example.focustime.presentation.root

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentRootBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.avatar.AvatarFragment
import com.example.focustime.presentation.focus.FocusFragment
import com.example.focustime.presentation.friends.FriendsFragment
import com.example.focustime.presentation.history.HistoryFragment
import com.example.focustime.presentation.indicators.IndicatorsFragment
import com.example.focustime.presentation.offlineSetting.OfflineSettingFragment
import com.example.focustime.presentation.sendRequest.SendRequestFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class RootFragment: Fragment(R.layout.fragment_root) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRootBinding by viewBinding()

    private var offlineMode: Boolean = false

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        makeCurrentFragment(HistoryFragment())

        binding.bottomNavigation.setOnItemSelectedListener  { button ->
            when (button.itemId) {
                R.id.nav_focus -> makeCurrentFragment(FocusFragment())
                R.id.nav_friends -> makeCurrentFragment(setFragmentFriendOrOfflineSetting())
                R.id.nav_indicator -> makeCurrentFragment(HistoryFragment())
                R.id.nav_create_indicator -> makeCurrentFragment(IndicatorsFragment())
            }
            true
        }

        checkOfflineMode()
        changeFriendIfOfflineMode()
        setSettingAvatar()
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
        setTitleByCurrentFragment(fragment)
    }

    private fun setTitleByCurrentFragment(fragment: Fragment){
        val title = when(fragment){
            is HistoryFragment -> {
                "Индикаторы за период"
            }
            is FocusFragment -> {
                "Режим фокусировки"
            }
            is OfflineSettingFragment -> {
                "Оффлайн настройка"
            }
            is IndicatorsFragment -> {
                "Индикаторы"
            }
            is FriendsFragment -> {
                "Друзья"
            }
            is AccountUserFragment -> {
                "Экран профиля"
            }
            else -> {
                "Корень"
            }
        }
        with(binding){
            screenTitle.text = title
            if(fragment is AccountUserFragment){
                userAvatar.visibility = View.GONE
            } else {
                userAvatar.visibility = View.VISIBLE
            }
        }
    }

    private fun checkOfflineMode(){
        offlineMode = sharedPreferences.getBoolean("offlineMode", false)
    }

    private fun changeFriendIfOfflineMode(){
        if(!offlineMode) return
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val el = bottomNav.menu.findItem(R.id.nav_friends)
        el.title = "Настройки"
    }

    private fun setFragmentFriendOrOfflineSetting(): Fragment{
        return if(offlineMode)
            OfflineSettingFragment()
        else
            FriendsFragment()
    }

    private fun setSettingAvatar(){
        setAvatarImage(sharedPreferences)

        sharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
                if (key == "avatarId") {
                    setAvatarImage(sharedPref)
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(
            sharedPreferenceChangeListener)

        with(binding){

            userAvatar.setOnClickListener{
                if(offlineMode){
                    makeCurrentFragment(AvatarFragment())
                } else {
                    makeCurrentFragment(AccountUserFragment())
                }
            }
        }
    }

    private fun setAvatarImage(sPreferences: SharedPreferences) {
        val avatarId = sPreferences.getInt("avatarId", 0)
        val avatarResId = when (avatarId) {
            0 -> R.drawable.avatar1
            1 -> R.drawable.avatar2
            2 -> R.drawable.avatar3
            3 -> R.drawable.avatar4
            4 -> R.drawable.avatar5
            else -> R.drawable.avatar1
        }
        binding.userAvatar.setImageResource(avatarResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }
}