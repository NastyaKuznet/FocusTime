package com.example.focustime.presentation.indicators

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.databinding.FragmentIndicatorsBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorFragment
import javax.inject.Inject

class IndicatorsFragment : Fragment(R.layout.fragment_indicators){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentIndicatorsBinding by viewBinding()

    private val viewModel: IndicatorsViewModel by viewModels() {viewModelFactory}

    private val adapter = TypeIndicatorsAdapter(::onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }
        viewModel.getTypesIndicators(userId)
        with(binding){
            createIndicatorButton.setOnClickListener {
                goScreenCreateNewTypeIndicator()
            }
            with(indicatorList){
                adapter = this@IndicatorsFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.listTypeIndicators.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun goScreenCreateNewTypeIndicator(){
        val fr = NewTypeIndicatorFragment()
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "NEW_TYPE_INDICATOR_FRAGMENT_TAG")
            .addToBackStack("NEW_TYPE_INDICATOR_FRAGMENT_TAG")
            .commit()
    }

    fun onClick(typeIndicator: TypeIndicator) {
        val bundle = Bundle()
        bundle.putInt("idTypeIndicator", typeIndicator.id)
        bundle.putString("nameTypeIndicator", typeIndicator.name)
        val fr = OpenTypeIndicatorFragment()
        fr.arguments = bundle
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "OPEN_TYPE_INDICATOR_FRAGMENT_TAG")
            .addToBackStack("OPEN_TYPE_INDICATOR_FRAGMENT_TAG")
            .commit()

    }
}