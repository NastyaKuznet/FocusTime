package com.example.focustime.presentation.accountUser

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAccountUserBinding
import com.example.focustime.databinding.FragmentAccountUserEditBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserEditFragment : Fragment(R.layout.fragment_account_user_edit) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAccountUserEditBinding by viewBinding()

    private val viewModel: AccountUserEditFragmentViewModel by viewModels() { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

                lifecycleScope.launch {
                    viewModel.uiState.observe(viewLifecycleOwner) {
                        when (it) {
                            is UIState.Success -> {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            is UIState.Fail -> {
                                Toast.makeText(
                                    requireContext(),
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else ->{}
                        }
                    }
                }
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}