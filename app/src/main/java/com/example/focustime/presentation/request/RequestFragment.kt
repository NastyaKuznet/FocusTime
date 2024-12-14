package com.example.focustime.presentation.request

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
import com.example.focustime.databinding.FragmentRequestBinding
import com.example.focustime.di.ViewModelFactory
import com.example.focustime.di.appComponent
import com.example.focustime.presentation.addFriends.AddFriendsFragment
import com.example.focustime.presentation.friends.FriendsAdapter
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestFragment : Fragment(R.layout.fragment_request) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding: FragmentRequestBinding by viewBinding()

    private val viewModel: RequestFragmentViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendsAdapter = FriendsAdapter(emptyList())
        friendsAdapter.GetfriendOrRequest(1)
        binding.requestList.adapter = friendsAdapter
        binding.requestList.layoutManager = LinearLayoutManager(context)

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

        //friendsAdapter.showTwoFriends()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
}