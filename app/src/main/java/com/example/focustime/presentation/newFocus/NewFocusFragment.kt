package com.example.focustime.presentation.newFocus

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
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
import android.app.AlertDialog
import androidx.activity.OnBackPressedCallback

class NewFocusFragment: Fragment(R.layout.fragment_new_focus) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentNewFocusBinding by viewBinding()

    private val viewModel: NewFocusViewModel by viewModels() {viewModelFactory}

    private var wasExit: Boolean = false
    private var wasFinish: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val time = arguments?.getInt("time")
        val idType = arguments?.getInt("idType")

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        val userId = sharedPreferences.getInt("userId", 0)

        if(time != null && idType != null)
            viewModel.startTimer(offlineMode, time, idType, userId)

        viewModel.time.observe(viewLifecycleOwner) { timeInSeconds ->
            val time = formatTime(timeInSeconds)
            binding.time.text = time
        }

        viewModel.selectedImage.observe(viewLifecycleOwner){
            val bitmap = BitmapFactory.decodeStream(it)
            binding.containerImageState.setImageBitmap(bitmap)
        }

        viewModel.stateLoadingImages.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    binding.content.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                }
                is UIState.Fail -> {
                    binding.content.visibility = View.VISIBLE
                    binding.loading.visibility = View.GONE
                }
                is UIState.Loading -> {
                    binding.content.visibility = View.GONE
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        viewModel.stateTime.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    wasFinish = true
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is UIState.Fail -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UIState.Loading -> {
                    binding.content.visibility = View.GONE
                    binding.loading.visibility = View.VISIBLE
                }
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
                    viewModel.startTimer(offlineMode, time!!, idType!!, userId)
                    pause.text = "Пауза"
                }
            }
        }

        val backPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                showBackConfirmationDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun formatTime(seconds: Long): String {
        val minutes = TimeUnit.SECONDS.toMinutes(seconds)
        val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
        val hours = TimeUnit.MINUTES.toHours(minutes)
        val remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
        return String.format("%02d:%02d:%02d", hours, remainingMinutes, remainingSeconds)
    }

    private fun showBackConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Предупреждение")
            .setMessage("Таймер сбросится, если выйдете из приложения. Продолжить?")
            .setPositiveButton("Выйти") { _, _ ->
                viewModel.pauseTimer()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Продолжить") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun onStop() {
        super.onStop()
        if(!wasExit && !wasFinish) {
            viewModel.pauseTimer()
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        wasExit = true
        AlertDialog.Builder(requireContext())
            .setTitle("Предупреждение")
            .setMessage("Ваши минуты не были сохранены, потому что вы покинули приложение.")
            .setPositiveButton("Ясно") { _, _ ->
                viewModel.pauseTimer()
                if(isCurrentFragment())
                    requireActivity().supportFragmentManager.popBackStack()
            }
            .setCancelable(false)
            .show()
    }

    private fun isCurrentFragment() : Boolean{
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_container) as? androidx.navigation.fragment.NavHostFragment
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
        return currentFragment is NewFocusFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.pauseTimer()
    }
}