package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.databinding.FragmentAccountBinding
import ru.kvebekshaev.civettapizza.databinding.FragmentAccountEmployeeBinding
import ru.kvebekshaev.civettapizza.databinding.FragmentOrderEmployeeBinding
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController

class AccountEmployeeFragment : Fragment(){
    lateinit var binding: FragmentAccountEmployeeBinding

    private var navigationController: NavigationVisibilityController? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountEmployeeBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(false)
        binding.appCompatButton.setOnClickListener(){
            findNavController().navigate(R.id.action_accountEmployeeFragment_to_mainEmployeeFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }
}