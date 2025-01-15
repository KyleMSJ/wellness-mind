import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import ifsp.project.welnessmind.presentation.ui.edit.EditViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel()
    /*userName: String*/
) {
    val horasSono by viewModel.horasSono.collectAsState()
    val horasEstudo by viewModel.horasEstudo.collectAsState()
    val horasTrabalho by viewModel.horasTrabalho.collectAsState()
    val rbTreino by viewModel.rbTreino.collectAsState()
    val userName by viewModel.userName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White),
            )
            Text(
                text = "Olá, $userName!",
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Preencha as informações abaixo",
            fontSize = 20.sp
        )

        Text(
            text = "Serão importantes para te dar um retorno inicial das métricas",
            fontSize = 12.sp,
            fontStyle = Italic,
            modifier = Modifier.offset(y = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        DatePickerDocked()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Média de horas de sono",
                fontSize = 14.sp,
                modifier = Modifier
                    .offset(y = 4.dp)
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = horasSono.toString(),
                onValueChange = { viewModel.onHorasSonoChange(it.toIntOrNull() ?: 0) },
                placeholder = { Text(text = "ex: 8") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .heightIn(min = 10.dp)
                    .width(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Média de horas de estudo",
                fontSize = 14.sp,
                modifier = Modifier
                    .offset(y = 4.dp)
                    .padding(bottom = 8.dp)
            )

            TextField(
                value =horasEstudo.toString(),
                onValueChange = { viewModel.onHorasEstudoChange(it.toIntOrNull() ?: 0) },
                placeholder = { Text(text = "ex: 3") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .heightIn(min = 10.dp)
                    .width(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Média de horas de trabalho",
                fontSize = 14.sp,
                modifier = Modifier
                    .offset(y = 4.dp)
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = horasTrabalho.toString(),
                onValueChange = { viewModel.onHorasTrabalhoChange(it.toIntOrNull() ?: 0) },
                placeholder = { Text(text = "ex: 9") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .heightIn(min = 10.dp)
                    .width(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Faz atividade física?",
                fontSize = 14.sp,
                modifier = Modifier
                    .offset(y = 4.dp)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sim", textAlign = TextAlign.Center)
                RadioButton(selected = rbTreino, onClick = { viewModel.onRbTreinoChange(true) })
                Text(text = "Não", textAlign = TextAlign.Center)
                RadioButton(selected = !rbTreino, onClick = { viewModel.onRbTreinoChange(false)})
            }
        }
        BottomAppBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Data de Nascimento") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Selecione a data"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 32.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(calendar.time)
}

@Composable
fun BottomAppBar() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF018786),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(Icons.Filled.Home, contentDescription = "Home Icon")
                        }
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit Icon",
                            )
                        }
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu Icon",
                            )
                        }
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = "Settings Icon",
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = Color(0xFF03DAC5),
                        contentColor = Color(0xFF1E98D7),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Check, "Check Button")
                    }
                }
            )
        },
    ) { innerPadding ->
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}