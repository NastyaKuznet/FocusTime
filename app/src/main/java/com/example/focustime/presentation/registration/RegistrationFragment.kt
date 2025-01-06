package com.example.focustime.presentation.registration

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentRegistrationBinding
import com.example.focustime.di.ViewModelFactory
import javax.inject.Inject
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.focustime.di.appComponent
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.focustime.presentation.UIState

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRegistrationBinding by viewBinding()

    private val viewModel: RegistrationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.checkOfflineMode(requireContext())
        viewModel.checkLocaleUserId(requireContext())

        with(binding){

            viewModel.stateCheckOfflineMode.observe(viewLifecycleOwner){
                when(it){
                    is UIState.Success -> {
                        findNavController().navigate(
                            R.id.rootFragment)
                    }
                    is UIState.Fail -> {
                        content.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                    }
                    is UIState.Loading -> {
                        content.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
            }

            viewModel.stateCheckUserId.observe(viewLifecycleOwner){
                when(it){
                    is UIState.Success -> {
                        findNavController().navigate(
                            R.id.rootFragment)
                    }
                    is UIState.Fail -> {
                        content.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                    }
                    is UIState.Loading -> {
                        content.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
            }

            buttonRegister.setOnClickListener {
                viewModel.registration(
                    editTextUsername.text.toString(),
                    editTextPasswordRegister.text.toString())

                lifecycleScope.launch {
                    viewModel.stateRegistration.observe(viewLifecycleOwner) { uiState ->
                        when (uiState) {
                            is UIState.Success -> {

                                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                                saveUserIdToPreferences(uiState.value.id)
                                findNavController().navigate(
                                    R.id.rootFragment)
                            }
                            is UIState.Fail -> {
                                content.visibility = View.VISIBLE
                                loading.visibility = View.GONE
                                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                            }
                            is UIState.Loading -> {
                                content.visibility = View.GONE
                                loading.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }

            textViewLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
            }

            offlineModeButton.setOnClickListener {
                saveOfflineMode()
                findNavController().navigate(R.id.rootFragment)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveUserIdToPreferences(userId: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()
    }

    private fun saveOfflineMode() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("offlineMode", true)
        editor.apply()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}