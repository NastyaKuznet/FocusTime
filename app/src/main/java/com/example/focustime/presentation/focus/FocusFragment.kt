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
            when(it) {
                is UIState.Success -> {
                    binding.content.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                    val periods = it.value

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        periods
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.indicatorSpinner.adapter = adapter
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

        viewModel.hour.observe(viewLifecycleOwner){
            if(it != null)
                binding.focusTimeInputHour.setText(it.toString())
        }

        viewModel.minute.observe(viewLifecycleOwner){
            if(it != null)
                binding.focusTimeInputMinute.setText(it.toString())
        }

        viewModel.second.observe(viewLifecycleOwner){
            if(it != null)
                binding.focusTimeInputSecond.setText(it.toString())
        }

        with(binding){

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
                        "Нужно выбрать индикатор.",
                        Toast.LENGTH_SHORT).show()

                } else if(translateToSecond() == 0) {
                    Toast.makeText(requireContext(),
                        "Укажите время",
                        Toast.LENGTH_SHORT).show()
                } else {
                    goScreenCreateNewTypeIndicator()
                }
            }
        }

        binding.userAvatar.setOnClickListener{
            makeCurrentFragment(AccountUserFragment())
        }
        setUpAvatar()
    }

    private fun setUpAvatar(){
        val avatarId = arguments?.getInt("avatarId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("avatarId", -1)
        }

        val avatarResId = when (avatarId) {
            0 -> R.drawable.avatar1
            1 -> R.drawable.avatar2
            2 -> R.drawable.avatar3
            3 -> R.drawable.avatar4
            4 -> R.drawable.avatar5
            else -> R.drawable.avatar1
        }
        binding.userAvatar.setImageResource(avatarResId)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
        bundle.putInt("userId", userId)
        val fr = NewFocusFragment()
        fr.arguments = bundle
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "NEW_FOCUS_FRAGMENT_TAG")
            .addToBackStack("NEW_FOCUS_FRAGMENT_TAG")
            .commit()
    }

    fun translateToSecond(): Int{
        return (viewModel.hour.value?.toInt() ?: 0) * 3600 +
                (viewModel.minute.value?.toInt() ?: 0) * 60 +
                (viewModel.second.value?.toInt() ?: 0)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}