package com.example.focustime.presentation.acceptRequest

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
import com.example.focustime.databinding.FragmentAcceptRequestBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.friends.Friend
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AcceptRequestFragment : Fragment(R.layout.fragment_accept_request) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentAcceptRequestBinding by viewBinding()

    private val viewModel: AcceptRequestFragmentViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val friendsAdapter = RequestAdapter(emptyList(),::onAddFriendClicked,::accountFriend)
        binding.requestList.adapter = friendsAdapter
        binding.requestList.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.uiStateRequest.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is UIState.Success -> {
                        with(binding){
                            requestList.visibility = View.VISIBLE
                            loading.visibility = View.GONE
                        }
                        friendsAdapter.updateFriends(uiState.value)
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Fail -> {
                        with(binding){
                            requestList.visibility = View.VISIBLE
                            loading.visibility = View.GONE
                        }
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Loading -> {
                        with(binding){
                            requestList.visibility = View.GONE
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

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun accountFriend(userIdFriend: Int){
        val fragment = AccountUserFragment().apply {
            arguments = Bundle().apply {
                putInt("friendId", userIdFriend)
            }
        }

        makeCurrentFragment(fragment)
    }

    private fun onAddFriendClicked(friend: Friend): Boolean{
        var result = false

        val userId1 = friend.user_id

        val userId2 = arguments?.getInt("userId") ?: run {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.getInt("userId", 0)
        }

        viewModel.acceptRequest(userId1, userId2)
        lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) {
                when (it) {
                    is UIState.Success -> {
                        result = true
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()
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

        return result
    }
}