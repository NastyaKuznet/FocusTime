package com.example.focustime.presentation.focus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.focustime.R

class FocusFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_focus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация Spinner
        val periodSpinner: Spinner = view.findViewById(R.id.indicatorSpinner)

        // Создание массива строк для элементов Spinner
        val periods = arrayOf("Индикатор1", "Индикатор2", "Индикатор3")

        // Создание адаптера для Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Установка адаптера для Spinner
        periodSpinner.adapter = adapter
    }
}