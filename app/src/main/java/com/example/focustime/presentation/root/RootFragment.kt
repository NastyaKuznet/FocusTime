package com.example.focustime.presentation.root

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
import javax.inject.Inject

class RootFragment: Fragment(R.layout.fragment_root) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRootBinding by viewBinding()

    //private val viewModel: AuthorizationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        /*with(binding){
            val userId = arguments?.getInt("userId")
            content.text = "Welcome!\nТвой id: $userId"
        }*/

        makeCurrentFragment(HistoryFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_focus -> makeCurrentFragment(FocusFragment())
                R.id.nav_friends -> makeCurrentFragment(FriendsFragment())
                R.id.nav_indicator -> makeCurrentFragment(HistoryFragment())
                R.id.nav_create_indicator -> makeCurrentFragment(IndicatorsFragment())
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

}