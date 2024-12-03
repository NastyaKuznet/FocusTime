package com.example.focustime.presentation.createTypeIndicator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentCreateTypeIndicatorsBinding
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment

class CreateTypeIndicatorsFragment : Fragment(R.layout.fragment_create_type_indicators) {

    private val binding: FragmentCreateTypeIndicatorsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            createIndicatorButton.setOnClickListener {
                val fr = NewTypeIndicatorFragment()
                getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fr, "NEW_TYPE_INDICATOR_FRAGMENT_TAG")
                    .addToBackStack("NEW_TYPE_INDICATOR_FRAGMENT_TAG")
                    .commit()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}