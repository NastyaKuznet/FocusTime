package com.example.focustime.presentation.sendRequest

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentSendRequestBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendRequestFragment : Fragment(R.layout.fragment_send_request) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentSendRequestBinding by viewBinding()

    private val viewModel: SendRequestFragmentViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            val userId = arguments?.getInt("userId") ?: run {
                val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedPreferences.getInt("userId", 0)
            }

            buttonAdd.setOnClickListener {
                viewModel.sendFriendRequest(
                    userId,
                    focusTimeInput.text.toString())

                lifecycleScope.launch {
                    viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                        when (uiState) {
                            is UIState.Success -> {
                                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                            }
                            is UIState.Fail -> {
                                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                            }
                            else ->{}
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)

        binding.userAvatar.setOnClickListener{
            makeCurrentFragment(AccountUserFragment())
        }
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