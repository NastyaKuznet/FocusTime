package com.example.focustime.presentation.indicators

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentIndicatorsBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import javax.inject.Inject

class IndicatorsFragment : Fragment(R.layout.fragment_indicators) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentIndicatorsBinding by viewBinding()

    private val viewModel: IndicatorsViewModel by viewModels() {viewModelFactory}

    private val indicatorAdapter = IndicatorsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIndicators(1)//!!!
        val periods = arrayOf("За день", "За месяц", "За год", "За всё время")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding) {

            periodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    val adapter = parent?.adapter as? ArrayAdapter<String>
                    val selectedText = adapter?.getItem(selectedItemPosition) ?: ""
                    when(selectedText){
                        "За день"  -> {
                            viewModel.getIndicatorsDay()
                        }
                        "За месяц" -> {
                            viewModel.getIndicatorsMonth()
                        }
                        "За год" -> {
                            viewModel.getIndicatorsYear()
                        }
                        "За всё время" -> {
                            viewModel.getIndicatorsAllTime()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            with(indicatorsList){
                adapter = this@IndicatorsFragment.indicatorAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            periodSpinner.adapter = adapterSpinner
        }
        viewModel.currentIndicators.observe(viewLifecycleOwner){
            indicatorAdapter.submitList(it)
        }

        viewModel.getIndicatorsDay()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}