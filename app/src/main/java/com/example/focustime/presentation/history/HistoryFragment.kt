package com.example.focustime.presentation.history

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentHistoryBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import javax.inject.Inject

class HistoryFragment : Fragment(R.layout.fragment_history) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentHistoryBinding by viewBinding()

    private val viewModel: HistoryViewModel by viewModels() {viewModelFactory}

    private val indicatorAdapter = IndicatorsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        val userId = sharedPreferences.getInt("userId", 0)

        viewModel.getIndicators(offlineMode, userId)
        val periods = arrayOf( requireContext().getString(R.string.all_time),
            requireContext().getString(R.string.day),
            requireContext().getString(R.string.month),
            requireContext().getString(R.string.year))
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding) {

            periodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, selectedItemPosition: Int, selectedId: Long
                ) {
                    val adapter = parent?.adapter as? ArrayAdapter<*>
                    val selectedText = adapter?.getItem(selectedItemPosition) ?: ""
                    when(selectedText){
                        requireContext().getString(R.string.day)  -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.DAY)
                        }
                        requireContext().getString(R.string.month) -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.MONTH)
                        }
                        requireContext().getString(R.string.year) -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.YEAR)
                        }
                        requireContext().getString(R.string.all_time) -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.ALL)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            with(indicatorsList){
                adapter = this@HistoryFragment.indicatorAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            periodSpinner.adapter = adapterSpinner
        }

        viewModel.currentIndicators.observe(viewLifecycleOwner){ state ->
            with(binding) {
                when (state) {
                    is UIState.Success -> {
                        content.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        indicatorAdapter.submitList(state.value)
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

    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

}