package ifsp.project.welnessmind.presentation.ui.sign_up

import dagger.hilt.android.lifecycle.HiltViewModel
import ifsp.project.welnessmind.EDIT_SCREEN
import ifsp.project.welnessmind.HOME_SCREEN
import ifsp.project.welnessmind.SIGN_IN_SCREEN
import ifsp.project.welnessmind.SIGN_UP_SCREEN
import ifsp.project.welnessmind.domain.model.AccountService
import ifsp.project.welnessmind.presentation.ui.WelnessAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : WelnessAppViewModel() {
    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

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

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Password don't match")
            }
            accountService.signUp(email.value, password.value)
            openAndPopUp(EDIT_SCREEN, SIGN_UP_SCREEN) // Vai para a tela de edição a partir da tela de cadastro
        }
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_IN_SCREEN, SIGN_UP_SCREEN)
    }

    /** TODO
     *  1. Regras para criação de senha
     * 2. Visualizar senha clicado no ícone no final do campo
     **/



}
