package ifsp.project.welnessmind.presentation.ui.sign_in

import dagger.hilt.android.lifecycle.HiltViewModel
import ifsp.project.welnessmind.HOME_SCREEN
import ifsp.project.welnessmind.RECOVERY_SCREEN
import ifsp.project.welnessmind.SIGN_IN_SCREEN
import ifsp.project.welnessmind.SIGN_UP_SCREEN
import ifsp.project.welnessmind.data.remote.FirestoreService
import ifsp.project.welnessmind.domain.repository.AccountService
import ifsp.project.welnessmind.presentation.ui.WelnessAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : WelnessAppViewModel() {
    val email =
        MutableStateFlow("") // gerenciando o estado do email, inicializado como uma string vazia
    val password = MutableStateFlow("")

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) { // função para lidar com o clique no botão de login
        launchCatching {
            try {
                if (email.value.isEmpty()) {
                    _errorMessage.value = "Email não pode ser vazio"
                    return@launchCatching
                }
                if (password.value.isEmpty()) {
                    _errorMessage.value = "Senha não pode ser vazia"
                    return@launchCatching
                }

                accountService.signIn(email.value, password.value)
                val uid = accountService.currentUserId
                
                // Validar senha no Firestore
                if (!firestoreService.validatePassword(uid, password.value)) {
                    _errorMessage.value = "Senha incorreta"
                    return@launchCatching
                }
                
                _errorMessage.value = null
                openAndPopUp(HOME_SCREEN, SIGN_IN_SCREEN)

            } catch (e: Exception) {
                when (e.message) {
                    "ERROR_USER_NOT_FOUND" -> _errorMessage.value = "Usuário não encontrado"
                    "ERROR_WRONG_PASSWORD" -> _errorMessage.value = "Senha incorreta"
                    "ERROR_INVALID_EMAIL" -> _errorMessage.value = "Email inválido"
                    else -> _errorMessage.value = "Erro ao fazer login. Tente novamente"
                }
            }
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun onForgotPasswordClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RECOVERY_SCREEN, SIGN_IN_SCREEN)
    }

    /** TODO
     *  1. Validação de senha (retorno visual do erro) ✔
     *  2.  Validação de usuário (retorno visual do erro) ✔
     * **/
}