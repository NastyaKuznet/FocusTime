package com.example.focustime.presentation.offlineSetting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentOfflineSettingBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
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
        }

        viewModel.stateDelete.observe(viewLifecycleOwner){ state ->
            when(state){
                is UIState.Success -> {
                    Toast.makeText(requireContext(), "История очищена", Toast.LENGTH_SHORT).show()
                }
                is UIState.Fail -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}