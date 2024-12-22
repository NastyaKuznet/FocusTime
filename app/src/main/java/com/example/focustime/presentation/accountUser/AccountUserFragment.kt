package com.example.focustime.presentation.accountUser

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAccountUserBinding
import com.example.focustime.databinding.FragmentRegistrationBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.registration.RegistrationUserFragmentViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserFragment : Fragment(R.layout.fragment_account_user) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAccountUserBinding by viewBinding()

    private val viewModel: AccountUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        viewModel.getUserInfo(
            userId)

        with(binding){
            lifecycleScope.launch {
                viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                    when (uiState.success) {
                        ResultUIState.Success -> {
                            binding.userNickname.text = viewModel.uiState.value?.userInfo!!.nickname
                            binding.userStatus.text = viewModel.uiState.value?.userInfo!!.status
                            binding.friendsCount.text = "Friends: " + viewModel.uiState.value?.userInfo!!.friends_count
                            binding.focusTime.text = "Focus Time: " + viewModel.uiState.value?.userInfo!!.total_focus_time
                            Toast.makeText(requireContext(), "good", Toast.LENGTH_LONG).show()
                        }
                        ResultUIState.Error -> {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener{
            makeCurrentFragment(AccountUserEditFragment())
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
