package com.example.focustime.presentation.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentRootBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.presentation.navigation.FocusFragment
import com.example.focustime.presentation.navigation.FriendsFragment
import com.example.focustime.presentation.navigation.IndicatorsFragment
import com.example.focustime.presentation.navigation.TypesIndicatorsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        makeCurrentFragment(IndicatorsFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_focus -> makeCurrentFragment(FocusFragment())
                R.id.nav_friends -> makeCurrentFragment(FriendsFragment())
                R.id.nav_indicator -> makeCurrentFragment(IndicatorsFragment())
                R.id.nav_create_indicator -> makeCurrentFragment(TypesIndicatorsFragment())
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