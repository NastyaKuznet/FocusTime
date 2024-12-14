package com.example.focustime.presentation.newFocus

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentNewFocusBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewFocusFragment: Fragment(R.layout.fragment_new_focus) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentNewFocusBinding by viewBinding()

    private val viewModel: NewFocusViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val time = arguments?.getLong("time")
        val idType = arguments?.getInt("idType")

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        if(time != null && idType != null)
            viewModel.startTimer(time, idType, userId)

        viewModel.time.observe(viewLifecycleOwner) { timeInSeconds ->
            val time = formatTime(timeInSeconds)
            binding.time.text = time
        }

        viewModel.selectedImage.observe(viewLifecycleOwner){
            val bitmap = BitmapFactory.decodeStream(it)
            binding.containerImageState.setImageBitmap(bitmap)
        }

        viewModel.stateTime.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is UIState.Fail -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        with(binding){
            abort.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            pause.setOnClickListener {
                if(pause.text.toString() == "Пауза") {
                    viewModel.pauseTimer()
                    pause.text = "Продолжить"
                } else {
                    viewModel.startTimer(time!!, idType!!, userId)
                    pause.text = "Пауза"
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun formatTime(seconds: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)
        val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}