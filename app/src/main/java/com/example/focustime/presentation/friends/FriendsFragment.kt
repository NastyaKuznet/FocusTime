package com.example.focustime.presentation.friends

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentFriendsBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.sendRequest.SendRequestFragment
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.acceptRequest.AcceptRequestFragment
import com.example.focustime.presentation.accountUser.AccountUserFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentFriendsBinding by viewBinding()

    private val viewModel: FriendsFragmentViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendsAdapter = FriendsAdapter(emptyList(),::accountFriend)
        friendsAdapter.GetfriendOrRequest(0)
        binding.friendsList.adapter = friendsAdapter
        binding.friendsList.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is UIState.Success -> {
                        with(binding){
                            friendsList.visibility = View.VISIBLE
                            loading.visibility = View.GONE
                        }
                        friendsAdapter.updateFriends(uiState.value)
                        //Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Fail -> {
                        with(binding){
                            friendsList.visibility = View.VISIBLE
                            loading.visibility = View.GONE
                        }
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Loading -> {
                        with(binding){
                            friendsList.visibility = View.GONE
                            loading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }
        viewModel.loadFriends(userId)

        setUpAvatar()

        binding.addFriendButton.setOnClickListener {
            makeCurrentFragment(SendRequestFragment())
        }

        binding.friendRequestsButton.setOnClickListener {
            makeCurrentFragment(AcceptRequestFragment())
        }

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

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun accountFriend(userIdFriend: Int){
        val fragment = AccountUserFragment().apply {
            arguments = Bundle().apply {
                putInt("friendId", userIdFriend)
            }
        }

        makeCurrentFragment(fragment)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}