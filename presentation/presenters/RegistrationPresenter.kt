package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.usecases.LoginUserUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.RegisterUserUseCase
import ru.kvebekshaev.civettapizza.presentation.views.RegistrationView

class RegistrationPresenter(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val view: RegistrationView
){
    fun registrationUser(login: String, phoneNumber: String, email: String, password: String){
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val user = UserEntity(1, login, email, phoneNumber, 2)
                    val result = registerUserUseCase.execute(user, password)
                    result.fold(
                        onSuccess = { data: UserEntity ->
                            loginUserUseCase.execute(data)
                            view.showUser(data)
                        },
                        onFailure = { e: Throwable ->
                            view.showError(e)
                        }
                    )
                }
            }
            catch(e: Exception){
                view.showError(e)
            }

    }
}