package com.example.focustime.presentation.accountUser

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentAccountUserBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.avatar.AvatarFragment
import com.example.focustime.presentation.history.IndicatorsAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserFragment() : Fragment(R.layout.fragment_account_user) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAccountUserBinding by viewBinding()

    private val viewModel: AccountUserFragmentViewModel by viewModels() {viewModelFactory}

    private val indicatorAdapter = IndicatorsAdapter()

    var friendId:Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)

        arguments?.let {
            friendId = it.getInt("friendId", -1)
        }

        if (friendId == -1) {
            viewModel.getUserInfo(userId)
        } else {
            viewModel.getUserInfo(friendId)
        }
        with(binding){
            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UIState.Success -> {
                        avatarContainer.visibility = View.VISIBLE
                        userNickname.visibility = View.VISIBLE
                        userStatus.visibility = View.VISIBLE
                        info.visibility = View.VISIBLE
                        indicatorsList.visibility = View.VISIBLE
                        if (friendId != -1) {
                            buttons.visibility = View.GONE
                            changeAvatarButton.visibility = View.GONE
                        }
                        else {
                            buttons.visibility = View.VISIBLE
                        }
                        loading.visibility = View.GONE

                        setUpAvatar(state.value.id_avatar)
                        userNickname.text = state.value.nickname
                        userStatus.text = state.value.status
                        friendsCount.text = "Friends: " + state.value.friends_count
                        focusTime.text = formatTime(state.value.total_focus_time)
                    }
                    is UIState.Fail -> {
                        avatarContainer.visibility = View.VISIBLE
                        userNickname.visibility = View.VISIBLE
                        userStatus.visibility = View.VISIBLE
                        info.visibility = View.VISIBLE
                        indicatorsList.visibility = View.VISIBLE
                        buttons.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Loading -> {
                        avatarContainer.visibility = View.GONE
                        userNickname.visibility = View.GONE
                        userStatus.visibility = View.GONE
                        info.visibility = View.GONE
                        indicatorsList.visibility = View.GONE
                        buttons.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        }

        if (friendId == -1) {
            viewModel.getIndicators(userId)
        } else {
            viewModel.getIndicators(friendId)
        }

        with(binding) {
            with(indicatorsList) {
                adapter = this@AccountUserFragment.indicatorAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            registerButton.setOnClickListener{
                makeCurrentFragment(AccountUserEditFragment())
            }
            changeAvatarButton.setOnClickListener{
                makeCurrentFragment(AvatarFragment())
            }
            exitAccount.setOnClickListener{
                viewModel.deleteUserIdLocale(requireContext())
                findNavController().navigate(R.id.registrationFragment)
                clearShared()
            }
        }
        viewModel.currentIndicators.observe(viewLifecycleOwner){
            when(it){
                is UIState.Success -> {
                    indicatorAdapter.submitList(it.value)
                }
                is UIState.Fail -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UIState.Loading -> {
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun clearShared(){
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun formatTime(totalSeconds: Int): String{
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        return "Focus Time: $formattedTime"
    }

    private fun setUpAvatar(avatarId:Int){
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
}
