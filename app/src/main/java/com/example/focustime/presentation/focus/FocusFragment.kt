package com.example.focustime.presentation.focus

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentFocusBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.newFocus.NewFocusFragment
import javax.inject.Inject

class FocusFragment : Fragment(R.layout.fragment_focus) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentFocusBinding by viewBinding()

    private val viewModel: FocusViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        viewModel.getTypesIndicators(userId)
        viewModel.listTypeIndicators.observe(viewLifecycleOwner){
            if(it != null){
                val periods = it

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.indicatorSpinner.adapter = adapter
            }
        }

        viewModel.counter.observe(viewLifecycleOwner){
            if(it != null)
                binding.focusTimeInput.setText(it.toString())
        }

        with(binding){

            addTime.setOnClickListener {
                viewModel.increment()
            }
            removeTime.setOnClickListener {
                viewModel.decrement()
            }
            createIndicatorButton.setOnClickListener {
                goScreenCreateNewTypeIndicator()
            }
        }

    }

    private fun goScreenCreateNewTypeIndicator(){
        val bundle = Bundle()
        bundle.putLong("time", binding.focusTimeInput.text.toString().toLongOrNull() ?: 0L)
        bundle.putInt("idType", viewModel.getIdTypeIndByName(
            binding.indicatorSpinner.selectedItem.toString()
        ))
        val fr = NewFocusFragment()
        fr.arguments = bundle
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "NEW_FOCUS_FRAGMENT_TAG")
            .addToBackStack("NEW_FOCUS_FRAGMENT_TAG")
            .commit()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}