package ifsp.project.welnessmind.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import ifsp.project.welnessmind.ERROR_TAG
import ifsp.project.welnessmind.R
import ifsp.project.welnessmind.SIGN_IN_SCREEN
import ifsp.project.welnessmind.SIGN_UP_SCREEN
import kotlinx.coroutines.launch

@Composable
fun ConditionalScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 48.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_transparent), //
                    contentDescription = "Logo",
                    modifier = Modifier.size(96.dp)
                )
                // Adiciona um espaço entre a imagem e o texto
                Spacer(modifier = Modifier.width(24.dp))
                Column {
                    Text(
                        text = appName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = FontFamily.Serif, // TODO 1. trocar pela Segoe
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF41E8CD)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
            // Central Section
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Descubra o que o $appName pode fazer por você:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Aqui no $appName, acreditamos que cuidar da sua saúde mental pode ser simples e transformador.",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                val taskDescriptions = listOf(
                    "• Crie rotinas saudáveis que se encaixam no seu dia a dia",
                    "• Organize suas informações e reduza a sobrecarga mental",
                    "• Desfrute de dicas personalizadas para melhorar seu bem-estar"
                )
                LazyColumn {
                    items(taskDescriptions) { description ->
                        ListItem(
                            headlineContent = { Text(description) }
                        )
                    }
                }
            }

            // Bottom Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(SIGN_UP_SCREEN)},
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF41E8CD),
                        contentColor = Color.White
                    ),
                ) {
                    Text("Primeiro acesso")
                }
                Button(
                    onClick = { navController.navigate(SIGN_IN_SCREEN) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF41E8CD),
                        contentColor = Color.White
                    ),
                ) { Text("Já tenho cadastro") }
            }
            LoginWithGoogle(buttonText = R.string.login_with_google, onGetCredentialResponse = { /*TODO*/ })
        }
    }
}

@Composable
fun LoginWithGoogle(
    buttonText: Int,
    onGetCredentialResponse: (Credential) -> Unit
) {
    Row {

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val credentialManager = CredentialManager.create(context)

        Button(
            onClick = {
                val googleIdOption =
                    GetGoogleIdOption.Builder() // Para recuperar o token do ID do Google do usuário
                        .setFilterByAuthorizedAccounts(false) //Para que os usuários possam ver todas as contas disponíveis
                        .setServerClientId(context.getString(R.string.default_web_client_id))
                        .setAutoSelectEnabled(true) // Login automático para os usuários que se registrarem com a conta única
                        .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                coroutineScope.launch {
                    try {
                        val result = credentialManager.getCredential(
                            request = request,
                            context = context
                        )
                        onGetCredentialResponse(result.credential)
                    } catch (e: GetCredentialException) {
                        Log.d(
                            ERROR_TAG,
                            "LoginWithGoogle: ${e.message.orEmpty()}"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.google_g),
                contentDescription = "Login com o Google",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Login com o Google")
        }
    }
}
