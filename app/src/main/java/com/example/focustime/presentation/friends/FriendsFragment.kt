package com.example.focustime.presentation.friends

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.focustime.R
import com.example.focustime.databinding.FragmentFriendsBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.sendRequest.SendRequestFragment
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.accept_request.AcceptRequestFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentFriendsBinding by viewBinding()

    private val viewModel: FriendsFragmentViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendsAdapter = FriendsAdapter(emptyList())
        friendsAdapter.GetfriendOrRequest(0)
        binding.friendsList.adapter = friendsAdapter
        binding.friendsList.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState.stateResult) {
                    ResultUIState.Success -> {
                        friendsAdapter.updateFriends(uiState.friends)
                    }

                    ResultUIState.Error -> {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG)
                            .show()
                    }

                    else -> {}
                }
            }
        }

        val userId = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }
        viewModel.loadFriends(userId)

        binding.addFriendButton.setOnClickListener {
            makeCurrentFragment(SendRequestFragment())
        }

        binding.friendRequestsButton.setOnClickListener {
            makeCurrentFragment(AcceptRequestFragment())
        }
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