package com.example.focustime.presentation.offlineSetting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.activityViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentOfflineSettingBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.avatar.AvatarFragment
import javax.inject.Inject

class OfflineSettingFragment: Fragment(R.layout.fragment_offline_setting) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentOfflineSettingBinding by viewBinding()

    private val viewModel: OfflineSettingViewModel by viewModels() { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            deleteHistory.setOnClickListener {
                viewModel.deleteIndicators()
            }

            exit.setOnClickListener {
                viewModel.exitOfflineMode(requireContext())
                findNavController().navigate(R.id.registrationFragment)
            }

            userAvatar.setOnClickListener {
                makeCurrentFragment(AvatarFragment())
            }
        }

        viewModel.stateDelete.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    Toast.makeText(requireContext(), "История очищена", Toast.LENGTH_SHORT).show()
                }
                is UIState.Fail -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        setUpAvatar()
    }

    private fun setUpAvatar(){
        val avatarId = arguments?.getInt("avatarId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("avatarId", -1)
        }

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

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}