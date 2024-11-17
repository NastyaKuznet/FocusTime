package com.example.focustime.presentation.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAuthorizationBinding
import com.example.focustime.databinding.FragmentRootBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.presentation.authorization.AuthorizationUserFragmentViewModel
import javax.inject.Inject

class RootFragment: Fragment(R.layout.fragment_root) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRootBinding by viewBinding()

    //private val viewModel: AuthorizationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding){
            val userId = arguments?.getInt("userId")
            content.text = "Welcome!\nТвой id: $userId"
        }

        super.onViewCreated(view, savedInstanceState)
    }

}