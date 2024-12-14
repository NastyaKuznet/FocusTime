package com.example.focustime.presentation.openTypeIndicator

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentOpenTypeIndicatorBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.indicators.IndicatorsFragment
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class OpenTypeIndicatorFragment: Fragment(R.layout.fragment_open_type_indicator) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentOpenTypeIndicatorBinding by viewBinding()

    private val viewModel: OpenTypeIndicatorViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idType = arguments?.getInt("idTypeIndicator")
        val nameType = arguments?.getString("nameTypeIndicator")
        if(idType != null)
            viewModel.getImages(idType)


        with(binding){
            nameTypeIndicatorOpen.text = nameType
            viewModel.images.observe(viewLifecycleOwner){
                when(it){
                    is UIState.Success -> {
                        for (i in 0 until  it.value.size) {
                            addPictureOnScreen(it.value[i], i)
                        }
                    }
                    is UIState.Fail -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
            deleteTypeIndicator.setOnClickListener {
                if(idType != null) {
                    viewModel.deleteType(idType)
                    lifecycleScope.launch {
                        viewModel.stateDel.observe(viewLifecycleOwner) {
                            when (it) {
                                is UIState.Success -> {
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                        .show()
                                    goScreenOpenTypeIndicator()
                                }

                                is UIState.Fail -> {
                                    Toast.makeText(
                                        requireContext(),
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else ->{}
                            }
                        }
                    }
                }
            }
        }


    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun addPictureOnScreen(inputStream: InputStream, i: Int){
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val tv = TextView(requireContext())
        val paramsTv = tv.layoutParams as? ViewGroup.MarginLayoutParams
            ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsTv.setMargins(10, 10, 10, 10)
        tv.text = "${i+1} стадия"
        tv.layoutParams = paramsTv
        binding.containerImagesOpen.addView(tv)

        val iv = ImageView(requireContext())
        val paramsIv = iv.layoutParams as? ViewGroup.MarginLayoutParams
            ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsIv.setMargins(10, 10, 10, 10)
        iv.layoutParams = paramsIv
        iv.setImageBitmap(bitmap)
        binding.containerImagesOpen.addView(iv)
    }

    private fun goScreenOpenTypeIndicator(){
        val fr = IndicatorsFragment()
        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fr, "OPEN_TYPE_INDICATOR_FRAGMENT_TAG")
            .addToBackStack("OPEN_TYPE_INDICATOR_FRAGMENT_TAG")
            .commit()
    }
}