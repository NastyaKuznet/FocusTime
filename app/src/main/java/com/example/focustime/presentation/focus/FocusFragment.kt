package com.example.focustime.presentation.focus

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentFocusBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.avatar.AvatarFragment
import com.example.focustime.presentation.newFocus.NewFocusFragment
import javax.inject.Inject

class FocusFragment : Fragment(R.layout.fragment_focus) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentFocusBinding by viewBinding()

    private val viewModel: FocusViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)

        viewModel.getTypesIndicators(offlineMode, userId)
        viewModel.listTypeIndicators.observe(viewLifecycleOwner){ state ->
            with(binding) {
                when (state) {
                    is UIState.Success -> {
                        content.visibility = View.VISIBLE
                        loading.visibility = View.GONE

                        val periods = state.value
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            periods
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        indicatorSpinner.adapter = adapter
                    }
                    is UIState.Fail -> {
                        content.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is UIState.Loading -> {
                        content.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        }

        with(binding){

            viewModel.hour.observe(viewLifecycleOwner){
                if(it != null)
                    focusTimeInputHour.setText(it.toString())
            }

            viewModel.minute.observe(viewLifecycleOwner){
                if(it != null)
                    focusTimeInputMinute.setText(it.toString())
            }

            viewModel.second.observe(viewLifecycleOwner){
                if(it != null)
                    focusTimeInputSecond.setText(it.toString())
            }

            addTimeHour.setOnClickListener {
                viewModel.incrementHour()
            }

            removeTimeHour.setOnClickListener {
                viewModel.decrementHour()
            }

            addTimeMinute.setOnClickListener {
                viewModel.incrementMinute()
            }

            removeTimeMinute.setOnClickListener {
                viewModel.decrementMinute()
            }

            addTimeSecond.setOnClickListener {
                viewModel.incrementSecond()
            }

            removeTimeSecond.setOnClickListener {
                viewModel.decrementSecond()
            }

            createIndicatorButton.setOnClickListener {
                if(indicatorSpinner.selectedItem == null){
                    Toast.makeText(requireContext(),
                        requireContext().getString(R.string.you_need_choose_indicator),
                        Toast.LENGTH_SHORT).show()

                } else if(translateToSecond() == 0) {
                    Toast.makeText(requireContext(),
                        requireContext().getString(R.string.you_need_write_time),
                        Toast.LENGTH_SHORT).show()
                } else {
                    goScreenCreateNewTypeIndicator()
                }
            }
        }
    }

    private fun goScreenCreateNewTypeIndicator(){
        val bundle = Bundle()
        bundle.putInt("time", translateToSecond())
        bundle.putInt("idType", viewModel.getIdTypeIndByName(
            binding.indicatorSpinner.selectedItem.toString()
        ))
        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.apply()

        val fr = NewFocusFragment()
        fr.arguments = bundle
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "NEW_FOCUS_FRAGMENT_TAG")
            .addToBackStack("NEW_FOCUS_FRAGMENT_TAG")
            .commit()
    }

    private fun translateToSecond(): Int{
        return (viewModel.hour.value?.toInt() ?: 0) * 3600 +
                (viewModel.minute.value?.toInt() ?: 0) * 60 +
                (viewModel.second.value?.toInt() ?: 0)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}