package ifsp.project.welnessmind.presentation.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ifsp.project.welnessmind.data.remote.FirestoreService
import ifsp.project.welnessmind.domain.repository.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService
): ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    init {
        viewModelScope.launch {
            if (accountService.hasUser()) {
                val uid = accountService.currentUserId
                val name = firestoreService.getUserName(uid)
                _userName.value = name ?: ""
            }
        }
    }

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date.asStateFlow()

    fun onDateChange(newDate: String) {
        _date.value = newDate
    }
    private val _horasSono = MutableStateFlow(0)
    val horasSono: StateFlow<Int> = _horasSono.asStateFlow()

    private val _horasEstudo = MutableStateFlow(0)
    val horasEstudo: StateFlow<Int> = _horasEstudo.asStateFlow()

    private val _horasTrabalho = MutableStateFlow(0)
    val horasTrabalho: StateFlow<Int> = _horasTrabalho.asStateFlow()

    private val _rbTreino = MutableStateFlow(false)
    val rbTreino: StateFlow<Boolean> = _rbTreino.asStateFlow()

    fun onHorasSonoChange(newHorasSono: Int) {
        _horasSono.value = newHorasSono
    }

    fun onHorasEstudoChange(newHorasEstudo: Int) {
        _horasEstudo.value = newHorasEstudo
    }

    fun onHorasTrabalhoChange(newHorasTrabalho: Int) {
        _horasTrabalho.value = newHorasTrabalho
    }

    fun onRbTreinoChange(isChecked: Boolean) {
        _rbTreino.value = isChecked
    }
}