package ifsp.project.welnessmind.presentation.ui.sign_in

import dagger.hilt.android.lifecycle.HiltViewModel
import ifsp.project.welnessmind.HOME_SCREEN
import ifsp.project.welnessmind.SIGN_IN_SCREEN
import ifsp.project.welnessmind.SIGN_UP_SCREEN
import ifsp.project.welnessmind.domain.model.AccountService
import ifsp.project.welnessmind.presentation.ui.WelnessAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
): WelnessAppViewModel()
{
    val email = MutableStateFlow("") // gerenciando o estado do email, inicializado como uma string vazia
    val password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) { // função para lidar com o clique no botão de login
        launchCatching {
            accountService.signIn(email.value, password.value) // chama o método do AccountService para fazer o login
            openAndPopUp(HOME_SCREEN, SIGN_IN_SCREEN) // Vai para a tela de Home a partir da tela Login
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }

    /** TODO
     *  1.  Validação de usuário (retorno visual do erro)
     *  2. Validação de senha (retorno visual do erro)
     * **/
}