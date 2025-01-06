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
import androidx.lifecycle.lifecycleScope
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import kotlinx.coroutines.launch


class NewTypeIndicatorFragment: Fragment(R.layout.fragment_new_type_indicator) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentNewTypeIndicatorBinding by viewBinding()

    private val viewModel: NewTypeIndicatorViewModel by viewModels() {viewModelFactory}

    companion object {
        private const val PICK_IMAGE_REQUEST = 1 // Запрос для выбора изображения
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val offlineMode = sharedPreferences.getBoolean("offlineMode", false)
        val userId = sharedPreferences.getInt("userId", 0)

        with(binding){
            load.setOnClickListener {
                binding.containerImages.removeAllViews()
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }
            save.setOnClickListener {
                viewModel.save(offlineMode, requireContext(), etInputName.text.toString(), userId)
                lifecycleScope.launch {
                    viewModel.resultSave.observe(viewLifecycleOwner) { uiState ->
                        when (uiState) {
                            is UIState.Success -> {
                                Toast.makeText(requireContext(),
                                    uiState.message, Toast.LENGTH_LONG).show()
                                requireActivity().supportFragmentManager.popBackStack()
                            }
                            is UIState.Fail -> {
                                binding.content.visibility = View.VISIBLE
                                binding.loading.visibility = View.GONE
                                Toast.makeText(requireContext(),
                                    uiState.message, Toast.LENGTH_LONG).show()
                            }
                            is UIState.Loading -> {
                                binding.content.visibility = View.GONE
                                binding.loading.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
        
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
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