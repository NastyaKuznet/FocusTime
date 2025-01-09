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
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragmentViewModel


class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAuthorizationBinding by viewBinding()

    private val viewModel: AuthorizationUserFragmentViewModel by viewModels() {viewModelFactory}

    private val viewModelAccountInfo: AccountUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding){
            buttonLogin.setOnClickListener {
                viewModel.authorization(
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString())

                lifecycleScope.launch {
                    viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                        when (uiState) {
                            is UIState.Success -> {
                                val bundle = Bundle()
                                bundle.putInt("userId", uiState.value.id)
                                saveUserIdToPreferences(requireContext(), uiState.value.id)
                                saveUserAvatarIdToPreferences(requireContext(), uiState.value.id, bundle)
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
                findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveUserAvatarIdToPreferences(context: Context, userId: Int, bundle: Bundle){
        viewModelAccountInfo.getUserInfo(userId)

        lifecycleScope.launch{
            viewModelAccountInfo.uiState.observe(viewLifecycleOwner) {
                when(it){
                    is UIState.Success -> {
                        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("avatarId", it.value.id_avatar)
                        editor.apply()

                        findNavController().navigate(
                            R.id.rootFragment,
                            bundle)
                    }
                    is UIState.Fail -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Loading -> {
                    }
                }
            }
        }
    }

    fun saveUserIdToPreferences(context: Context, userId: Int) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}