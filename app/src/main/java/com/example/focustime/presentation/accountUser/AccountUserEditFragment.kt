package com.example.focustime.presentation.accountUser

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAccountUserEditBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import javax.inject.Inject

class AccountUserEditFragment : Fragment(R.layout.fragment_account_user_edit) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAccountUserEditBinding by viewBinding()

    private val viewModel: AccountUserEditFragmentViewModel by viewModels() { viewModelFactory }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences =
                requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }


        with(binding) {
            saveButton.setOnClickListener {
                viewModel.updateUserInfo(
                    userId,
                    editUserStatus.text.toString()
                )
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is UIState.Fail -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{}
            }
        }

        binding.themeSwitchButton.setOnClickListener{
            toggleTheme()

        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun toggleTheme() {
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        sharedPreferences.edit().putBoolean("isDarkTheme", !isDarkTheme).apply()

        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}