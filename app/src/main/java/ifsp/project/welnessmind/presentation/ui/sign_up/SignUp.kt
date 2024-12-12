package ifsp.project.welnessmind.presentation.ui.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview
@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val nome = viewModel.name.collectAsState()
    val email = viewModel.email.collectAsState()
    val senha = viewModel.password.collectAsState()
    val confirmarSenha = viewModel.confirmPassword.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth().padding(32.dp))
        Text(
            text = "Cadastro",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier
            .fillMaxWidth().padding(20.dp))

        Text(
            text = "Preencha os campos abaixo :D",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier
            .fillMaxWidth().padding(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Já possui uma conta?",
                fontSize = 14.sp,
                color = Color(0xFF8C8C8C)
            )

            TextButton (
                    onClick = { viewModel.onSignInClick(openAndPopUp) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF1E98D7)
                )
                ){
                Text("Faça o login")
            }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth().padding(16.dp))

        // Campos de cadastro

        Text(
            text = "Nome Completo",
            color = Color(0xFF8C8C8C),
            modifier = Modifier.offset((-80).dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        TextField(
            value = nome.value,
            onValueChange = { viewModel.updateNome(it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .heightIn(min = 40.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Email",
            color = Color(0xFF8C8C8C),
            modifier = Modifier.offset((-120).dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        TextField(
            value = email.value,
            onValueChange = { viewModel.updateEmail(it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .heightIn(min = 40.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Senha",
            color = Color(0xFF8C8C8C),
            modifier = Modifier.offset((-120).dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        TextField(
            value = senha.value,
            onValueChange = { viewModel.updatePassword(it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }, // TODO trocar o ícone e implementar 'onCick'
            modifier = Modifier
                .heightIn(min = 40.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Confirme a senha",
            color = Color(0xFF8C8C8C),
            modifier = Modifier.offset((-80).dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        TextField(
            value = confirmarSenha.value,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }, // TODO trocar o ícone e implementar função de clique
            modifier = Modifier
                .heightIn(min = 40.dp)
        )

        Button(
            onClick = { viewModel.onSignUpClick(openAndPopUp) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E98D7),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cadastrar".uppercase())
        }
    }

}

