package ru.kvebekshaev.civettapizza.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.data.db.api.RetrofitClient
import ru.kvebekshaev.civettapizza.data.repositories.SessionRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.UserRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentAuthorizationBinding
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetUserUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.LoginUserUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.presenters.AuthorizationPresenter
import ru.kvebekshaev.civettapizza.presentation.views.AuthorizationView

class AuthorizationFragment : Fragment(), AuthorizationView {
    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var presenter: AuthorizationPresenter

    private var navigationController: NavigationVisibilityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = UserRepositoryImpl(RetrofitClient.userApiService)
        val login = SessionRepositoryImpl(requireContext())
        presenter = AuthorizationPresenter(GetUserUseCase(repository), LoginUserUseCase(login),this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationController = context as? NavigationVisibilityController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(false)
        val navController = findNavController()

        binding.tabLayoutChooseClientEmployee.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        // Показать элементы для первой вкладки
                        binding.registrationButton.visibility = View.VISIBLE
                        binding.labelIDKPassword.visibility = View.VISIBLE
                        presenter.isAdmin = false
                    }
                    1 -> {
                        // Скрыть элементы для второй вкладки
                        binding.registrationButton.visibility = View.GONE
                        binding.labelIDKPassword.visibility = View.GONE
                        presenter.isAdmin = true
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.registrationButton.setOnClickListener{
            navController.navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        binding.loginButton.setOnClickListener{
            val email = binding.editBoxEmailPhone.text.toString()
            val password = binding.editBoxPassword.text.toString()
            presenter.authorizationUser(email, password)
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    override fun showUser(user: UserEntity){
        if (user.roleId == 2) {
            val navController = findNavController()
            navController.navigate(R.id.action_authorizationFragment_to_mainClientFragment)
        } else
        {
            val navController = findNavController()
            navController.navigate(R.id.action_authorizationFragment_to_mainEmployeeFragment)
        }
    }

    override fun showError(e: Throwable) {
        when(e.message) {
            "BAD REQUEST" ->{MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                .setTitle("Ошибка")
                .setMessage("Введите логин и пароль")
                .setPositiveButton("Ок", null)
                .show() }
            "UNAUTHORIZED" ->{MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                .setTitle("Ошибка")
                .setMessage("Введите правильный логин и пароль")
                .setPositiveButton("Ок", null)
                .show() }
            else -> {MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                .setTitle("Ошибка")
                .setMessage(e.message)
                .setPositiveButton("Ок", null)
                .show()
                    e.printStackTrace()}
        }
    }
}