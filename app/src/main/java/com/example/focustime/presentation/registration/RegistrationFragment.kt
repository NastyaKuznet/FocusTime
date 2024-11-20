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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.focustime.presentation.models.ResultUIState

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRegistrationBinding by viewBinding()

    private val viewModel: RegistrationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            buttonRegister.setOnClickListener {
                viewModel.registration(
                    editTextUsername.text.toString(),
                    editTextPasswordRegister.text.toString())

                lifecycleScope.launch {
                    viewModel.uiState.collect { uiState ->
                        when (uiState.resultUIState) {
                            ResultUIState.Success -> {
                                Toast.makeText(requireContext(), "good", Toast.LENGTH_LONG).show()
                                val bundle = Bundle()
                                bundle.putInt("userId", uiState.user.id)
                                findNavController().navigate(
                                    R.id.rootFragment,
                                    bundle)
                            }
                            ResultUIState.Error -> {
                                Toast.makeText(requireContext(), "Соблюдайте условия!", Toast.LENGTH_LONG).show()
                            }
                            ResultUIState.AlreadyExists -> {
                                Toast.makeText(requireContext(), "Имя занято", Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
            textViewLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
            }

        }


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}