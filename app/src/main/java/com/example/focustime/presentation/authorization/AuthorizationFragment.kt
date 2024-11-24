package com.example.focustime.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.focustime.R
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.databinding.FragmentAuthorizationBinding
import com.example.focustime.di.appComponent
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.focustime.presentation.models.ResultUIState


class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAuthorizationBinding by viewBinding()

    private val viewModel: AuthorizationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            buttonLogin.setOnClickListener {
                viewModel.authorization(
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString())

                lifecycleScope.launch {
                    viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                        when (uiState.resultUIState) {
                            ResultUIState.Success -> {
                                Toast.makeText(requireContext(), "nice", Toast.LENGTH_LONG).show()
                                val bundle = Bundle()
                                bundle.putInt("userId", uiState.user.id)
                                findNavController().navigate(
                                    R.id.rootFragment,
                                    bundle)
                            }
                            ResultUIState.Error -> {
                                Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
            textViewLogin.setOnClickListener {
                findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}