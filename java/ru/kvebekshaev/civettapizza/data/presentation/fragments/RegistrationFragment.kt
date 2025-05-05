package ru.kvebekshaev.civettapizza.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.data.db.api.RetrofitClient
import ru.kvebekshaev.civettapizza.data.repositories.SessionRepositoryImpl
import ru.kvebekshaev.civettapizza.data.repositories.UserRepositoryImpl
import ru.kvebekshaev.civettapizza.databinding.FragmentRegistrationBinding
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.usecases.LoginUserUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.RegisterUserUseCase
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController
import ru.kvebekshaev.civettapizza.presentation.presenters.RegistrationPresenter
import ru.kvebekshaev.civettapizza.presentation.views.AuthorizationView
import ru.kvebekshaev.civettapizza.presentation.views.RegistrationView

class RegistrationFragment : Fragment(), RegistrationView {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var presenter: RegistrationPresenter

    private var navigationController: NavigationVisibilityController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = UserRepositoryImpl(RetrofitClient.userApiService)
        val login = SessionRepositoryImpl(requireContext())
        presenter = RegistrationPresenter(RegisterUserUseCase(repository), LoginUserUseCase(login),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController?.setNavigationVisibility(false)
        var navController = findNavController()
        binding.labelIAlreadyHaveAccount.setOnClickListener{
            navController.navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }
        binding.registrationButton.setOnClickListener{
            if (binding.editBoxPassword.text.toString() != binding.editBoxRepeatPassword.text.toString()){
                MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                    .setTitle("Ошибка")
                    .setMessage("У вас разные пароли ${binding.editBoxPassword.text.toString()}${binding.editBoxRepeatPassword.text.toString()}")
                    .setPositiveButton("Ок", null)
                    .show()
            }
                else {
                val login = binding.editBoxLogin.text.toString()
                val password = binding.editBoxPassword.text.toString()
                val email = binding.editBoxEmailPhone.text.toString()
                val phone = binding.editBoxPhone.text.toString()
                presenter.registrationUser(login, password, email, phone)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationController = null
    }

    override fun showUser(user: UserEntity){
        val navController = findNavController()
        navController.navigate(R.id.action_authorizationFragment_to_mainClientFragment)
    }

    override fun showError(e: Throwable) {
        when(e.message) {
            "BAD REQUEST" ->{MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                .setTitle("Ошибка")
                .setMessage("Введите все необходимые данные")
                .setPositiveButton("Ок", null)
                .show()
                e.printStackTrace()}
            else -> {MaterialAlertDialogBuilder(requireContext(), R.style.CivettaAlertDialog)
                .setTitle("Ошибка")
                .setMessage(e.message)
                .setPositiveButton("Ок", null)
                .show()
                e.printStackTrace()}
        }
    }
}