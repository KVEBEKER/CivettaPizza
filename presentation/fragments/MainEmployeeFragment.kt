package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.databinding.FragmentMainEmployeeBinding
import ru.kvebekshaev.civettapizza.databinding.FragmentOrderEmployeeBinding
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController

class MainEmployeeFragment : Fragment() {
    lateinit var binding: FragmentMainEmployeeBinding

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
        binding = FragmentMainEmployeeBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(false)
        binding.myOrdersButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainEmployeeFragment_to_orderEmployeeFragment)
        }
        binding.myAccountButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainEmployeeFragment_to_accountEmployeeFragment)
        }
        binding.myPastOrdersButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainEmployeeFragment_to_pastOrderEmployeeFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }
}