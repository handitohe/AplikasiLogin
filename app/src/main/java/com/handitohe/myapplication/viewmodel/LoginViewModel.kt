package com.handitohe.myapplication.viewmodel

import com.handitohe.myapplication.model.Credential
import com.handitohe.myapplication.model.LoginResult
import com.handitohe.myapplication.repository.BebasBayarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed class LoginState(open val credential: Credential) {
    data class Filling(override val credential: Credential) : LoginState(credential)
    data class Loading(override val credential: Credential) : LoginState(credential)
    data class LoginSuccess(override val credential: Credential) : LoginState(credential)
    data class LoginFailed(override val credential: Credential, val message: String) : LoginState(credential)
}

sealed class LoginEvent {
    data class SetUser(val user: String) : LoginEvent()
    data class SetPassword(val password: String) : LoginEvent()
    object Login : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: BebasBayarRepository) : BaseViewModel<LoginState, LoginEvent>(
    initialState = LoginState.Filling(Credential("", ""))
) {
    @FlowPreview
    override suspend fun mapEventToState(event: LoginEvent): Flow<LoginState> {
        val prevState = state.value
        return when (event) {
            is LoginEvent.SetUser -> flowOf(LoginState.Filling(prevState.credential.copy(user = event.user)))
            is LoginEvent.SetPassword -> flowOf(LoginState.Filling(prevState.credential.copy(password = event.password)))
            is LoginEvent.Login -> repository.login(prevState.credential)
                .map<LoginResult, LoginState> { LoginState.LoginSuccess(prevState.credential) }
                .catch { emit(LoginState.LoginFailed(prevState.credential, it.message ?: "Unknown Error")) }
                .onStart { emit(LoginState.Loading(prevState.credential)) }
        }
    }
}
