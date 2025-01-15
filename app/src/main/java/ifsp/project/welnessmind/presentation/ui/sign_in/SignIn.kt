package ifsp.project.welnessmind.presentation.ui.sign_in

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ifsp.project.welnessmind.R
import ifsp.project.welnessmind.presentation.theme.Blue
import ifsp.project.welnessmind.presentation.theme.Cyan

@Preview
@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val email = viewModel.email.collectAsState()
    val senha = viewModel.password.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Text(
            text = "Bem-vindo(a) de volta!\nEntre com seu e-mail e senha abaixo",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Não tem uma conta?",
                fontSize = 14.sp,
                color = Color(0xFF8C8C8C)
            )

            TextButton(
                onClick = { viewModel.onSignUpClick(openAndPopUp) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF1E98D7)
                )
            ) {
                Text("Cadastre-se")
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        // Campos de login

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
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .heightIn(min = 40.dp)
                .fillMaxWidth(0.8f)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
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
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Blue,
                unfocusedIndicatorColor = Cyan
            ),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        painter = if (passwordVisible)
                            painterResource(R.drawable.eye_show)
                        else
                            painterResource(R.drawable.eye_hide),
                        contentDescription = if (passwordVisible)
                            "Ocultar senha"
                        else
                            "Mostrar senha",
                        tint = Blue
                    )
                }
            },
            modifier = Modifier
                .heightIn(min = 40.dp)
                .fillMaxWidth(0.8f)
        )

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        TextButton(
            onClick = { viewModel.onForgotPasswordClick(openAndPopUp) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF1E98D7)
            )
        ) {
            Text("Esqueceu sua senha? Clique aqui")
        }

        Button(
            onClick = { viewModel.onSignInClick(openAndPopUp) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E98D7),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Entrar".uppercase())
        }
    }

}

