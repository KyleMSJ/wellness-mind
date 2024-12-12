package ifsp.project.welnessmind.presentation.ui.edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(): ViewModel() {
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