package com.example.focustime.presentation.createNewTypeIndicator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentNewTypeIndicatorBinding
import com.example.focustime.di.ViewModelFactory
import javax.inject.Inject
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.avatar.AvatarFragment
import kotlinx.coroutines.launch


class NewTypeIndicatorFragment: Fragment(R.layout.fragment_new_type_indicator) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentNewTypeIndicatorBinding by viewBinding()

    private val viewModel: NewTypeIndicatorViewModel by viewModels() {viewModelFactory}

    private lateinit var pickImagesLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        val userId = sharedPreferences.getInt("userId", 0)

        pickImagesLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onImagesPicked(result)
            }

        with(binding){
            load.setOnClickListener {
                binding.containerImages.removeAllViews()
                pickImages()
            }
            save.setOnClickListener {
                viewModel.save(offlineMode, requireContext(), etInputName.text.toString(), userId)
            }

            viewModel.resultSave.observe(viewLifecycleOwner) { state ->
                with(binding) {
                    when (state) {
                        is UIState.Success -> {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        is UIState.Fail -> {
                            content.visibility = View.VISIBLE
                            loading.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                state.message, Toast.LENGTH_LONG
                            ).show()
                        }
                        is UIState.Loading -> {
                            content.visibility = View.GONE
                            loading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun pickImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickImagesLauncher.launch(intent)
    }

    private fun onImagesPicked(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val clipData = data.clipData
                if (clipData != null) {
                    val count = clipData.itemCount
                    if(count != 5){
                        Toast.makeText(requireContext(),
                            "Нужно выбрать пять изображений!",
                            Toast.LENGTH_LONG)
                            .show()
                        return
                    }
                    val listUri = mutableListOf<Uri>()
                    for (i in 0 until count) {
                        val imageUri = clipData.getItemAt(i).uri
                        listUri.add(imageUri)
                        addPictureOnScreen(imageUri, i)
                    }
                    viewModel.setImages(listUri)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun addPictureOnScreen(imageUri: Uri, i: Int){
        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)

        val tv = TextView(requireContext())
        val paramsTv = tv.layoutParams as? ViewGroup.MarginLayoutParams
            ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsTv.setMargins(10, 10, 10, 10)
        tv.text = "${i+1} стадия"
        tv.layoutParams = paramsTv
        binding.containerImages.addView(tv)

        val iv = ImageView(requireContext())
        val paramsIv = iv.layoutParams as? ViewGroup.MarginLayoutParams
            ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramsIv.setMargins(10, 10, 10, 10)
        iv.layoutParams = paramsIv
        iv.setImageBitmap(bitmap)
        binding.containerImages.addView(iv)
    }

}