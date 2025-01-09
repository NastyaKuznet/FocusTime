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

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            with(binding) {
                when (uiState) {
                    is UIState.Success -> {
                        friendsList.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        friendsAdapter.updateFriends(uiState.value)
                    }
                    is UIState.Fail -> {
                        friendsList.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG).show()
                    }
                    is UIState.Loading -> {
                        friendsList.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
            }
        }
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)
        viewModel.loadFriends(userId)

        with(binding){

            friendsList.adapter = friendsAdapter
            friendsList.layoutManager = LinearLayoutManager(context)

            addFriendButton.setOnClickListener {
                makeCurrentFragment(SendRequestFragment())
            }
            friendRequestsButton.setOnClickListener {
                makeCurrentFragment(AcceptRequestFragment())
            }
        }

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