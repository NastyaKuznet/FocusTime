package com.example.focustime.presentation.registration

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.focustime.di.ViewModelFactory
import javax.inject.Inject

class RegistrationUserFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    //private val binding: FragmentMainIterpolBinding by viewBinding()
    //private val viewModel: RegistrationUserFragmentViewModel by viewModels() {viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*viewModel.screenState.observe(viewLifecycleOwner){ list ->
            val intercom = list?.first() ?: return@observe

            binding.but.text = intercom.name
            //Toast.makeText(requireContext(), intercom.name, Toast.LENGTH_LONG).show()
        }

        viewModel.loadItercomList()
        binding.but.setOnClickListener {
            viewModel.loadItercomList() //!!
        }*/
    }

    override fun onAttach(context: Context) {
        /*context.appComponent.inject(this)*/


        super.onAttach(context)

    }

}