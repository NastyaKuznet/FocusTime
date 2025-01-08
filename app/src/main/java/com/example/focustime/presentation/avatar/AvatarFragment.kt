package com.example.focustime.presentation.avatar

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAvatarBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AvatarFragment : Fragment(R.layout.fragment_avatar) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAvatarBinding by viewBinding()

    private val viewModel: AvatarFragmentViewModel by viewModels {viewModelFactory}

    private lateinit var avatarAdapter: AvatarAdapter

    var selectedAvatar:Int? = null
    val avatarImages = intArrayOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        setupRecyclerView()

        binding.saveButton.setOnClickListener {
            if (selectedAvatar == null){
                Toast.makeText(requireContext(), "Вы не выбрали картинку", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.updateAvatar(userId, selectedAvatar!!)

            lifecycleScope.launch {
                viewModel.uiState.observe(viewLifecycleOwner) {
                    when (it) {
                        is UIState.Success -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        is UIState.Fail -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT ).show()
                        }
                        else ->{}
                    }
                }
            }

            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("avatarId", selectedAvatar!!)
            editor.apply()
        }

        setUpAvatar()

        binding.userAvatar.setOnClickListener{
            makeCurrentFragment(AccountUserFragment())
        }
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

    private fun setupRecyclerView() {
        val spanCount = 3
        binding.avatarsRecyclerView.layoutManager = GridLayoutManager(context,spanCount)

        avatarAdapter = AvatarAdapter(avatarImages,::onAvatarClick)
        binding.avatarsRecyclerView.adapter = avatarAdapter
    }

    private fun onAvatarClick(avatarResId: Int) {
        selectedAvatar = avatarResId
        avatarAdapter.setSelectedAvatar(avatarResId)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}