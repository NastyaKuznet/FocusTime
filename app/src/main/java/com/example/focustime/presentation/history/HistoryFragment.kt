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
import com.example.focustime.presentation.accountUser.AccountUserFragment
import javax.inject.Inject

class HistoryFragment : Fragment(R.layout.fragment_history) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentHistoryBinding by viewBinding()

    private val viewModel: HistoryViewModel by viewModels() {viewModelFactory}

    private val indicatorAdapter = IndicatorsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }
        viewModel.getIndicators(userId)
        val periods = arrayOf( "За всё время", "За день", "За месяц", "За год")
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
                            viewModel.filterIndicators(HistoryViewModel.FilterType.DAY)
                        }
                        "За месяц" -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.MONTH)
                        }
                        "За год" -> {
                            viewModel.filterIndicators(HistoryViewModel.FilterType.YEAR)
                        }
                        "За всё время" -> {
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
        viewModel.currentIndicators.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    binding.content.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                    indicatorAdapter.submitList(it.value)
                }
                is UIState.Fail -> {
                    binding.content.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UIState.Loading -> {
                    binding.content.visibility = View.GONE
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }
        binding.userAvatar.setOnClickListener{
            makeCurrentFragment(AccountUserFragment())
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}