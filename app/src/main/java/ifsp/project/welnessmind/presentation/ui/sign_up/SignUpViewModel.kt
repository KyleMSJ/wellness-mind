package ifsp.project.welnessmind.presentation.ui.sign_up

import dagger.hilt.android.lifecycle.HiltViewModel
import ifsp.project.welnessmind.EDIT_SCREEN
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
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
) : WelnessAppViewModel() {
    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _confirmPasswordVisible = MutableStateFlow(false)
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible.asStateFlow()

    fun updateNome(newNome: String) {
        name.value = newNome
    }
    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.length < 6 -> "A senha deve ter pelo menos 6 caracteres"
            !password.any { it.isDigit() } -> "A senha deve conter pelo menos 1 número"
            !password.any { it.isUpperCase() } -> "A senha deve conter pelo menos 1 letra maiúscula"
            !password.any { it.isLowerCase() } -> "A senha deve conter pelo menos 1 letra minúscula"
            else -> null
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            try {
                validatePassword(password.value)?.let { error ->
                    _errorMessage.value = error
                    return@launchCatching
                }
                
                if (password.value != confirmPassword.value) {
                    _errorMessage.value = "As senhas não coincidem"
                    return@launchCatching
                }
                
                accountService.signUp(email.value, password.value)
                val uid = accountService.currentUserId
                firestoreService.saveUserData(
                    uid = uid,
                    name = name.value,
                    email = email.value,
                    password = password.value
                )
                openAndPopUp(EDIT_SCREEN, SIGN_UP_SCREEN)
            } catch (e: Exception) {
                when (e.message) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> _errorMessage.value = "Email já cadastrado"
                    "ERROR_INVALID_EMAIL" -> _errorMessage.value = "Email inválido"
                    "ERROR_WEAK_PASSWORD" -> _errorMessage.value = "Senha muito fraca"
                    else -> _errorMessage.value = "Erro ao fazer cadastro. Tente novamente"
                }
            }
        }
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_IN_SCREEN, SIGN_UP_SCREEN)
    }

    fun togglePasswordVisibility() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }

    /** TODO
     *  1. Visualizar senha clicado no ícone no final do campo ✔
     * 2. Regras para criação de senha ✔
     **/



}
