package ru.kvebekshaev.civettapizza.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvebekshaev.civettapizza.domain.entities.UserEntity
import ru.kvebekshaev.civettapizza.domain.usecases.GetUserUseCase
import ru.kvebekshaev.civettapizza.domain.usecases.LoginUserUseCase
import ru.kvebekshaev.civettapizza.presentation.views.AuthorizationView

class AuthorizationPresenter(
    private val getUserUseCase: GetUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val view: AuthorizationView,
    var isAdmin: Boolean = false
) {
    fun authorizationUser(email: String, password: String){
        CoroutineScope(Dispatchers.Main).launch {
            val result: Result<UserEntity>
            if (isAdmin)
            {
                 result = getUserUseCase.execute(email,password,1)
            }else
            {
                 result = getUserUseCase.execute(email,password,2)
            }
            result.fold(
                onSuccess = { data: UserEntity ->
                    view.showUser(data)
                    loginUserUseCase.execute(data)
                },
                onFailure = { e: Throwable ->
                    view.showError(e)
                }
            )
        }
    }
    fun testAuth(){
        val data = UserEntity(1,"Guest","Отсутствует","Отсутствует",2)
        CoroutineScope(Dispatchers.Main).launch {
            view.showUser(data)
            loginUserUseCase.execute(data)
        }
    }
}